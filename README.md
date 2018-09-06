## 基于RecycerView的decoration的吸顶联动的控件

### 实现原理分析

简单的说就是一个父亲后面跟着一堆孩子。它们是一对多的关系，所以有以下几个特性
- 判断当前是否为父亲节点位置
- 判断当前是否为child节点位置
- 如果当前为child节点，那么可以得到该child节点的父节点的位置
- 如果当前为父节点，知道其子节点的数量

节点位置，在该技术范畴就是指RecycerView的adapter的position。

<!-- more -->

先看一个效果图(gif图)

![tu](https://i.niupic.com/images/2018/09/06/5zxB.gif)


那么根据上面的情况设计一个接口。

就叫VirtualFamily。
```
public interface VirtualFamily {
  /**
 * 是否是吸顶类型
 */
 boolean isParentType(int position);
 /**
 * 吸顶类型所包含的子类型的数量
 */
 int parentChildren(int parentPosition);
 /**
 * 是否是子类型
 */
 boolean isChildType(int position);

 /**
 * 返回子类型的父类型的位置
 */
 int childParentPosition(int childPosition);
}
```
#### 技术选型
由于该吸顶设计到滚动，所以一般情况下，可以两种选择，一种加入onscroll的监听，一种使用RecyclerView的decoration。我这里选择decoration，因为我觉的更灵活，而且我觉的本身decoration就是装饰者，更加符合该实现的情形。同时为了灵活些，吸顶的view容器需要直接传递给decoration。这样可以方便扩展横竖还有其他的使用场景（目前横向没有实现，但是很简单）

----------
#### 实现细节


标题引用详细源码请看TopDecoration。这里只做简单的描述。

- 在onDrawOver的时候，首先需要通过LayoutManager获取当前第一个显示的节点。判断其是子节点还是父节点，还是普通节点。
- 如果是普通节点，直接隐藏吸顶容器.如果是父节点，我们需要通过VirtualFamily获取子节点数量，然后计算出最后一个子节点的position。
- 如果是子节点，我们先获取父节点的位置，然后在获取子节点，
- 最后在计算出最后一个自节点的position

然后我们需要制作这个父节点的view视图，由于recyclerView的可以支持多个类型和使用的是ViewHolder。所以我们首先获取父节点的视图类型也就是viewType，然后调用adapter创建一个ViewHolder，把这个viewholder的View加入到我们的吸顶容器里，然后在绑定数据。（注意目前我没有支持同时支持多个类型的吸顶情况，因为感觉使用场景不多，有需要的话可以在扩展下）
最后一步就是需要计算滚动时，吸顶容器也需要跟着滚动了。只需要计算最后一个子child的bottom和我们吸顶容器高度对比，计算出差值来，然后通过属性动画移动就可以了。



#### 总结
整个实现过程其实很清晰，如果支持横向的话可以自己更改一下

github地址: https://github.com/yujintao529/TopDecoration

博客地址： http://www.demon-yu.com
