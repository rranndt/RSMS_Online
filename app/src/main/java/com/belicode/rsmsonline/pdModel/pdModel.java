package com.belicode.rsmsonline.pdModel;

import android.app.ProgressDialog;
import android.content.Context;

import com.belicode.rsmsonline.R;

public class pdModel {

    static ProgressDialog progressDialog;
    public static void pdLogin(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Loading....");
        progressDialog.setIndeterminate(true);
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.show();
    }
    public static void pdData(Context context){
        progressDialog=new ProgressDialog(context, R.style.AppTheme_Dark_Dialog);
        progressDialog.setMessage("Menyiapkan data ...");
        progressDialog.setTitle("Silahkan Tunggu");
        progressDialog.setIndeterminate(true);
        progressDialog.show();

    }

    public static void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }
}
