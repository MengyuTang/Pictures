package com.tang.alex.pictures.presentor;

import com.tang.alex.pictures.model.IModel;
import com.tang.alex.pictures.model.ModelImpl;
import com.tang.alex.pictures.model.MyObservable;
import com.tang.alex.pictures.model.bean.BaseBean;
import com.tang.alex.pictures.view.IView;

import java.util.Map;

import io.reactivex.Observer;

public class PresentorImpl implements IPresentor{

    private IModel mModel;

    private IView mView;

    @Override
    public void getData(Map<String, Object> map) {
        mModel = new ModelImpl();
        mView.showLoadingDialog();
        mModel.getData(map, new MyObservable() {
            @Override
            public void onSuccess(BaseBean data) {
                mView.showData(data);
            }

            @Override
            public void onFailed(String errMsg) {
                mView.showErrorMessage(errMsg);
            }

            @Override
            public void onError() {
                mView.showErrorMessage("");
            }

            @Override
            public void onComplete() {
                mView.dismissDialog();
            }

            @Override
            protected void subscribeActual(Observer<? super BaseBean> observer) {

            }
        });
    }

    @Override
    public void onAttachView(IView view) {
        mView = view;
    }

    @Override
    public void onDetachView() {
        if (null != mView){
            mView = null;
        }
    }
}
