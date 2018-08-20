package com.gjayz.multimedia.net;

import com.gjayz.multimedia.net.bean.IPBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface APIService {
    @GET("json")
    Observable<IPBean> getIPHost();
}
