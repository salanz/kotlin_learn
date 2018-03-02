package com.csii.android.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

/**
 * TODO:
 * <p>
 * 使用说明：
 * <p>
 * Created by zhangyifeng on 2018/3/2.
 */

public class IndexActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.hava_app).setOnClickListener(this);
        findViewById(R.id.go_app).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.hava_app:

                if (checkFacebookExist(IndexActivity.this)) {
                    Toast.makeText(IndexActivity.this,"已安装中信手机银行",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(IndexActivity.this,"未安装中信手机银行",Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.go_app:

                if (checkFacebookExist(IndexActivity.this)){
                    try{
                        Intent intent = this.getPackageManager().getLaunchIntentForPackage(appPackageName);
                        startActivity(intent);
                    }catch(Exception e){
                        Toast.makeText(this, "没有安装", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


    private static String appPackageName = "com.ecitic.bank.mobile";

    public static boolean checkApkExist(Context context, String packageName){
        if (TextUtils.isEmpty(packageName))
            return false;
        try {
            ApplicationInfo info = context.getPackageManager()
                    .getApplicationInfo(packageName,
                            PackageManager.GET_UNINSTALLED_PACKAGES);
            Log.e("index",info.toString());
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("index",e.toString());
            return false;
        }
    }

    public static boolean checkFacebookExist(Context context){
        return checkApkExist(context, appPackageName);
    }
}
