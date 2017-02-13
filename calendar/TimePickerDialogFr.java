package eventreminder.calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;

/**
 * Created by suhas on 03-08-2016.
 */
public class
        TimePickerDialogFr extends DialogFragment implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener
{
    boolean isDatePicker = false;

    onDateTimePicked    csDateTimePickedInterface = null;

    public void setIsDatePicker(onDateTimePicked listener)
    {
        csDateTimePickedInterface = listener;
        this.isDatePicker = true;
    }
    public void setIsTimePicker(onDateTimePicked listener)
    {
        csDateTimePickedInterface = listener;
        this.isDatePicker = false;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        Dialog dialog = null;

        // Create a new instance of TimePickerDialog and return it
        if(isDatePicker)
        {
            dialog = new DatePickerDialog(getActivity(), this, year, month, day);
            /*Calendar c1 = Calendar.getInstance();
            c1.set(year, month, day);
            ((DatePickerDialog)dialog).getDatePicker().setMaxDate(c1.getTimeInMillis());

            c1.set(year, month, day);
            ((DatePickerDialog)dialog).getDatePicker().setMinDate(c1.getTimeInMillis());*/
        }
        else {
            dialog = new TimePickerDialog(getActivity(), this, hour, minute, false);
        }

        return dialog;
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        Calendar datetime = Calendar.getInstance();
        datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
        datetime.set(Calendar.MINUTE, minute);
        datetime.set(Calendar.SECOND,0);

        if(csDateTimePickedInterface != null)
            csDateTimePickedInterface.timeSelected(datetime.getTime().getTime());
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
    {
        Calendar date = Calendar.getInstance();
        date.set(year, monthOfYear, dayOfMonth);


        if (csDateTimePickedInterface != null)
            csDateTimePickedInterface.dateSelected(date.getTime().getTime());
    }

    public interface onDateTimePicked
    {
        void dateSelected(long milliSec);
        void timeSelected(long milliSec);
    }
}
