package com.gjayz.multimedia.net.presenter;

import com.gjayz.multimedia.net.base.IView;
import com.gjayz.multimedia.net.base.OnCallback;
import com.gjayz.multimedia.net.bean.IPBean;
import com.gjayz.multimedia.net.model.IPHostModel;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;

public class IPHostPresenter {

    private IView<IPBean> mView;
    private IPHostModel mHostModel;

    public IPHostPresenter(IView iView) {
        this.mView = iView;
        mHostModel = new IPHostModel();
    }

    public void getIPHost(LifecycleProvider<FragmentEvent> lifecycleProvider) {
        mHostModel.getIPHost(lifecycleProvider, new OnCallback<IPBean>() {
            @Override
            public void onSuccess(IPBean ipBean) {
                if (mView != null){
                    mView.showData(ipBean);
                }
            }

            @Override
            public void onFailed(int errCode, String errMsg) {
                if (mView != null){
                    mView.showError(errCode, errMsg);
                }
            }
        });
    }
}
