package eventreminder.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

public class CallAndMessageSettingsActivity extends AppCompatActivity {

    Switch meetingCallSwitch,meetingMsgSwitch,birthDayCallSwitch,birthDayMsgSwitch,marriageCallSwitch,marriageMsgSwitch,othersCallSwitch,othersMsgSwitch;

    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_message_settings);
        //to display back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivityResources();
    }

    private void getActivityResources()
    {
        meetingCallSwitch   = (Switch) findViewById(R.id.settings_meetingcallSwitch);
        meetingMsgSwitch    = (Switch) findViewById(R.id.settings_meetingMsgSwitch);
        birthDayCallSwitch  = (Switch) findViewById(R.id.settings_birthDaycallSwitch);
        birthDayMsgSwitch   = (Switch) findViewById(R.id.settings_birthDayMsgSwitch);
        marriageCallSwitch  = (Switch) findViewById(R.id.settings_marriagecallSwitch);
        marriageMsgSwitch   = (Switch) findViewById(R.id.settings_marriageMsgSwitch);
        othersCallSwitch    = (Switch) findViewById(R.id.settings_otherscallSwitch);
        othersMsgSwitch     = (Switch) findViewById(R.id.settings_othersMsgSwitch);

        meetingCallSwitch.setOnCheckedChangeListener(onChecked);
        meetingMsgSwitch.setOnCheckedChangeListener(onChecked);
        birthDayCallSwitch.setOnCheckedChangeListener(onChecked);
        birthDayMsgSwitch.setOnCheckedChangeListener(onChecked);
        marriageCallSwitch.setOnCheckedChangeListener(onChecked);
        marriageMsgSwitch.setOnCheckedChangeListener(onChecked);
        othersCallSwitch.setOnCheckedChangeListener(onChecked);
        othersMsgSwitch.setOnCheckedChangeListener(onChecked);

        sharedPref  = CallAndMessageSettingsActivity.this.getSharedPreferences("pref",Context.MODE_PRIVATE);
        editor      = sharedPref.edit();

        setSwitchButtonValue();
    }


    private CompoundButton.OnCheckedChangeListener onChecked = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

            if(buttonView.getId() == meetingCallSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.MEETING_CALL), isChecked);
                editor.commit();
            }
            else if(buttonView.getId() == meetingMsgSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.MEETING_MSG),isChecked);
                editor.commit();

            }
            else if(buttonView.getId() == birthDayCallSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.BIRTHDAY_CALL),isChecked);
                editor.commit();

            }
            else if(buttonView.getId() == birthDayMsgSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.BIRTHDAY_MSG),isChecked);
                editor.commit();

            }
            else if(buttonView.getId() == marriageCallSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.MARRIAGE_CALL),isChecked);
                editor.commit();

            }
            else if(buttonView.getId() == marriageMsgSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.MARRIAGE_MSG),isChecked);
                editor.commit();

            }
            else if(buttonView.getId() == othersCallSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.OTHERS_CALL),isChecked);
                editor.commit();
            }
            else if(buttonView.getId() == othersMsgSwitch.getId())
            {
                editor.putBoolean(getResources().getString(R.string.OTHERS_MSG),isChecked);
                editor.commit();
            }

        }
    };


    private void setSwitchButtonValue()
    {

        meetingCallSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.MEETING_CALL), false));
        meetingMsgSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.MEETING_MSG),false));
        birthDayCallSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.BIRTHDAY_CALL),false));
        birthDayMsgSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.BIRTHDAY_MSG),false));
        marriageCallSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.MARRIAGE_CALL),false));
        marriageMsgSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.MARRIAGE_MSG),false));
        othersCallSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.OTHERS_CALL),false));
        othersMsgSwitch.setChecked(sharedPref.getBoolean(CallAndMessageSettingsActivity.this.getResources().getString(R.string.OTHERS_MSG),false));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);

    }
}
