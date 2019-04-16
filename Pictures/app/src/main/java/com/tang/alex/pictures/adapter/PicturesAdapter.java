package com.tang.alex.pictures.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tang.alex.pictures.R;
import com.tang.alex.pictures.model.bean.AllItemsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PicHolder>{

    private Context mContext;

    private List<AllItemsBean> datas;

    private onItemClickListener onItemClickListener;

    public PicturesAdapter(Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<AllItemsBean> datas){
        this.datas = datas;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(onItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public PicHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new PicHolder(LayoutInflater.from(mContext).inflate(R.layout.item_pic,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull final PicHolder picHolder, int i) {
        if (datas.size()>0){
            String url = datas.get(i).getPic_url();
            Uri uri = Uri.parse(url);
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(true)
                    .setTapToRetryEnabled(true)
                    .build();
            picHolder.simpleDraweeView.setController(controller);
            GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(mContext.getResources());
            GenericDraweeHierarchy hierarchy = builder.setFadeDuration(1000)
                    .setPlaceholderImage(mContext.getDrawable(R.mipmap.ic_launcher_round))
                    .setRetryImage(R.mipmap.ic_launcher)
                    .setFailureImage(R.mipmap.ic_launcher_round)
                    .setProgressBarImage(R.mipmap.progressbar)
                    .build();
            picHolder.simpleDraweeView.setHierarchy(hierarchy);
            picHolder.simpleDraweeView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(picHolder.getAdapterPosition());
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (null ==datas) {
            return 0;
        }
        return datas.size();
    }

    public class PicHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.simpleDraweeView)
        SimpleDraweeView simpleDraweeView;

        public PicHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position);
    }
}
