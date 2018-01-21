package com.demon.yu;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;


/**
 * Created by yujintao on 2017/10/9.
 * <p>
 * 适配recyclerView吸顶控件 V1.0.0
 */

public class TopDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = TopDecoration.class.getSimpleName();
    private VirtualFamily VirtualFamily;
    private LinearLayoutManager gridLayoutManager;
    private ViewGroup headerContainer;
    private RecyclerView.ViewHolder currentHeaderViewHolder;
    private int currentHeaderType;
    private int currentHeaderPosition;
    private Paint drawBitmap;

    /**
     * @param virtualFamily       虚拟父子类关系
     * @param recyclerView        recyclerView
     * @param linearLayoutManager linearLayoutManager
     * @param headerContainer     吸顶的父view
     */
    public TopDecoration(VirtualFamily virtualFamily, RecyclerView recyclerView, LinearLayoutManager linearLayoutManager, ViewGroup headerContainer) {
        this.VirtualFamily = virtualFamily;
        this.gridLayoutManager = linearLayoutManager;
        this.headerContainer = headerContainer;
        recyclerView.addItemDecoration(this);
        drawBitmap = new Paint(Paint.FILTER_BITMAP_FLAG);
    }


    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);
        int position = gridLayoutManager.findFirstVisibleItemPosition();
        if (position == RecyclerView.NO_POSITION) {
            return;
        }

        boolean isParent = VirtualFamily.isParentType(position);
        boolean isChild = VirtualFamily.isChildType(position);
        if (!isParent && !isChild) {
            headerContainer.setVisibility(View.GONE);
            return;
        }

        int lastChildPosition = 0;
        int parentPosition = 0;
        int childNumber;
        if (isParent) {
            parentPosition = position;
            childNumber = VirtualFamily.parentChildren(position);
            lastChildPosition = position + childNumber;
        } else {
            parentPosition = VirtualFamily.childParentPosition(position);
            childNumber = VirtualFamily.parentChildren(parentPosition);
            lastChildPosition = parentPosition + childNumber;
        }
        View parentView = gridLayoutManager.findViewByPosition(parentPosition);
        if (childNumber == 0 || (parentView != null && parentView.getTop() > 0)) {
            headerContainer.setVisibility(View.GONE);
            //INFO 如果没有一个child的话,就不显示就好了
            return;
        }
        headerContainer.setVisibility(View.VISIBLE);
        View lastChild = gridLayoutManager.findViewByPosition(lastChildPosition);
        int viewType = parent.getAdapter().getItemViewType(parentPosition);
        if (currentHeaderViewHolder == null) {
            currentHeaderViewHolder = parent.getAdapter().createViewHolder(parent, viewType);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            headerContainer.addView(currentHeaderViewHolder.itemView, layoutParams);
        }
        if (currentHeaderPosition != parentPosition) {
            parent.getAdapter().bindViewHolder(currentHeaderViewHolder, parentPosition);
            currentHeaderPosition = parentPosition;
        }
        if (lastChild != null) {
            int lastChildY = lastChild.getBottom();
            int viewHeight = currentHeaderViewHolder.itemView.getHeight();
            if (viewHeight >= lastChildY) {
                int diffY = lastChildY - viewHeight;
                currentHeaderViewHolder.itemView.setTranslationY(diffY);
            } else {
                currentHeaderViewHolder.itemView.setTranslationY(0);
            }
        } else {
            currentHeaderViewHolder.itemView.setTranslationY(0);
        }
    }


}
