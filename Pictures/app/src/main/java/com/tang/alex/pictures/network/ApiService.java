package com.tang.alex.pictures.network;

import com.tang.alex.pictures.model.bean.BaseBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {

    /**
     * 根据标记获取图片列表
     * @return
     */
    @GET("/pics/channel/getAllRecomPicByTag.jsp")
    Observable<BaseBean> getPicturesByTags( @QueryMap Map<String, Object> map);
}
