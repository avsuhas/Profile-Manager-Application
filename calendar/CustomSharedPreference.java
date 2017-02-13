package eventreminder.calendar;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by suhas on 26/3/16.
 */
public class CustomSharedPreference
{

    public static final String PREFS_NAME = "AOP_PREFS";

    public static CustomSharedPreference self = new CustomSharedPreference();

    public CustomSharedPreference() {
        super();
    }

    public static CustomSharedPreference getInstance()
    {
        return self;
    }

    //save boolean value
    public void put(Context context,boolean value,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1;
        settings.edit().putBoolean(key, value).commit(); //4
    }

    //save int value
    public void put(Context context,int value,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1;
        settings.edit().putInt(key, value).commit(); //4
    }

    //save float value
    public void put(Context context,float value,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1;
        settings.edit().putFloat(key, value).commit(); //4
    }

    //save long value
    public void put(Context context, long value,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1;
        settings.edit().putLong(key, value).commit(); //4
    }

    //save string value
    public void put(Context context,String value,String key) {
        value = value != null ? value : "";
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE); //1;
        settings.edit().putString(key, value).commit(); //4
    }

    //get int value
    public boolean getBoolean(Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean value = settings.getBoolean(key, false);

        return value;
    }

    //get int value
    public int getInt(Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int value = settings.getInt(key, 0);

        return value;
    }

    //get float value
    public float getFloat(Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        float value = settings.getFloat(key, 0);

        return value;
    }

    //get int value
    public long getLong(Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        long value = settings.getLong(key, 0);

        return value;
    }

    //get int value
    public String getString(Context context,String key) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String value = settings.getString(key, "");

        return value;
    }


    public void clearSharedPreference(Context context) {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);;
        settings.edit().clear().commit();
    }

    public void remove(Context context, String key)
    {
        SharedPreferences settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);;
        settings.edit().remove(key).commit();
    }
}
