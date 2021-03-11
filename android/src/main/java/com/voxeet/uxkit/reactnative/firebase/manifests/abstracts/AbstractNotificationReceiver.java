package com.voxeet.uxkit.reactnative.firebase.manifests.abstracts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public abstract class AbstractNotificationReceiver {

    @Nullable
    private FirebaseMessagingService messagingService;

    protected AbstractNotificationReceiver(@NonNull FirebaseMessagingService parent) {
        this.messagingService = construct(parent);
    }

    public final void onSendError(String messageId, Exception sendError) {
        getService().onSendError(messageId, sendError);
    }

    public final void onDeletedMessages() {
        getService().onDeletedMessages();
    }

    public final void onMessageSent(String messageId) {
        getService().onMessageSent(messageId);
    }

    public final void onNewToken(@NonNull String token) {
        getService().onNewToken(token);
    }

    public final void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        getService().onMessageReceived(remoteMessage);
    }

    protected abstract FirebaseMessagingService construct(@NonNull FirebaseMessagingService parent);

    @Nullable
    private FirebaseMessagingService getService() {
        return messagingService;
    }
}
