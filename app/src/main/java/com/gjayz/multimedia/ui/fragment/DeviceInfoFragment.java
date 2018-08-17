package com.gjayz.multimedia.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjayz.multimedia.R;
import com.gjayz.multimedia.utils.DeviceUtils;

public class DeviceInfoFragment extends Fragment {

    public static DeviceInfoFragment newInstance() {
        return new DeviceInfoFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_deviceinfos, null);
        TextView textView = view.findViewById(R.id.textview);
        textView.setText(DeviceUtils.getDeviceInfos(getActivity()));
        return view;
    }
}