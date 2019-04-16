package com.tang.alex.pictures.presentor;

import com.tang.alex.pictures.view.IView;

import java.util.Map;

public interface IPresentor {

   void getData(Map<String, Object> map);

   void onAttachView(IView view);

   void onDetachView();
}
