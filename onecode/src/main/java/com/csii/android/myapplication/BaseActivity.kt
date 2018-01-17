package com.csii.android.myapplication

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

/**
 * TODO:
 *
 * 使用说明：
 *
 * Created by ChangXubin on 2018/1/17.
 */
abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        ActivityTask.pushActivity(this)
    }

    /**
     * 获取要加载的布局ID
     */
    abstract fun getLayoutId():Int

}