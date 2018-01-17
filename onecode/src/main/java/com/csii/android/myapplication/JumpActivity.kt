package com.csii.android.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_jump.*

class JumpActivity : BaseActivity() {
    override fun getLayoutId(): Int {
        return R.layout.activity_jump
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //大坑要注意,这个地方是可空的。如果在定义变量的时候不加？设置可空，
        // 哪么bundle ！= null就永远为true，那么程序就要boom boom boom
        val bundle:Bundle? = intent.extras
        if (bundle != null) {
            val name:String = bundle.getString("name")
            Toast.makeText(this,name,Toast.LENGTH_SHORT).show()

            Log.e("HAT",ActivityTask.currentActivity()?.localClassName)
        }

        go_back.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("name","JumpActivity")
            setResult(Activity.RESULT_OK, Intent().putExtras(bundle))
            finish()
        }

        remove_all.setOnClickListener {
            ActivityTask.finishAllActivity()
        }

        remove_one.setOnClickListener {
            ActivityTask.popActivity(this)
            Log.e("HAT",ActivityTask.currentActivity()?.localClassName)

        }
    }
}
