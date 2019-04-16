package com.tang.alex.pictures.model;

import android.util.Log;

import com.google.gson.Gson;
import com.tang.alex.pictures.model.bean.BaseBean;
import com.tang.alex.pictures.network.RetrofitManager;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ModelImpl implements IModel{

    @Override
    public void getData(Map<String,Object> map, final MyObservable observable) {
        new RetrofitManager().getPicturesByTags( map, new Observer<BaseBean>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(BaseBean baseBean) {
                Log.e("ModelImpl","baseBean:"+new Gson().toJson(baseBean));
                observable.onSuccess(baseBean);
            }

            @Override
            public void onError(Throwable e) {
                observable.onFailed(e.getMessage());
                observable.onError();
            }

            @Override
            public void onComplete() {
                observable.onComplete();
            }
        });
    }
}
