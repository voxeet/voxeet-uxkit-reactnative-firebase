package com.voxeet.uxkit.reactnative.firebase.manifests.impls;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.manifests.abstracts.AbstractNotificationReceiver;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

public class InvertaseReactNativeFirebaseMessagingService extends AbstractNotificationReceiver {
    private final static String KLASS_NAME = "io.invertase.firebase.messaging.ReactNativeFirebaseMessagingService";

    public InvertaseReactNativeFirebaseMessagingService(@NonNull FirebaseMessagingService parent) {
        super(parent);
    }

    @Nullable
    protected FirebaseMessagingService construct(@NonNull FirebaseMessagingService parent) {
        return Reflection.construct(this, KLASS_NAME);
    }
}
