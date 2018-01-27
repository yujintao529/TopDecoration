package com.demon.yu.decoration;

/**
 * Created by yujintao on 2017/10/9.
 */

public interface VirtualFamily {


    /**
     * 是否是父类型
     *
     * @param position
     * @return
     */
    boolean isParentType(int position);

    /**
     * 父类型包含的子类型的数量
     *
     * @param parentPosition
     * @return 父类型包含的子类型的数量
     */
    int parentChildren(int parentPosition);

    /**
     * 是否是子类型
     *
     * @param position
     * @return boolean
     */
    boolean isChildType(int position);

    /**
     * 返回该子类型的父类型的位置
     *
     * @param childPosition
     * @return
     */
    int childParentPosition(int childPosition);


}
