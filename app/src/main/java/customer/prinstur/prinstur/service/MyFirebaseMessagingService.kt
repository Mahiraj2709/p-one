package customer.prinstur.prinstur.service

import android.util.Log

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


/**
 * Created by admin on 12/20/2016.
 */
class MyFirebaseMessagingService : FirebaseMessagingService() {
    internal var payLoad = ""

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage!!.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }

        // Check if message contains a notification payload.
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification.body)
        }

        if (remoteMessage.data.containsKey("type")) {
            /*final int notificationType = Integer.parseInt(remoteMessage.getData().get("type"));
            payLoad = new NotificationUtils().getData(remoteMessage.getData());
            Intent intent = null;

            if (FairRepairApplication.isVisible) {
                Log.i(TAG, notificationType + "");
                Handler mHandler = new Handler(Looper.getMainLooper());
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (notificationType == ApplicationMetadata.NOTIFICATION_REQ_ACCEPTED) {
                            AllMechanic allMechanic = new Gson().fromJson(payLoad, AllMechanic.class);
                            FairRepairApplication.getBus().post(allMechanic);
                        } else if (notificationType == ApplicationMetadata.NOTIFICATION_MECH_FINISHED) {
                            NotificationData notificationData = new Gson().fromJson(payLoad,NotificationData.class);
                            FairRepairApplication.getBus().post(notificationData);
                        }
                    }
                });
            } else {
                intent = new Intent(this, MainActivity.class);
                switch (notificationType) {
                *//*case ApplicationMetadata.NOTIFICATION_NEW_OFFER:
                    intent.putExtra(ApplicationMetadata.NOTIFICATION_DATA, payLoad);
                    intent.putExtra(ApplicationMetadata.NOTIFICATION_TYPE, notificationType);
                    break;*//*
                    case ApplicationMetadata.NOTIFICATION_REQ_ACCEPTED:
                        intent.putExtra(ApplicationMetadata.NOTIFICATION_DATA, payLoad);
                        intent.putExtra(ApplicationMetadata.NOTIFICATION_TYPE, notificationType);
                        break;
                    case ApplicationMetadata.NOTIFICATION_REQ_COMPLETED:
                        break;
                *//*case ApplicationMetadata.NOTIFICATION_SEND_REQ:
                    intent.putExtra(ApplicationMetadata.NOTIFICATION_DATA, payLoad);
                    intent.putExtra(ApplicationMetadata.NOTIFICATION_TYPE, notificationType);
                    break;*//*
                    default:

                }


                // use System.currentTimeMillis() to have a unique ID for the pending intent
                PendingIntent pIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, 0);

                // build notification
                // the addAction re-use the same intent to keep the example short
                Notification n = new Notification.Builder(this)
                        .setContentTitle("FairRepair")
                        .setContentText("You have one new request")
                        .setSmallIcon(R.drawable.ic_login_logo)
                        //.setColor(getColor(R.color.colorPrimary))
                        .setContentIntent(pIntent)
                        .setAutoCancel(true)
                        .build();

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

                notificationManager.notify(0, n);
            }*/


        }
    }

    companion object {

        private val TAG = MyFirebaseMessagingService::class.java.simpleName
    }
}
