package com.voxeet.uxkit.reactnative.firebase.manifests.impls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.manifests.abstracts.AbstractNotificationReceiver;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

public class ZoOrPushNotificationListenerService extends AbstractNotificationReceiver {
    private final static String KLASS_NAME = "com.dieam.reactnativepushnotification.modules.RNPushNotificationListenerService";

    public ZoOrPushNotificationListenerService(@NonNull FirebaseMessagingService parent) {
        super(parent);
    }

    @Nullable
    protected FirebaseMessagingService construct(@NonNull FirebaseMessagingService parent) {
        return Reflection.construct(this, KLASS_NAME, parent);
    }
}
