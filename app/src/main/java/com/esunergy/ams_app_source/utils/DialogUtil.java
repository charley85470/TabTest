package com.esunergy.ams_app_source.utils;

import android.app.Dialog;
import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;
import com.esunergy.ams_app_source.R;

public class DialogUtil {

    public static Dialog createCheckVersionProgressDialog(Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_title_normal)
                .content(R.string.dialog_check_version_loading)
                .cancelable(false)
                .progress(true, 0).build();
        return dialog;
    }

    public static Dialog createProgressDialog(Context context) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(R.string.dialog_title_normal)
                .content(R.string.dialog_loading)
                .cancelable(false)
                .progress(true, 0).build();
        return dialog;
    }

    public static Dialog showMessage(Context context, String title, String msg) {
        MaterialDialog dialog = new MaterialDialog.Builder(context)
                .title(title)
                .content(msg)
                .cancelable(false)
                .positiveText(R.string.confirm)
                .show();
        return dialog;
    }

}
