package com.voxeet.uxkit.reactnative.firebase.manifests.impls;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.manifests.abstracts.AbstractNotificationReceiver;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

import java.util.Arrays;
import java.util.List;

public class ZoOrPushNotificationListenerService extends AbstractNotificationReceiver {
    private final static String KLASS_NAME = "com.dieam.reactnativepushnotification.modules.RNPushNotificationListenerService";

    public ZoOrPushNotificationListenerService(@NonNull FirebaseMessagingService parent, @NonNull Context context) {
        super(parent, context);
    }

    @Nullable
    protected List<FirebaseMessagingService> construct(@NonNull FirebaseMessagingService parent) {
        return Arrays.asList(Reflection.construct(this, KLASS_NAME, parent));
    }
}
