package edu.sqchen.iubao.ui.util;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;
import edu.sqchen.iubao.R;

/**
 * Created by Administrator on 2017/4/29.
 */

public class LoadingDialogUtil {

    public static SweetAlertDialog mLoadingDialog;

    public static void initDialog(Context context) {
        mLoadingDialog = new SweetAlertDialog(context,SweetAlertDialog.PROGRESS_TYPE);
        mLoadingDialog.getProgressHelper().setBarColor(context.getResources().getColor(R.color.blue));
        mLoadingDialog.setTitleText("Loading");
        mLoadingDialog.setCancelable(false);
    }

}
