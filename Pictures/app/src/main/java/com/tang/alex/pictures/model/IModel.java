package com.tang.alex.pictures.model;

import java.util.Map;

public interface IModel {
    void getData(Map<String, Object> map, MyObservable observable);
}
