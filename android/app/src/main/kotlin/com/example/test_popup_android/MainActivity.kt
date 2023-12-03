package com.example.test_popup_android

import android.app.ActivityManager
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import io.flutter.Log
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.MethodChannel


class MainActivity: FlutterActivity() {
    private val channelName = "sample.text"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("MainActivity", "!!!!! onCreate $intent")

        //var actionProcessText = false
        val startedInMultiWindow = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            isInMultiWindowMode
        } else {
            false
        }

        val deviceFreeFormSupported = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            packageManager.hasSystemFeature(PackageManager.FEATURE_FREEFORM_WINDOW_MANAGEMENT) ||
                    Settings.Global.getInt(contentResolver, "enable_freeform_support", 0) != 0
        } else {
            false
        }

        val selectedText = intent.getStringExtra("android.intent.extra.PROCESS_TEXT") ?: ""

        if (selectedText.isNotEmpty()) {
            Log.d("MainActivity", "!!! ACTION_PROCESS_TEXT $selectedText")
        }

        val activityManager = getSystemService(ACTIVITY_SERVICE) as ActivityManager
        val isLowRamDevice = activityManager.isLowRamDevice

        MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, channelName).invokeMethod(
                "sendParams",
                mapOf("selectedText" to selectedText,
//                        "actionProcessText" to actionProcessText,
                        "deviceFreeFormSupported" to deviceFreeFormSupported,
                        "startedInMultiWindow" to startedInMultiWindow,
                        "isLowRamDevice" to isLowRamDevice
                ))
    }
}
