package com.tang.alex.pictures.view;

import com.tang.alex.pictures.model.bean.BaseBean;

public interface IView {

    void showLoadingDialog();

    void dismissDialog();

    void showData(BaseBean data);

    void showErrorMessage(String msg);
}
