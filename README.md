Launching Flutter app on Android in a new popup window via `ACTION_PROCESS_TEXT` received from external app. The typical use case is selecting a word in browser (i.e. Chrome) and getting a list of shortcuts (i.e. Copy, Search) and few extra options (such us Translate):

![image](https://github.com/maxim-saplin/test_popup_android/assets/7947027/3c9856dd-95b9-4930-a520-675c1454c3c8)

![image](https://github.com/maxim-saplin/test_popup_android/assets/7947027/4d5b5bce-3e5a-40a3-8872-d3f731250f91)

An alternative to picture-in-picture or overlay.

Pros:
 - Easier to implement
 - Standard window controls (close, maximize)

 Cons:
 - Might not work on many devices
 - Doesn't work on Android emulators

# Enable multi-window free-form mode on Android emulator

```
adb shell settings put global enable_freeform_support 1
adb shell settings put global force_resizable_activities  1
adb shell reboot
```

Multi window mode is disabled on low-ram devices

!! Still was not able to launch app on Android emulators (API 33 and 34), tested works fine on Samsung devices with Android 10 and Android 14

## Checking multi-window free-form mode on Android emulator

```
adb shell settings gut global enable_freeform_support
```

# Changes to `AndroidManifest.xml`

Added new activity to inercept text intent:

```
        <activity android:name=".TextActivity"
            android:exported="true"
            android:label="PPUP" android:theme="@style/LaunchTheme">
            <intent-filter android:label="PPUP">
                <action android:name="android.intent.action.PROCESS_TEXT" />
                <data android:mimeType="text/plain"/>
                <category android:name="android.intent.category.DEFAULT" />
            </intent-filter>
        </activity>
```

Updated default Launch intent:

```
 <activity
        android:name=".MainActivity"
    ...
        android:windowSoftInputMode="adjustResize">

```
