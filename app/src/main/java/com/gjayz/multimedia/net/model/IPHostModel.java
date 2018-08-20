package com.gjayz.multimedia.net.model;

import com.gjayz.multimedia.net.APIService;
import com.gjayz.multimedia.net.NetConf;
import com.gjayz.multimedia.net.RetrofitManager;
import com.gjayz.multimedia.net.base.OnCallback;
import com.gjayz.multimedia.net.base.ZXObserver;
import com.gjayz.multimedia.net.bean.IPBean;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class IPHostModel {

    public void getIPHost(LifecycleProvider<FragmentEvent> lifecycleProvider, OnCallback<IPBean> callback) {
        RetrofitManager.getInstance()
                .createRetrofit(NetConf.IP_HOST)
                .create(APIService.class)
                .getIPHost()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .compose(lifecycleProvider.<IPBean>bindUntilEvent(FragmentEvent.DESTROY_VIEW))
                .subscribe(new ZXObserver<IPBean>(callback));
    }
}
