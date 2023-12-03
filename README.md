# Enable multi-window free-form mode on Android emulator

```
adb shell settings put global enable_freeform_support 1
adb shell reboot
```

# Checking multi-window free-form mode on Android emulator

```
adb shell settings gut global enable_freeform_support
```

# Changes to `AndroidManifest.xml`

```
 <activity
    ...
            android:windowSoftInputMode="adjustResize">
        <layout
                android:defaultHeight="640dp"
                android:defaultWidth="360dp"
                android:gravity="center" />
        ...
```