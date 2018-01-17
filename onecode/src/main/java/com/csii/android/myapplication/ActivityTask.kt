package com.csii.android.myapplication

import android.app.Activity
import java.util.*

/**
 * TODO:
 *
 * 使用说明：
 *
 * Created by ChangXubin on 2018/1/17.
 */
class ActivityTask(){

    companion object {
        //栈
        private val activityStack = Stack<Activity>()

        /**
         * 入栈
         */
        fun pushActivity(activity: Activity){
            activityStack.push(activity)
        }

        /***
         * 推出栈
         */
        fun popActivity(activity: Activity){
            activityStack.remove(activity)
            activity.finish()
        }


        /**
         * 结束指定activity
         *
         * @param activity
         */
         fun endActivity(activity : Activity) {
                activity.finish()
                activityStack.remove(activity)
        }

        /**
         * 获得当前的activity(即最上层)
         *
         * @return
         */
        fun currentActivity() : Activity? {

            var activity:Activity? = null
            if (!activityStack.empty()){
                activity = activityStack.lastElement()
            }
            return activity
        }

        /**
         * 结束所有activity
         */
        fun finishAllActivity() {
            while (!activityStack.empty()) {
                val activity : Activity? = currentActivity()
                endActivity(activity!!)
            }
        }
    }



        /**
         * 弹出除cls外的所有activity
         *
         * @param cls
         */
//        fun popAllActivityExceptOne(Class<? extends Activity> cls) {
//            while (true) {
//                Activity activity = currentActivity();
//                if (activity == null) {
//                    break;
//                }
//                if (activity.getClass().equals(cls)) {
//                    break;
//                }
//                popActivity(activity);
//            }
//        }

        /**
         * 结束除cls之外的所有activity,执行结果都会清空Stack
         *
         * @param cls
         */
//        public void finishAllActivityExceptOne(Class<? extends Activity> cls) {
//            while (!activityStack.empty()) {
//                Activity activity = currentActivity();
//                if (activity.getClass().equals(cls)) {
//                    popActivity(activity);
//                } else {
//                    endActivity(activity);
//                }
//            }
//        }


}