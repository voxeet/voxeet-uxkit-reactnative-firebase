package com.voxeet.uxkit.reactnative.firebase;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;

public class RNVoxeetFirebaseModule extends ReactContextBaseJavaModule {

  private final ReactApplicationContext reactContext;

  public RNVoxeetFirebaseModule(ReactApplicationContext reactContext) {
    super(reactContext);
    this.reactContext = reactContext;
  }

  @Override
  public String getName() {
    return "RNVoxeetFirebase";
  }
}