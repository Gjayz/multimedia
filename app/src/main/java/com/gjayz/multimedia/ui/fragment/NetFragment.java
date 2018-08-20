package com.gjayz.multimedia.ui.fragment;

import android.view.View;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.net.base.BaseBean;
import com.gjayz.multimedia.net.bean.IPBean;
import com.gjayz.multimedia.net.presenter.IPHostPresenter;
import com.gjayz.multimedia.utils.Utils;

import butterknife.BindView;
import butterknife.OnClick;

public class NetFragment extends BaseFragment {

    private static final String TAG = "NetFragment";
    @BindView(R.id.start_test)
    View mStartTextview;
    @BindView(R.id.copy_test_result)
    View mCopyTextview;
    @BindView(R.id.tips_view)
    TextView mTipsView;

    public static NetFragment newInstance() {
        return new NetFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_net;
    }

    @Override
    public void init() {
        IPHostPresenter ipHostPresenter = new IPHostPresenter(this);
        ipHostPresenter.getIPHost(this);
    }

    @Override
    public void showData(BaseBean baseBean) {
        if (baseBean instanceof IPBean) {
            IPBean ipBean = (IPBean) baseBean;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("ip:")
                    .append(ipBean.getIp())
                    .append(" ")
                    .append(ipBean.getCountry())
                    .append(" ")
                    .append(ipBean.getCity());
            mTipsView.setText(stringBuilder);
            mStartTextview.setEnabled(true);
        }
    }

    @OnClick({R.id.start_test, R.id.copy_test_result})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_test:
                break;
            case R.id.copy_test_result:
                Utils.copyTextToClipBroad(getActivity(), "");
                break;
        }
    }
}