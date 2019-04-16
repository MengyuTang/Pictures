package com.tang.alex.pictures.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tang.alex.pictures.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoadMoreWrapper extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private RecyclerView.Adapter adapter;

    private Context mContext;

    private final int VIEWTYPE_ITEM = 1001;

    private final int VIEWTYPE_FOOTER = 1002;

    private int loadState = 2;//加载状态。默认加载完成

    public final int LOADING = 1; //正在加载

    public final  int LOADCOMPLETE = 2; //加载完成

    public final int LOADEND = 3; //加载到底部
    public LoadMoreWrapper(RecyclerView.Adapter adapter,Context mContext){
        this.adapter = adapter;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        if (viewType == VIEWTYPE_FOOTER){
            return new FooterHolder(LayoutInflater.from(mContext).inflate(R.layout.item_footer,viewGroup,false));
        }else{
            return adapter.onCreateViewHolder(viewGroup,viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof FooterHolder){
            FooterHolder footerHolder = (FooterHolder) holder;
            switch (loadState){
                case LOADING:
                    footerHolder.mProgressBar.setVisibility(View.VISIBLE);
                    footerHolder.tvRefreshText.setVisibility(View.VISIBLE);
                    footerHolder.tvBottom.setVisibility(View.GONE);
                    break;
                case LOADCOMPLETE:
                    footerHolder.mProgressBar.setVisibility(View.GONE);
                    footerHolder.tvRefreshText.setVisibility(View.GONE);
                    footerHolder.tvBottom.setVisibility(View.GONE);
                    break;
                case LOADEND:
                    footerHolder.mProgressBar.setVisibility(View.GONE);
                    footerHolder.tvRefreshText.setVisibility(View.GONE);
                    footerHolder.tvBottom.setVisibility(View.VISIBLE);
                    break;
            }
        }else{
            adapter.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return adapter.getItemCount()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position +1 == getItemCount()){
            return VIEWTYPE_FOOTER;
        }else{
            return VIEWTYPE_ITEM;
        }
    }

    public void setLoadState(int loadState){
        this.loadState = loadState;
        notifyDataSetChanged();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    // 如果当前是footer的位置，那么该item占据2个单元格，正常情况下占据1个单元格
                    return getItemViewType(position) == VIEWTYPE_FOOTER ? gridManager.getSpanCount() : 1;
                }
            });
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.progressBar)
        ProgressBar mProgressBar;

        @BindView(R.id.tv_refresh_text)
        TextView tvRefreshText;

        @BindView(R.id.tv_bottom)
        TextView tvBottom;

        public FooterHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
