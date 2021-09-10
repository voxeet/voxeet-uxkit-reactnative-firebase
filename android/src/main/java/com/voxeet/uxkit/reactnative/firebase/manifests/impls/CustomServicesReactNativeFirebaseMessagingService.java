package com.voxeet.uxkit.reactnative.firebase.manifests.impls;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.R;
import com.voxeet.uxkit.reactnative.firebase.manifests.abstracts.AbstractNotificationReceiver;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

import java.util.ArrayList;
import java.util.List;

public class CustomServicesReactNativeFirebaseMessagingService extends AbstractNotificationReceiver {

    private final static String TAG = CustomServicesReactNativeFirebaseMessagingService.class.getSimpleName();

    public CustomServicesReactNativeFirebaseMessagingService(@NonNull FirebaseMessagingService parent, @NonNull Context context) {
        super(parent, context);
    }

    @Nullable
    protected List<FirebaseMessagingService> construct(@NonNull FirebaseMessagingService parent) {
        String[] services = parent.getResources().getStringArray(R.array.custom_services);

        List<FirebaseMessagingService> result = new ArrayList<>();
        for (String str : services) {
            FirebaseMessagingService object = Reflection.construct(this, str);
            if (null != object) result.add(object);
        }
        return result;
    }
}
