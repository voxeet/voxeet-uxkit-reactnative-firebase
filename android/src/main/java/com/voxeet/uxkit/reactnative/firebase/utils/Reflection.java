package com.voxeet.uxkit.reactnative.firebase.utils;

import android.app.Service;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.voxeet.sdk.push.utils.NotificationHelper;
import com.voxeet.uxkit.firebase.implementation.FirebaseProvider;
import com.voxeet.uxkit.reactnative.firebase.manifests.RNVoxeetFirebaseReceiver;
import com.voxeet.uxkit.reactnative.firebase.manifests.impls.InvertaseReactNativeFirebaseMessagingService;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflection {
    private final static String TAG = RNVoxeetFirebaseReceiver.class.getSimpleName();

    public static void log(@NonNull Object caller, @NonNull String text) {
        String output = String.format("%s :: %s", caller.getClass().getSimpleName(), text);
        Log.d(TAG, output);
    }

    @Nullable
    public static Class getKlass(@NonNull Object caller, @NonNull String name) {
        try {
            return Class.forName(name);
        } catch (ClassNotFoundException e) {
            log(caller, "Unable to fetch class " + name + " because the class does not exist");
            e.printStackTrace();
            return null;
        }
    }

    @Nullable
    public static FirebaseMessagingService construct(@NonNull Object caller, @NonNull String name, @NonNull FirebaseMessagingService parent) {
        Class klass = getKlass(caller, name);
        if (null == klass) {
            log(caller, "Unable to construct " + name + " :: skipped");
            return null;
        }

        try {
            Constructor<?> constructor = klass.getConstructor(FirebaseMessagingService.class);
            return (FirebaseMessagingService) constructor.newInstance(parent);
        } catch (Exception e) {
            log(caller, "Unable to construct " + name + " because the following error occured :");
            e.printStackTrace();
            return null;
        }
    }

    public static FirebaseMessagingService construct(@NonNull Object caller, @NonNull String name) {
        Class klass = getKlass(caller, name);
        if (null == klass) {
            log(caller, "Unable to construct " + name + " :: skipped");
            return null;
        }

        try {
            Constructor<?> constructor = klass.getConstructor();
            return (FirebaseMessagingService) constructor.newInstance();
        } catch (Exception e) {
            log(caller, "Unable to construct " + name + " because the following error occured :");
            e.printStackTrace();
            return null;
        }
    }

    public static void attachBaseContext(Service service, Context context) {
        Logging.d(TAG, "attachBaseContext to " + service.getClass().getSimpleName());

        Class<?> klass = service.getClass();

        while(null != klass) {
            try {
                for (Method declaredMethod : klass.getDeclaredMethods()) {
                    Logging.d(TAG, "method found " + declaredMethod.getName());
                }
                Method method = klass.getDeclaredMethod("attachBaseContext", Context.class);
                method.setAccessible(true);

                method.invoke(service, context);
                Logging.d(TAG, "injection done");
                return;
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            klass = klass.getSuperclass();
        }
    }
}
