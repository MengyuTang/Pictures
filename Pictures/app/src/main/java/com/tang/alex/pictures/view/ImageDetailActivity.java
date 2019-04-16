package com.tang.alex.pictures.view;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;
import android.view.ViewGroup;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tang.alex.pictures.CommonUtils;
import com.tang.alex.pictures.R;
import com.tang.alex.pictures.model.bean.AllItemsBean;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ImageDetailActivity extends AppCompatActivity{

    Unbinder unbinder;

    @BindView(R.id.iv_big_pic)
    SimpleDraweeView ivBigPic;

    private AllItemsBean allItemsBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_detail);
        unbinder = ButterKnife.bind(this);
        getWindow().setEnterTransition(new Explode().setDuration(2000));
        getWindow().setExitTransition(new Explode().setDuration(2000));
        initView();
    }

    private void initView() {
        allItemsBean = (AllItemsBean) getIntent().getExtras().getSerializable("allItemsBean");
        String url = allItemsBean.getOri_pic_url();
        Uri uri = Uri.parse(url);
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setAutoPlayAnimations(true)
                .setTapToRetryEnabled(true)
                .build();
        ivBigPic.setController(controller);
        GenericDraweeHierarchyBuilder builder = new GenericDraweeHierarchyBuilder(getResources());
        GenericDraweeHierarchy hierarchy = builder.setFadeDuration(1000)
                .setPlaceholderImage(getDrawable(R.mipmap.ic_launcher_round))
                .setRetryImage(R.mipmap.ic_launcher)
                .setFailureImage(R.mipmap.ic_launcher_round)
                .setProgressBarImage(R.mipmap.progressbar)
                .setActualImageScaleType(ScalingUtils.ScaleType.CENTER_CROP)
                .build();
        ivBigPic.setHierarchy(hierarchy);
        ViewGroup.LayoutParams lp = ivBigPic.getLayoutParams();
        lp.height = allItemsBean.getHeight();
        lp.width = allItemsBean.getWidth();
        ivBigPic.setLayoutParams(lp);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
