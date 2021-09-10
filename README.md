
# react-native-voxeet-firebase

React native plugin for Voxeet aiming at providing a compatibility between the most used react native FCM libraries.

This plugin is currently compatible with

- `react-native-firebase`
- `react-native-push-notifications`

## Getting started

`$ npm install @voxeet/react-native-voxeet-firebase --save`

## Enable the compatible third party libraries

### react-native-firebase

In your `AndroidManifest.xml` file, add the following meta-data inside the `<application>...</application>` section :

```
<manifest>
  <application>
  	...
		<meta-data android:name="voxeet_use_invertase_react-native-firebase" android:value="true" />
	</application>
</manifest>
```

And no other configuration are required.

### react-native-push-notifications

In your `AndroidManifest.xml` file, add the following meta-data inside the `<application>...</application>` section :

```
<manifest>
  <application>
  	...
		<meta-data android:name="voxeet_use_zoOr_react_native_push_notifications" android:value="true" />
	</application>
</manifest>
```

And no other configuration are required. This plugin takes as well care of the modification that was asked by the developer (which consisted of declaring a `service` block in the `AndroidManifest.xml` file).

### Specific needs

Sometimes, you may need to add compatibility with more services or use a modified version of the above third-party libraries, you may then want to create your own service which manages every libraries (like ours which in turn make this specific library deprecate for your that usage).

However, if you still want to create a specific receiver for those libraries, you can create a standard receiver and referring this in a string-array inside your project :

```
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string-array name="custom_services">
        <!-- copy this file in your app/src/main/res/values/ folder (create it if required) -->
        <!-- then set as many rows as required -->
        <!-- <item>com.fully.package.name.MyReceiver</item>-->
    </string-array>
</resources>
```

## Issues

### Debugging

The library will output its own logs using the `RNVoxeetFirebaseReceiver` filter. You then can debug and send reports using Android Studio or `adb logcat -s RNVoxeetFirebaseReceiver`. It's currently impossible to disable the logs but the library should only print logs when being initialized and receiving interaction with.

### Crashes and errors

Please open a new issue ticket and give as much information as possible regarding the crash :

- device
- OS version
- `adb logcat` results (unfiltered)

### Add new library compatibility

Please open a new issue ticket and give as much information as possible regarding the library to make it compatible with
