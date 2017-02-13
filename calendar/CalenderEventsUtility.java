package eventreminder.calendar;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by suhas on 08-07-2016.
 */
public class CalenderEventsUtility {

    public static ArrayList<String> nameOfEvent = new ArrayList<String>();
    public static ArrayList<String> startDates = new ArrayList<>();
    public static ArrayList<String> endDates = new ArrayList<String>();
    public static ArrayList<String> descriptions = new ArrayList<String>();

    public static ArrayList<ArrayList> mainArrayList    = new ArrayList<>();
    public static ArrayList<ArrayList> readCalendarEvent(Context context) {


        Cursor cursor = context.getContentResolver()
                .query(
                        Uri.parse("content://com.android.calendar/events"),
                        new String[] { "calendar_id", "title", "description",
                                "dtstart", "dtend", "eventLocation" }, null, null, null);
        cursor.moveToFirst();
        // fetching calendars name
        String CNames[] = new String[cursor.getCount()];

        // fetching calendars id
        nameOfEvent.clear();
        startDates.clear();
        endDates.clear();
        descriptions.clear();
        for (int i = 0; i < CNames.length; i++) {

           ArrayList<String> nameOfEvent = new ArrayList<String>();

           // nameOfEvent.add(cursor.getString(1));
          //  startDates.add(getDate(Long.parseLong(cursor.getString(3).trim())));
//            endDates.add(getDate(Long.parseLong(cursor.getString(4).trim())));
          //  descriptions.add(cursor.getString(2));

            nameOfEvent.add(cursor.getString(1));
            nameOfEvent.add(cursor.getString(2));
            nameOfEvent.add(getDate(Long.parseLong(cursor.getString(3).trim())));
            //nameOfEvent.add(getDate(Long.parseLong(cursor.getString(4).trim())));
            nameOfEvent.add(cursor.getString(4));
            nameOfEvent.add(cursor.getString(5));

           /* mainArrayList.add(startDates);
            mainArrayList.add(descriptions);
*/
            CNames[i] = cursor.getString(1);
            cursor.moveToNext();

            mainArrayList.add(nameOfEvent);
        }


       /* Log.d("===========", String.valueOf(nameOfEvent));
        Log.d("===========", String.valueOf(startDates));
        Log.d("===========", String.valueOf(descriptions));*/

        return mainArrayList;
    }

    public static String getDate(long milliSeconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss a");
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
}

