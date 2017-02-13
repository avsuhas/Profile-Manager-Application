package eventreminder.calendar;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;


public class EventCreateActivity extends AppCompatActivity implements TimePickerDialogFr.onDateTimePicked{

    RelativeLayout                      csStartDateLay                  = null;
    RelativeLayout                      csEndDateLay                    = null;
    TextView                            csStartDate                     = null;
    TextView                            csEndDate                       = null;
    TextView                            csTimeZoneTxt                   = null;
    EditText                            csTitleTxt                      = null;
    EditText                            csLocation                      = null;
    EditText                            csDescription                   = null;

    long                                csDateMillies                   = 0;
    long                                csTimeMillies                   = 0;

    String                              csSelectedDateLay               = null;

    long                                csMillies                       = 0;
    long                                csStartMillies                  = 0;
    long                                csEndMillies                    = 0;

    String                              csSelectedTitle                 = "others";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_create);

        //to display back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getActivityResources();
    }

    private void getActivityResources()
    {

        csStartDateLay      = (RelativeLayout)findViewById(R.id.eventCreate_startDateLay);
        csEndDateLay        = (RelativeLayout) findViewById(R.id.eventCreate_endDateLay);
        csStartDate         = (TextView)findViewById(R.id.eventCreate_startDateTxt);
        csEndDate           = (TextView) findViewById(R.id.eventCreate_endDateTxt);
        csTimeZoneTxt       = (TextView) findViewById(R.id.eventCreate_timeZoneTxt);
        csTitleTxt          = (EditText) findViewById(R.id.eventCreate_titleTxt);
        csLocation          = (EditText) findViewById(R.id.eventCreate_locationTxt);
        csDescription       = (EditText) findViewById(R.id.eventCreate_descriptionTxt);

        setTimeZoneText();

        csTitleTxt.setOnClickListener(onClick);
        csStartDateLay.setOnClickListener(onClick);
        csEndDateLay.setOnClickListener(onClick);
    }


    private View.OnClickListener onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == csStartDateLay.getId()) {
                csSelectedDateLay = "START";
                openDateTimeDialog();
            } else if (v.getId() == csEndDateLay.getId()) {
                csSelectedDateLay = "END";
                openDateTimeDialog();
            }
            else if(v.getId() == csTitleTxt.getId())
            {
                openTitleDialog();
            }
        };

        private void openTitleDialog()
        {

            RadioGroup          csRadioGroup            = null;

            Button              csOkBtn                 = null;
            Button              csCloseBtn              = null;



            final Dialog dialog = new Dialog(EventCreateActivity.this);


            //dialog.requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
            dialog.setContentView(R.layout.event_title_dialog);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(true);
            dialog.setTitle("Title");
            // csMainLay.setAlpha(0.9f);


            csRadioGroup    = (RadioGroup)dialog.findViewById(R.id.eventTitleDialog_radioGroup);
            csOkBtn         = (Button)dialog.findViewById(R.id.eventTitleDialog_okBtn);
            csCloseBtn      = (Button)dialog.findViewById(R.id.eventTitleDialog_cancelBtn);
            csRadioGroup.clearCheck();
            final RadioGroup finalCsRadioGroup = csRadioGroup;
            csRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {

                    RadioButton csRadioBtn = (RadioButton) finalCsRadioGroup.findViewById(finalCsRadioGroup.getCheckedRadioButtonId());
                    csSelectedTitle = csRadioBtn.getText().toString();

                }
            });

            csOkBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (csSelectedTitle != null) {
                        csTitleTxt.setText(csSelectedTitle);

                    }
                    dialog.dismiss();
                }

            });
            csCloseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }

        private void openDateTimeDialog() {
            TimePickerDialogFr csTimePickerDialog = new TimePickerDialogFr();

            csTimePickerDialog.setIsTimePicker(EventCreateActivity.this);
            if (csTimePickerDialog.isVisible() == false)
                csTimePickerDialog.show(getSupportFragmentManager(), "TimePicker");


            TimePickerDialogFr csTimePickerDialog1 = new TimePickerDialogFr();
            csTimePickerDialog1.setIsDatePicker(EventCreateActivity.this);
            if (csTimePickerDialog1.isVisible() == false)
                csTimePickerDialog1.show(getSupportFragmentManager(), "DatePicker");

        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater =  getMenuInflater();
        menuInflater.inflate(R.menu.create_event, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.createEvent)
        {
            addEvent();
        }
        super.onBackPressed();
        return super.onOptionsItemSelected(item);
    }
    private void addEvent()
    {
        ContentValues l_event = new ContentValues();
        l_event.put("calendar_id", 1);
        l_event.put("title", csTitleTxt.getText().toString());
        l_event.put("description", csDescription.getText().toString());
        l_event.put("eventLocation", csLocation.getText().toString());
        l_event.put("dtstart", csStartMillies);
        l_event.put("dtend", csEndMillies);
        l_event.put("allDay", 0);
        l_event.put("rrule", "FREQ=YEARLY");
        // status: 0~ tentative; 1~ confirmed; 2~ canceled
        // l_event.put("eventStatus", 1);

        l_event.put("eventTimezone", "India");
        Uri l_eventUri;
        if (Build.VERSION.SDK_INT >= 8) {
            l_eventUri = Uri.parse("content://com.android.calendar/events");
        } else {
            l_eventUri = Uri.parse("content://calendar/events");
        }
        Uri l_uri = EventCreateActivity.this.getContentResolver()
                .insert(l_eventUri, l_event);
        startAlert();
        endAlert();
    }

    private void endAlert()
    {
        Intent intent   =  new Intent(this,MyBroadcastReceiver.class);
        intent.putExtra("title", csSelectedTitle);
        intent.putExtra("type","END");
        Random  r =  new Random();
        int n = r.nextInt(100);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), n, intent, 0);
        AlarmManager alarmManager   = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,csEndMillies,pendingIntent);

    }

    private void startAlert()
    {
        Intent intent   =  new Intent(this,MyBroadcastReceiver.class);
        intent.putExtra("title", csSelectedTitle);
        intent.putExtra("type","START");
        Random  r =  new Random();
        int n = r.nextInt(100);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), n, intent, 0);
        AlarmManager alarmManager   = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,csStartMillies,pendingIntent);
    }


    @Override
    public void dateSelected(long milliSec) {
        csDateMillies = milliSec;
    }

    @Override
    public void timeSelected(long milliSec) {
        csTimeMillies = milliSec;
        dateTime();
    }

    private void dateTime()
    {
        Calendar c= Calendar.getInstance();
        c.setTimeInMillis(csDateMillies);

        Calendar c1= Calendar.getInstance();
        c1.setTimeInMillis(csTimeMillies);

        c.set(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), c1.get(Calendar.HOUR_OF_DAY), c1.get(Calendar.MINUTE));
        csMillies = c.getTimeInMillis();
        String timeString = new SimpleDateFormat("dd-MM-yyyy hh:mm a").format(new Date(csMillies));


        if(csSelectedDateLay.equals("START"))
        {
            csStartDate.setText(timeString);
            csStartMillies = csMillies;
        }
        else if(csSelectedDateLay.equals("END"))
        {
            csEndDate.setText(timeString);
            csEndMillies = csMillies;
        }
    }


    private void setTimeZoneText()
    {
        TimeZone tz = TimeZone.getDefault();

        long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
        long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
                - TimeUnit.HOURS.toMinutes(hours);

        String timeZoneString = String.format("(GMT +%d:%02d) %s", hours,
                minutes, tz.getID());

        csTimeZoneTxt.setText(timeZoneString);
    }

}

