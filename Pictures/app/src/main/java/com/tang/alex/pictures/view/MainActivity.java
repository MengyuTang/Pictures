package com.tang.alex.pictures.view;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.tang.alex.pictures.R;
import com.tang.alex.pictures.adapter.LoadMoreWrapper;
import com.tang.alex.pictures.adapter.PicturesAdapter;
import com.tang.alex.pictures.callback.RecyclerOnScrollListener;
import com.tang.alex.pictures.model.bean.AllItemsBean;
import com.tang.alex.pictures.model.bean.BaseBean;
import com.tang.alex.pictures.presentor.IPresentor;
import com.tang.alex.pictures.presentor.PresentorImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements IView{

    Unbinder unbinder;

    @BindView(R.id.ry_pics)
    RecyclerView ryPics;

    private PicturesAdapter picAdapter;
    private LoadMoreWrapper loadMoreWrapper;
    private IPresentor mPresentor;
    private int currentPosition = 0;
    private List<AllItemsBean> all_items;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        initView();
        getData();
    }

    public void initView(){
        mPresentor = new PresentorImpl();
        mPresentor.onAttachView(this);
        all_items = new ArrayList<>();
        GridLayoutManager layoutManager = new GridLayoutManager(this,2);
        ryPics.setLayoutManager(layoutManager);
        picAdapter = new PicturesAdapter(this);
        picAdapter.setOnItemClickListener(new PicturesAdapter.onItemClickListener() {
            @Override
            public void onItemClick(int position) {
                AllItemsBean allItemsBean = all_items.get(position);
                Intent intent = new Intent(MainActivity.this,ImageDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("allItemsBean",allItemsBean);
                intent.putExtras(bundle);
                startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(MainActivity.this).toBundle());
            }
        });
        loadMoreWrapper = new LoadMoreWrapper(picAdapter,this);
        ryPics.setAdapter(loadMoreWrapper);
        ryPics.addOnScrollListener(new RecyclerOnScrollListener() {
            @Override
            public void onLoadMore() {
                loadMoreWrapper.setLoadState(loadMoreWrapper.LOADING);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                currentPosition += 16;
                                getData();
                            }
                        });
                    }
                }, 1000);
                }
        });
    }

    private void getData(){
        HashMap<String,Object> map = new HashMap<>();
        map.put("category","搞笑");
        map.put("tag","全部");
        map.put("start",currentPosition);
        map.put("len",16);
        mPresentor.getData(map);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        mPresentor.onDetachView();
    }

    @Override
    public void showLoadingDialog() {
        Log.e("MainActivity","Loading!!!");
    }

    @Override
    public void dismissDialog() {
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADCOMPLETE);
    }

    @Override
    public void showData(BaseBean data) {
        if (null != data){
            all_items.addAll(data.getAll_items());
            picAdapter.setData(all_items);
            loadMoreWrapper.notifyDataSetChanged();
        }
    }

    @Override
    public void showErrorMessage(String msg) {
        loadMoreWrapper.setLoadState(loadMoreWrapper.LOADCOMPLETE);
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}
