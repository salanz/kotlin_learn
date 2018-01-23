package com.csii.android.myapplication

import android.content.Intent
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.csii.android.myapplication.activity_life.SaveInstanceActivity
import com.csii.android.myapplication.base_knowledge.JumpActivity
import com.csii.android.myapplication.base_knowledge.MainActivity
import com.csii.android.myapplication.webview.WebViewActivity
import kotlinx.android.synthetic.main.activity_go.*

class GoActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_go
    }

    var dataList = arrayOf(MainActivity::class.java
            ,JumpActivity::class.java
            ,SaveInstanceActivity::class.java
            , WebViewActivity::class.java)

    var dataStrList = arrayOf("MainActivity"
            ,"JumpActivity"
            ,"SaveInstanceActivity"
            ,"WebViewActivity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        listview.adapter = ArrayAdapter<String>(GoActivity@this,android.R.layout.simple_list_item_1,dataStrList)
        listview.onItemClickListener = AdapterView.OnItemClickListener {
            parent, view, position, id ->  startActivity(Intent(GoActivity@this, dataList[position]))}
    }
}
