package com.csii.android.myapplication

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

class MainActivity : BaseActivity() {

    override fun getLayoutId(): Int {
        return R.layout.activity_main

    }
    private val REQUEST_CODE : Int = 0x111

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.jump_one -> startActivity(Intent(this,JumpActivity::class.java))
            R.id.jump_two -> startActivity(Intent("com.csii.android.myapplication.ACTION_START"))
            R.id.jump_three -> startActivity(Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.baidu.com")))
            R.id.jump_four -> sendData()
            R.id.jump_five -> sendDataForResult()

        }
        return true
    }

    private fun sendData(){
        val intent = Intent(this,JumpActivity::class.java)
        val bundle = Bundle()
        bundle.putString("name","MainActivity类")
        intent.putExtras(bundle)
        startActivity(intent)
    }

    private fun sendDataForResult(){
        val intent = Intent(this,JumpActivity::class.java)
        val bundle = Bundle()
        bundle.putString("name","MainActivity类")
        intent.putExtras(bundle)
        startActivityForResult(intent,REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            REQUEST_CODE -> if (data != null) {
                if (resultCode == Activity.RESULT_OK) {
                    val bundle:Bundle = data.extras
                    val name:String = bundle.getString("name","啥也没有")
                    Toast.makeText(this,name,Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
