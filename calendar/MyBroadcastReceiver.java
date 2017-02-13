package eventreminder.calendar;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.telephony.SmsManager;
import android.util.Log;

import java.util.Random;

/**
 * Created by suhas on 19-08-2016.
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Log.d("============","enter");

        SharedPreferences sharedPref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);

        boolean meetingCall = sharedPref.getBoolean(context.getResources().getString(R.string.MEETING_CALL), false);
        boolean meetingMsg = sharedPref.getBoolean(context.getResources().getString(R.string.MEETING_MSG), false);
        boolean birthDayCall = sharedPref.getBoolean(context.getResources().getString(R.string.BIRTHDAY_CALL), false);
        boolean birthDayMsg = sharedPref.getBoolean(context.getResources().getString(R.string.BIRTHDAY_MSG), false);
        boolean marriageCall = sharedPref.getBoolean(context.getResources().getString(R.string.MARRIAGE_CALL), false);
        boolean marriageMsg = sharedPref.getBoolean(context.getResources().getString(R.string.MARRIAGE_MSG), false);
        boolean otersCall   = sharedPref.getBoolean(context.getResources().getString(R.string.OTHERS_CALL), false);
        boolean othersMsg = sharedPref.getBoolean(context.getResources().getString(R.string.OTHERS_MSG), false);

        String meetingProfile = sharedPref.getString(context.getResources().getString(R.string.MEETING_PROFILE), "VIBRATE");
        String marriageProfile = sharedPref.getString(context.getResources().getString(R.string.MARRIAGE_PROFILE), "VIBRATE");
        String birthDayProfile = sharedPref.getString(context.getResources().getString(R.string.BIRTHDAY_PROFILE), "VIBRATE");
        String othersProfile = sharedPref.getString(context.getResources().getString(R.string.OTHERS_PROFILE), "VIBRATE");



        Log.d("============",""+meetingCall);
        Log.d("============",""+meetingMsg);
        Log.d("============",""+birthDayCall);
        Log.d("============",""+birthDayMsg);
        Log.d("============",""+marriageCall);
        Log.d("============",""+marriageMsg);
        Log.d("============",""+otersCall);
        Log.d("============",""+othersMsg);

        Log.d("=============",""+meetingProfile);
        Log.d("=============",""+marriageProfile);
        Log.d("=============",""+birthDayProfile);
        Log.d("=============",""+othersProfile);


        //adding event status
        addStatusToPref(context, "NORMAL");

        Random random = new Random();
        int n= random.nextInt(100);
        if(intent.getStringExtra("title").equalsIgnoreCase("MEETING"))
        {
            if(intent.getStringExtra("type").equalsIgnoreCase("START"))
            {
                addStatusToPref(context, "MEETING");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if(meetingProfile.equalsIgnoreCase(context.getResources().getString(R.string.GENERAL)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL));
                else if(meetingProfile.equalsIgnoreCase(context.getResources().getString(R.string.VIBRATE)))
                mobileMode.setRingerMode((AudioManager.RINGER_MODE_VIBRATE));
                else if(meetingProfile.equalsIgnoreCase(context.getResources().getString(R.string.SILENT)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_SILENT));

            }

            else if(intent.getStringExtra("type").equalsIgnoreCase("END"))
            {
                removeStatusFromPref(context, "MEETING");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL
                ));
            }

        }

        else if(intent.getStringExtra("title").equalsIgnoreCase("MARRIAGE"))
        {
            if(intent.getStringExtra("type").equalsIgnoreCase("START"))
            {
                addStatusToPref(context, "MARRIAGE");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if(marriageProfile.equalsIgnoreCase(context.getResources().getString(R.string.GENERAL)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL));
                else if(marriageProfile.equalsIgnoreCase(context.getResources().getString(R.string.VIBRATE)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_VIBRATE));
                else if(marriageProfile.equalsIgnoreCase(context.getResources().getString(R.string.SILENT)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_SILENT));

            }

            else if(intent.getStringExtra("type").equalsIgnoreCase("END"))
            {
                removeStatusFromPref(context, "MARRIAGE");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL
                ));
            }

        }
        else if(intent.getStringExtra("title").equalsIgnoreCase("BIRTHDAY"))
        {
            if(intent.getStringExtra("type").equalsIgnoreCase("START"))
            {
                addStatusToPref(context, "BIRTHDAY");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if(birthDayProfile.equalsIgnoreCase(context.getResources().getString(R.string.GENERAL)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL));
                else if(birthDayProfile.equalsIgnoreCase(context.getResources().getString(R.string.VIBRATE)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_VIBRATE));
                else if(birthDayProfile.equalsIgnoreCase(context.getResources().getString(R.string.SILENT)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_SILENT));

            }

            else if(intent.getStringExtra("type").equalsIgnoreCase("END"))
            {
                removeStatusFromPref(context, "BIRTHDAY");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL
                ));
            }

        }
        else if(intent.getStringExtra("title").equalsIgnoreCase("OTHERS"))
        {
            if(intent.getStringExtra("type").equalsIgnoreCase("START"))
            {
                addStatusToPref(context, "OTHERS");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                if(othersProfile.equalsIgnoreCase(context.getResources().getString(R.string.GENERAL)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL));
                else if(othersProfile.equalsIgnoreCase(context.getResources().getString(R.string.VIBRATE)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_VIBRATE));
                else if(othersProfile.equalsIgnoreCase(context.getResources().getString(R.string.SILENT)))
                    mobileMode.setRingerMode((AudioManager.RINGER_MODE_SILENT));

            }

            else if(intent.getStringExtra("type").equalsIgnoreCase("END"))
            {
                removeStatusFromPref(context, "OTHERS");
                AudioManager     mobileMode = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
                mobileMode.setRingerMode((AudioManager.RINGER_MODE_NORMAL
                ));
            }

        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        PendingIntent pendingIntent = PendingIntent.getService(context , n ,intent,PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSuondUri     = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder  =  (NotificationCompat.Builder) new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.logo)
                .setContentTitle(intent.getStringExtra("title") )
                .setContentText(intent.getStringExtra("type"))
                .setAutoCancel(true)
                .setSound(defaultSuondUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager  = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(n, notificationBuilder.build());

    }
    private void addStatusToPref(Context context,String title)
    {
        CustomSharedPreference pref = new CustomSharedPreference();
        pref.put(context,title,"TITLE");

        Log.d("============", title);
    }

    private void removeStatusFromPref(Context context,String title)
    {
        CustomSharedPreference pref = new CustomSharedPreference();
        pref.remove(context,"TITLE");

        Log.d("============", title);
    }

}
