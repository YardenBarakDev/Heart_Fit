package com.idan_koren_israeli.heartfit.common

import android.app.Activity
import android.app.Application
import android.os.Bundle

class MyAppLifecycleCallbacks: Application.ActivityLifecycleCallbacks {
    var currentActivity: Activity? = null
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }


}