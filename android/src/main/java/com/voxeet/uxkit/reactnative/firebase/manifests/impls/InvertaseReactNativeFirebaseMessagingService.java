package com.voxeet.uxkit.reactnative.firebase.manifests.impls;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.manifests.abstracts.AbstractNotificationReceiver;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

import java.util.Arrays;
import java.util.List;

public class InvertaseReactNativeFirebaseMessagingService extends AbstractNotificationReceiver {
    private final static String KLASS_NAME_LONG = "io.invertase.firebase.messaging.ReactNativeFirebaseMessagingService";
    private final static String KLASS_NAME_SHORT = "io.invertase.firebase.messaging.RNFirebaseMessagingService";

    public InvertaseReactNativeFirebaseMessagingService(@NonNull FirebaseMessagingService parent, @NonNull Context context) {
        super(parent, context);
    }

    @Nullable
    protected List<FirebaseMessagingService> construct(@NonNull FirebaseMessagingService parent) {
        return Arrays.asList(Reflection.construct(this, KLASS_NAME_LONG),
                Reflection.construct(this, KLASS_NAME_SHORT));
    }
}
