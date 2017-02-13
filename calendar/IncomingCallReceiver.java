package eventreminder.calendar;

/**
 * Created by suhas on 28-08-2016.
 */import java.lang.reflect.Method;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.media.AudioManager;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.util.Log;

public class IncomingCallReceiver extends BroadcastReceiver{
    String incomingNumber="";
    AudioManager audioManager;
    TelephonyManager telephonyManager;

    public void onReceive(Context context, Intent intent) {

        SharedPreferences sharedPref = context.getSharedPreferences("pref",Context.MODE_PRIVATE);

        boolean meetingCall = sharedPref.getBoolean(context.getResources().getString(R.string.MEETING_CALL), false);
        boolean meetingMsg = sharedPref.getBoolean(context.getResources().getString(R.string.MEETING_MSG), false);
        boolean birthDayCall = sharedPref.getBoolean(context.getResources().getString(R.string.BIRTHDAY_CALL), false);
        boolean birthDayMsg = sharedPref.getBoolean(context.getResources().getString(R.string.BIRTHDAY_MSG), false);
        boolean marriageCall = sharedPref.getBoolean(context.getResources().getString(R.string.MARRIAGE_CALL), false);
        boolean marriageMsg = sharedPref.getBoolean(context.getResources().getString(R.string.MARRIAGE_MSG), false);
        boolean otersCall   = sharedPref.getBoolean(context.getResources().getString(R.string.OTHERS_CALL), false);
        boolean othersMsg = sharedPref.getBoolean(context.getResources().getString(R.string.OTHERS_MSG), false);

        Log.d("======","call reciver");
        // Get AudioManager
        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        // Get TelephonyManager
        telephonyManager= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (intent.getAction().equals("android.intent.action.PHONE_STATE"))  {
            String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
            if (state.equals(TelephonyManager.EXTRA_STATE_RINGING))  {
                // Get incoming number
                incomingNumber =  intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
            }
        }
        if(!incomingNumber.equals("")){
            // Get an instance of ContentResolver
            ContentResolver cr=context.getContentResolver();
            // Fetch the matching number
            Cursor numbers=cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,  new  String[]{ContactsContract.CommonDataKinds.Phone.CONTACT_ID,ContactsContract.CommonDataKinds.Phone.NUMBER},  ContactsContract.CommonDataKinds.Phone.NUMBER +"=?", new String[]{incomingNumber},  null);
            Log.d("=============", String.valueOf(incomingNumber));
            CustomSharedPreference pref = new CustomSharedPreference();
            Log.d("============", "type" + pref.getString(context,"TITLE"));

            String status = pref.getString(context,"TITLE");
            if(status != null && status.equalsIgnoreCase("MEETING"))
            {
                Log.d("============", String.valueOf(meetingCall));
                Log.d("============", String.valueOf(meetingMsg));

                if(meetingCall)
                rejectCall();
                if(meetingMsg) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(incomingNumber, null, "sorry im in meeting", null, null);
                }
            }
            else if(status != null && status.equalsIgnoreCase("BIRTHDAY"))
            {
                Log.d("============", String.valueOf(birthDayCall));
                Log.d("============", String.valueOf(birthDayMsg));

                if(birthDayCall)
                    rejectCall();
                if(birthDayMsg) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(incomingNumber, null, "sorry im in birthDay party", null, null);
                }
            }
            else if(status != null && status.equalsIgnoreCase("MARRIAGE"))
            {
                Log.d("============", String.valueOf(marriageCall));
                Log.d("============", String.valueOf(marriageMsg));

                if(marriageCall)
                    rejectCall();
                if(marriageMsg) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(incomingNumber, null, "sorry im in marriage", null, null);
                }
            }
            else if(status != null && status.equalsIgnoreCase("OTHERS"))
            {
                Log.d("============", String.valueOf(otersCall));
                Log.d("============", String.valueOf(othersMsg));

                if(otersCall)
                    rejectCall();
                if(othersMsg) {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(incomingNumber, null, "sorry im busy", null, null);
                }
            }
        }
    }

    private void rejectCall(){

        try {
            // Get the getITelephony() method
            Class<?> classTelephony = Class.forName(telephonyManager.getClass().getName());
            Method method = classTelephony.getDeclaredMethod("getITelephony");
            // Disable access check
            method.setAccessible(true);
            // Invoke getITelephony() to get the ITelephony interface
            Object telephonyInterface = method.invoke(telephonyManager);
            // Get the endCall method from ITelephony
            Class<?> telephonyInterfaceClass =Class.forName(telephonyInterface.getClass().getName());
            Method methodEndCall = telephonyInterfaceClass.getDeclaredMethod("endCall");
            // Invoke endCall()
            methodEndCall.invoke(telephonyInterface);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
}
