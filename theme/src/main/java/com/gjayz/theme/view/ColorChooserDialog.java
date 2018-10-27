package com.gjayz.theme.view;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.gjayz.theme.R;

import java.io.Serializable;

public class ColorChooserDialog extends DialogFragment {

    private static final String KEY_BUILDER = "key_builder";

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity())
                .setTitle(R.string.strTitle)
                .create();
        return alertDialog;
    }

    public static class Builder implements Serializable {

        private int titleId;
        private int ok;
        private int cancel;
        private int custom;

        public ColorChooserDialog build() {
            ColorChooserDialog colorChooserDialog = new ColorChooserDialog();
            Bundle bundle = new Bundle();
            bundle.putSerializable(KEY_BUILDER, this);
            colorChooserDialog.setArguments(bundle);
            return colorChooserDialog;
        }
    }
}
