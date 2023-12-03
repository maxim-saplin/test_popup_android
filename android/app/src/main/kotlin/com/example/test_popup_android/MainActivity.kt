package com.example.test_popup_android

import android.content.Intent
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
        var selectedText= ""
        var actionProcessText = false
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

        if (intent.action == Intent.ACTION_PROCESS_TEXT) {
            selectedText = intent.getStringExtra("android.intent.extra.PROCESS_TEXT")!!
            Log.d("MainActivity", "!!! ACTION_PROCESS_TEXT $selectedText")
//            val popupIntent = Intent(this, MainActivity::class.java).apply {
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_MULTIPLE_TASK or Intent.FLAG_ACTIVITY_LAUNCH_ADJACENT)
//            }
//            startActivity(popupIntent)
            actionProcessText = true
        }

        MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, channelName).invokeMethod(
                "sendParams",
                mapOf("selectedText" to selectedText,
                        "actionProcessText" to actionProcessText,
                        "deviceFreeFormSupported" to deviceFreeFormSupported,
                        "startedInMultiWindow" to startedInMultiWindow
                ))

        // MethodChannel(flutterEngine!!.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
        //     if (call.method == "returnSelectedText") {
        //         val selectedText = call.argument<String>("text")
        //         // Do something with the selected text
        //         result.success(selectedText)
        //     } else {
        //         result.notImplemented()
        //     }
        // }
    }
}
