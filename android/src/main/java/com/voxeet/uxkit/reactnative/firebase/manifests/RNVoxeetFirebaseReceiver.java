package com.voxeet.uxkit.reactnative.firebase.manifests;

import android.app.Service;
import android.content.Context;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.voxeet.sdk.push.center.RemoteMessageFactory;
import com.voxeet.sdk.services.notification.INotificationTokenProvider;
import com.voxeet.sdk.services.notification.NotificationTokenHolderFactory;
import com.voxeet.sdk.utils.AndroidManifest;
import com.voxeet.uxkit.reactnative.firebase.manifests.abstracts.AbstractNotificationReceiver;
import com.voxeet.uxkit.reactnative.firebase.manifests.impls.CustomServicesReactNativeFirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.manifests.impls.InvertaseReactNativeFirebaseMessagingService;
import com.voxeet.uxkit.reactnative.firebase.manifests.impls.ZoOrPushNotificationListenerService;
import com.voxeet.uxkit.reactnative.firebase.utils.Logging;
import com.voxeet.uxkit.reactnative.firebase.utils.Reflection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RNVoxeetFirebaseReceiver extends FirebaseMessagingService {

    private final static String TAG = RNVoxeetFirebaseReceiver.class.getSimpleName();
    private final static String REACT_NATIVE_PUSH_NOTIFICATIONS = "voxeet_use_zoOr_react_native_push_notifications";
    private final static String REACT_NATIVE_FIREBASE = "voxeet_use_invertase_react-native-firebase";


    private List<AbstractNotificationReceiver> receivers = new ArrayList<>();

    public RNVoxeetFirebaseReceiver() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Logging.d(TAG, "onCreate");

        Context context = getApplicationContext();
        String use_zoOr = AndroidManifest.readMetadata(context, REACT_NATIVE_PUSH_NOTIFICATIONS, "false");
        String use_invertase = AndroidManifest.readMetadata(context, REACT_NATIVE_FIREBASE, "false");

        Logging.d(TAG, "adding a receiver for custom_services services from res/values/services.xml");
        receivers.add(new CustomServicesReactNativeFirebaseMessagingService(this, context));

        if ("true".equals(use_zoOr)) {
            Logging.d(TAG, "zoOr implementation will be forwarded");
            receivers.add(new ZoOrPushNotificationListenerService(this, context));
        } else {
            Logging.d(TAG, "zoOr implementation won't be forwarded");
        }

        if ("true".equals(use_invertase)) {
            Logging.d(TAG, "invertase implementation will be forwarded");
            receivers.add(new InvertaseReactNativeFirebaseMessagingService(this, context));
        } else {
            Logging.d(TAG, "invertase implementation won't be forwarded");
        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        INotificationTokenProvider provider = NotificationTokenHolderFactory.provider;
        Logging.d(TAG, "onMessageReceived " + provider);

        if (null != provider) {
            provider.log("New notification with body " + remoteMessage.getData());

            boolean managed = RemoteMessageFactory.manageRemoteMessage(getApplicationContext(), remoteMessage.getData());

            provider.log("notification managed := " + managed);
        }

        for (AbstractNotificationReceiver receiver : receivers) {
            try {
                Logging.d(TAG, "Forwarding to " + receiver.getClass().getSimpleName());
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
