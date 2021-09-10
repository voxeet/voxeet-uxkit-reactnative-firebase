package com.voxeet.uxkit.reactnative.firebase.manifests.abstracts;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

import java.util.List;

public abstract class AbstractNotificationReceiver {

    @Nullable
    private List<FirebaseMessagingService> messagingServices;

    protected AbstractNotificationReceiver(@NonNull FirebaseMessagingService parent, @NonNull Context context) {
        this.messagingServices = construct(parent);

        // attach the context to each of those services
        for (FirebaseMessagingService service : messagingServices) {
            if (null == messagingServices || null == service) continue;
            Reflection.attachBaseContext(service, context);
        }
    }

    public final void onSendError(String messageId, Exception sendError) {
        execute(getServices(), service -> service.onSendError(messageId, sendError));
    }

    public final void onDeletedMessages() {
        execute(getServices(), FirebaseMessagingService::onDeletedMessages);
    }

    public final void onMessageSent(String messageId) {
        execute(getServices(), service -> service.onMessageSent(messageId));
    }

    public final void onNewToken(@NonNull String token) {
        execute(getServices(), service -> service.onNewToken(token));
    }

    public final void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        execute(getServices(), service -> service.onMessageReceived(remoteMessage));
    }

    protected abstract List<FirebaseMessagingService> construct(@NonNull FirebaseMessagingService parent);

    @Nullable
    public List<FirebaseMessagingService> getServices() {
        return messagingServices;
    }

    private void execute(@Nullable List<FirebaseMessagingService> services, Execute execute) {
        if (null == services) return;
        for (FirebaseMessagingService service : services) {
            try {
                if (null != service) execute.apply(service);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private interface Execute {
        void apply(FirebaseMessagingService service);
    }
}
