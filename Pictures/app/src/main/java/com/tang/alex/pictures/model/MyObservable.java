package com.tang.alex.pictures.model;

import com.tang.alex.pictures.model.bean.BaseBean;

import io.reactivex.Observable;

public abstract class MyObservable extends Observable<BaseBean> {
    public abstract void onSuccess(BaseBean data);

    public abstract void onFailed(String errMsg);

    public abstract void onError();

    public abstract void onComplete();

}
