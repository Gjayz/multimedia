package com.gjayz.multimedia.ui.fragment;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.utils.DeviceUtils;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import butterknife.BindView;

public class DeviceInfoFragment extends BaseFragment {

    private StringBuilder mGpuInfo;
    @BindView(R.id.textview)
    TextView mTextView;
    @BindView(R.id.view_container)
    RelativeLayout vieGroup;

    public static DeviceInfoFragment newInstance() {
        return new DeviceInfoFragment();
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_deviceinfos;
    }

    @Override
    public void init() {
        vieGroup.addView(new DemoGLSurfaceView(getActivity()), 1, 1);
        updateShowInfo();
    }

    class DemoGLSurfaceView extends GLSurfaceView {

        DemoRenderer mRenderer;

        public DemoGLSurfaceView(Context context) {
            super(context);
            setEGLContextClientVersion(1);
            setEGLConfigChooser(8, 8, 8, 8, 0, 0);
            mRenderer = new DemoRenderer();
            setRenderer(mRenderer);
        }
    }

    class DemoRenderer implements GLSurfaceView.Renderer {

        public void onSurfaceCreated(GL10 gl, EGLConfig config) {
            mGpuInfo = new StringBuilder();

            mGpuInfo.append("============ GPU ============");
            mGpuInfo.append("\n");
            mGpuInfo.append("\n");
            mGpuInfo.append(gl.glGetString(GL10.GL_RENDERER));
            mGpuInfo.append("\n");
            mGpuInfo.append(gl.glGetString(GL10.GL_VENDOR));
            mGpuInfo.append("\n");
            mGpuInfo.append(gl.glGetString(GL10.GL_VERSION));
            mGpuInfo.append("\n");
            mGpuInfo.append("\n");
            mGpuInfo.append("\n");

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateShowInfo();
                }
            });
        }

        @Override
        public void onDrawFrame(GL10 arg0) {

        }


        @Override
        public void onSurfaceChanged(GL10 arg0, int arg1, int arg2) {
        }
    }

    private void updateShowInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DeviceUtils.getDeviceInfos(getActivity()));
        if (mGpuInfo != null) {
            stringBuilder.append(mGpuInfo);
        }
        stringBuilder.append(DeviceUtils.getEncoderAndDecoders());
        mTextView.setText(stringBuilder);
    }
}