package com.voxeet.uxkit.reactnative.firebase.utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.voxeet.sdk.services.notification.INotificationTokenProvider;
import com.voxeet.sdk.services.notification.NotificationTokenHolderFactory;

public class Logging {
    public static void d(@NonNull String TAG, @NonNull String text) {
        INotificationTokenProvider provider = NotificationTokenHolderFactory.provider;
        if (null != provider) {
            provider.log(TAG + " :: " + text);
        } else {
            Log.d(TAG, text);
        }
    }
}
