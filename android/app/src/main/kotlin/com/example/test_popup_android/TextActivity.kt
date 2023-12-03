package com.example.test_popup_android

import android.app.Activity
import android.app.ActivityOptions
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Rect
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import io.flutter.Log


class TextActivity: Activity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("TextActivity", "!!!!! onCreate $intent")
        var selectedText= ""

        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            selectedText = intent.getStringExtra("android.intent.extra.PROCESS_TEXT")!!
            Log.d("TextActivity", "!!! ACTION_PROCESS_TEXT $selectedText")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                val intent = applicationContext.packageManager.getLaunchIntentForPackage("com.example.test_popup_android")?.apply {
//                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK)
                    putExtra("android.intent.extra.PROCESS_TEXT", selectedText)
                }

                //val intent = packageManager.getLaunchIntentForPackage("com.example.test_popup_android")

                val mBounds = Rect(300, 0, 500, 650)
                var activityOptions = ActivityOptions.makeBasic()

                try {
                    val method = ActivityOptions::class.java.getMethod("setLaunchWindowingMode", Int::class.javaPrimitiveType)
                    method.invoke(activityOptions, 5)
                } catch (_: Exception) {}
                activityOptions = activityOptions.setLaunchBounds(mBounds)
                startActivity(intent, activityOptions.toBundle())

                finish()
            }
        }
    }
}
