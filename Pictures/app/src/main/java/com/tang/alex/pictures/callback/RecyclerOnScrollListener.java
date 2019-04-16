package com.tang.alex.pictures.callback;

import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class RecyclerOnScrollListener extends RecyclerView.OnScrollListener {

    //用来标记是否正在向上滑动
    private boolean isSlidingUpward = false;

    @Override
    public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof LinearLayoutManager){
            LinearLayoutManager layoutManager = (LinearLayoutManager) manager;
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                //空闲状态，即停止滑动时
                //获取最后一个完全显示的itemPosition
                int lastItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = layoutManager.getItemCount();
                // 判断是否滑动到了最后一个item，并且是向上滑动
                if (lastItemPosition == (itemCount - 1) && isSlidingUpward){
                    //加载更多
                    onLoadMore();
                }
            }
        }else if (manager instanceof GridLayoutManager){
            GridLayoutManager layoutManager = (GridLayoutManager) manager;
            if (newState == RecyclerView.SCROLL_STATE_IDLE){
                //空闲状态，即停止滑动时
                //获取最后一个完全显示的itemPosition
                int lastItemPosition = layoutManager.findLastCompletelyVisibleItemPosition();
                int itemCount = layoutManager.getItemCount()/layoutManager.getSpanCount();
                // 判断是否滑动到了最后一个item，并且是向上滑动
                if (lastItemPosition == (itemCount - 1) && isSlidingUpward){
                    //加载更多
                    onLoadMore();
                }
            }
        }
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        // 大于0表示正在向上滑动，小于等于0表示停止或向下滑动
        if (dy>0){
            isSlidingUpward = true;
        }
    }

    /**
     * 加载更多回调方法
     */
    public abstract void onLoadMore();
}
