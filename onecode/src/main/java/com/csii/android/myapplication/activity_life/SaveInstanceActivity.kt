package com.csii.android.myapplication.activity_life

import android.os.Bundle
import android.os.PersistableBundle
import com.csii.android.myapplication.BaseActivity
import com.csii.android.myapplication.R
import kotlinx.android.synthetic.main.activity_save_instance.*

class SaveInstanceActivity : BaseActivity() {

    private var nameEv : String = ""
    private var psdEv : String = ""

    override fun getLayoutId(): Int {
        return R.layout.activity_save_instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            nameEv = savedInstanceState.getString("name")
            psdEv = savedInstanceState.getString("psd")
            name.setText(nameEv)
            psd.setText(psdEv)

        }

        getData()
    }

    private fun getData(){
        nameEv = name.text.toString()
        psdEv = psd.text.toString()

    }

    /**
     * 当页面被回收时，如果有
     */
    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)

        outState?.putString("name",nameEv)
        outState?.putString("psd",psdEv)

    }
}
