package com.impetrosys.spideradmin.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.impetrosys.spideradmin.Act_Clientids_requestchangepass;
import com.impetrosys.spideradmin.Act_CloseId_list;
import com.impetrosys.spideradmin.Act_paymentdeposit;
import com.impetrosys.spideradmin.MainActivity;
import com.impetrosys.spideradmin.R;
import com.impetrosys.spideradmin.Act_User_requestlist;
import com.impetrosys.spideradmin.Act_Withdrawals_request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "MyFirebaseMsgService";
    String Subtitle,Type,Image,Title;
    Context context;
    Uri defaultSoundUri;
    int uniqeid;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            JSONObject jsonObject= new JSONObject(remoteMessage.getData());
            try {
                Subtitle= jsonObject.getString("sub_title");
                Type= jsonObject.getString("type");
                Image= jsonObject.getString("image");
                Title= jsonObject.getString("title");
                sendNotification(Title,Subtitle,Image,Type);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use WorkManager.
                scheduleJob();


            } else {
                // Handle message within 10 seconds
                handleNow();
            }

        }

        // Check if message contains a notification payload.
      /*  if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            sendNotification(Title,Subtitle,Image,Type);
        }*/

    }
    @Override
    public void onNewToken(String token) {
        Log.d(TAG, "Refreshed token: " + token);

        sendRegistrationToServer(token);
    }
    private void scheduleJob() {
    }

    /**
     * Handle time allotted to BroadcastReceivers.
     */
    private void handleNow() {
        Log.d(TAG, "Short lived task is done.");
    }

    private void sendRegistrationToServer(String token) {
        // TODO: Implement this method to send token to your app server.
    }
    private void sendNotification( String title, String subtitle, String image, String type) {
        Date date=new Date();
        uniqeid=(int)date.getTime();
        Intent intent=null;
        if (type.equalsIgnoreCase("clientid")){
             intent = new Intent(this, Act_User_requestlist.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }else
        if (type.equalsIgnoreCase("deposit")){
             intent = new Intent(this, Act_paymentdeposit.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else
        if (type.equalsIgnoreCase("withdraw")){
            intent = new Intent(this, Act_Withdrawals_request.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }else
        if (type.equalsIgnoreCase("closeid")){
            intent = new Intent(this, Act_CloseId_list.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }else
        if (type.equalsIgnoreCase("changepassword")){
            intent = new Intent(this, Act_Clientids_requestchangepass.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        }
        else {

            intent = new Intent(this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        }

//

        PendingIntent pendingIntent = PendingIntent.getActivity(this, uniqeid /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);

        String channelId = getString(R.string.channel_id);

      //  Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
       ;

        if(type.equalsIgnoreCase("withdraw")){
            defaultSoundUri = Uri.parse("android.resource://"+getPackageName()+"/raw/notification2");

        }
        else if(type.equalsIgnoreCase("deposit")){
            defaultSoundUri = Uri.parse("android.resource://"+getPackageName()+"/raw/beep_3");
        }
        else {
            defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        }
        NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this, channelId+""+uniqeid)
                        .setSmallIcon(R.drawable.icon)
                        .setContentTitle(Title)
                        .setContentText(Subtitle)
                        .setAutoCancel(true)
                        .setSound(defaultSoundUri, AudioManager.STREAM_NOTIFICATION)
                        .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager mNotificationManager = getSystemService(NotificationManager.class);

            NotificationChannel existingChannel = notificationManager.getNotificationChannel(channelId);

//it will delete existing channel if it exists
            if (existingChannel != null) {
                mNotificationManager.deleteNotificationChannel(channelId);
            }

            NotificationChannel channel = new NotificationChannel(channelId+""+uniqeid,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_HIGH);
            AudioAttributes att = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .build();
            channel.setSound(defaultSoundUri, att);

            if (notificationManager != null) {

                notificationManager.createNotificationChannel(channel);
            }
        }

        notificationManager.notify(uniqeid , notificationBuilder.build());
    }
}