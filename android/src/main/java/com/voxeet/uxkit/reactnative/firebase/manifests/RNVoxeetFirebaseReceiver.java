package com.voxeet.uxkit.reactnative.firebase.manifests;

import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.voxeet.sdk.push.center.RemoteMessageFactory;
import com.voxeet.sdk.services.notification.INotificationTokenProvider;
import com.voxeet.sdk.services.notification.NotificationTokenHolderFactory;
import com.voxeet.sdk.utils.AndroidManifest;
import com.voxeet.uxkit.reactnative.firebase.manifests.abstracts.AbstractNotificationReceiver;
import com.voxeet.uxkit.reactnative.firebase.manifests.impls.InvertaseReactNativeFirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.manifests.impls.ZoOrPushNotificationListenerService;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

import java.util.ArrayList;
import java.util.List;

public class RNVoxeetFirebaseReceiver extends FirebaseMessagingService {

    private final static String REACT_NATIVE_PUSH_NOTIFICATIONS = "voxeet_use_zoOr_react_native_push_notifications";
    private final static String REACT_NATIVE_FIREBASE = "voxeet_use_invertase_react-native-firebase";


    private List<AbstractNotificationReceiver> receivers = new ArrayList<>();

    public RNVoxeetFirebaseReceiver() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Context context = getApplicationContext();
        String use_zoOr = AndroidManifest.readMetadata(context, REACT_NATIVE_PUSH_NOTIFICATIONS, "false");
        String use_invertase = AndroidManifest.readMetadata(context, REACT_NATIVE_FIREBASE, "false");

        if ("true".equals(use_zoOr)) {
            receivers.add(new ZoOrPushNotificationListenerService(this));
        }

        if ("true".equals(use_invertase)) {
            receivers.add(new InvertaseReactNativeFirebaseMessagingService(this));
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        INotificationTokenProvider provider = NotificationTokenHolderFactory.provider;
        if (null != provider) {
            provider.log("New notification with body " + remoteMessage.getData());

            boolean managed = RemoteMessageFactory.manageRemoteMessage(getApplicationContext(), remoteMessage.getData());

            provider.log("notification managed := " + managed);
        }

        for (AbstractNotificationReceiver receiver : receivers) {
            try {
                receiver.onMessageReceived(remoteMessage);
            } catch (Exception e) {
                Reflection.log(receiver, "Unable to handle onMessageReceived");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();

        for (AbstractNotificationReceiver receiver : receivers) {
            try {
                receiver.onDeletedMessages();
            } catch (Exception e) {
                Reflection.log(receiver, "Unable to handle onDeletedMessages");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onMessageSent(String messageId) {
        super.onMessageSent(messageId);

        for (AbstractNotificationReceiver receiver : receivers) {
            try {
                receiver.onMessageSent(messageId);
            } catch (Exception e) {
                Reflection.log(receiver, "Unable to handle onMessageSent");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onSendError(String messageId, Exception exception) {
        super.onSendError(messageId, exception);

        for (AbstractNotificationReceiver receiver : receivers) {
            try {
                receiver.onSendError(messageId, exception);
            } catch (Exception e) {
                Reflection.log(receiver, "Unable to handle onSendError");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        for (AbstractNotificationReceiver receiver : receivers) {
            try {
                receiver.onNewToken(token);
            } catch (Exception e) {
                Reflection.log(receiver, "Unable to handle onNewToken");
                e.printStackTrace();
            }
        }
    }
}
