package eventreminder.calendar;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class ProfileSettingsActivity extends AppCompatActivity {

    RadioGroup                  csMeetingGroup              = null;
    RadioGroup                  csBirthDayGroup             = null;
    RadioGroup                  csMarriageGroup             = null;
    RadioGroup                  csOthersGroup               = null;


    SharedPreferences sharedPref = null;
    SharedPreferences.Editor editor = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_settings);
        //to display back button
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getActivityResources();
    }

    private void getActivityResources()
    {
        csMeetingGroup = (RadioGroup) findViewById(R.id.meetingGroup);
        csMarriageGroup = (RadioGroup) findViewById(R.id.marriageGroup);
        csBirthDayGroup = (RadioGroup) findViewById(R.id.birthDayGroup);
        csOthersGroup = (RadioGroup) findViewById(R.id.othersGroup);

        csMeetingGroup.setOnCheckedChangeListener(onChecked);
        csMarriageGroup.setOnCheckedChangeListener(onChecked);
        csBirthDayGroup.setOnCheckedChangeListener(onChecked);
        csOthersGroup.setOnCheckedChangeListener(onChecked);

        sharedPref  = ProfileSettingsActivity.this.getSharedPreferences("pref", Context.MODE_PRIVATE);
        editor      = sharedPref.edit();


        setCheckedButton();

    }
    private RadioGroup.OnCheckedChangeListener onChecked = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if(group.getId() == R.id.meetingGroup)
            {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getId() == R.id.meetingRingBtn)
                {
                    editor.putString(getResources().getString(R.string.MEETING_PROFILE), getResources().getString(R.string.GENERAL));
                    editor.commit();
                }

                else if(rb.getId() == R.id.meetingVibrateBtn)
                {
                    editor.putString(getResources().getString(R.string.MEETING_PROFILE),getResources().getString(R.string.VIBRATE));
                    editor.commit();
                }
                else if(rb.getId() == R.id.meetingSilentBtn)
                {
                    editor.putString(getResources().getString(R.string.MEETING_PROFILE),getResources().getString(R.string.SILENT));
                    editor.commit();
                }
            }
            else if(group.getId() == R.id.marriageGroup)
            {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getId() == R.id.marriageRingBtn)
                {
                    editor.putString(getResources().getString(R.string.MARRIAGE_PROFILE), getResources().getString(R.string.GENERAL));
                    editor.commit();
                }

                else if(rb.getId() == R.id.marriageVibrateBtn)
                {
                    editor.putString(getResources().getString(R.string.MARRIAGE_PROFILE),getResources().getString(R.string.VIBRATE));
                    editor.commit();
                }
                else if(rb.getId() == R.id.marriageSilentBtn)
                {
                    editor.putString(getResources().getString(R.string.MARRIAGE_PROFILE),getResources().getString(R.string.SILENT));
                    editor.commit();
                }
            }

            else if(group.getId() == R.id.birthDayGroup)
            {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getId() == R.id.birthDayRingBtn)
                {
                    editor.putString(getResources().getString(R.string.BIRTHDAY_PROFILE), getResources().getString(R.string.GENERAL));
                    editor.commit();
                }

                else if(rb.getId() == R.id.birthDayVibrateBtn)
                {
                    editor.putString(getResources().getString(R.string.BIRTHDAY_PROFILE),getResources().getString(R.string.VIBRATE));
                    editor.commit();
                }
                else if(rb.getId() == R.id.birthDaySilentBtn)
                {
                    editor.putString(getResources().getString(R.string.BIRTHDAY_PROFILE),getResources().getString(R.string.SILENT));
                    editor.commit();
                }
            }

            else if(group.getId() == R.id.othersGroup)
            {
                RadioButton rb=(RadioButton)findViewById(checkedId);
                if(rb.getId() == R.id.othersRingBtn)
                {
                    editor.putString(getResources().getString(R.string.OTHERS_PROFILE), getResources().getString(R.string.GENERAL));
                    editor.commit();
                }

                else if(rb.getId() == R.id.othersVibrateBtn)
                {
                    editor.putString(getResources().getString(R.string.OTHERS_PROFILE),getResources().getString(R.string.VIBRATE));
                    editor.commit();
                }
                else if(rb.getId() == R.id.othersSilentBtn)
                {
                    editor.putString(getResources().getString(R.string.OTHERS_PROFILE),getResources().getString(R.string.SILENT));
                    editor.commit();
                }
            }
        }
    };


    private void setCheckedButton()
    {
        String meetingProfile = sharedPref.getString(ProfileSettingsActivity.this.getResources().getString(R.string.MEETING_PROFILE), "VIBRATE");
        String marriageProfile = sharedPref.getString(ProfileSettingsActivity.this.getResources().getString(R.string.MARRIAGE_PROFILE), "VIBRATE");
        String birthDayProfile = sharedPref.getString(ProfileSettingsActivity.this.getResources().getString(R.string.BIRTHDAY_PROFILE), "VIBRATE");
        String othersProfile = sharedPref.getString(ProfileSettingsActivity.this.getResources().getString(R.string.OTHERS_PROFILE), "VIBRATE");
        if(meetingProfile.equalsIgnoreCase(getResources().getString(R.string.GENERAL)))
        {
            csMeetingGroup.check(R.id.meetingRingBtn);
        }
        else if (meetingProfile.equalsIgnoreCase(getResources().getString(R.string.VIBRATE)))
        {
            csMeetingGroup.check(R.id.meetingVibrateBtn);
        }
        else if(meetingProfile.equalsIgnoreCase(getResources().getString(R.string.SILENT)))
        {
            csMeetingGroup.check(R.id.meetingSilentBtn);
        }



        if(marriageProfile.equalsIgnoreCase(getResources().getString(R.string.GENERAL)))
        {
            csMarriageGroup.check(R.id.marriageRingBtn);
        }
        else if (marriageProfile.equalsIgnoreCase(getResources().getString(R.string.VIBRATE)))
        {
            csMarriageGroup.check(R.id.marriageVibrateBtn);
        }
        else if(marriageProfile.equalsIgnoreCase(getResources().getString(R.string.SILENT)))
        {
            csMarriageGroup.check(R.id.marriageSilentBtn);
        }




        if(birthDayProfile.equalsIgnoreCase(getResources().getString(R.string.GENERAL)))
        {
            csBirthDayGroup.check(R.id.birthDayRingBtn);
        }
        else if (birthDayProfile.equalsIgnoreCase(getResources().getString(R.string.VIBRATE)))
        {
            csBirthDayGroup.check(R.id.birthDayVibrateBtn);
        }
        else if(birthDayProfile.equalsIgnoreCase(getResources().getString(R.string.SILENT)))
        {
            csBirthDayGroup.check(R.id.birthDaySilentBtn);
        }



        if(othersProfile.equalsIgnoreCase(getResources().getString(R.string.GENERAL)))
        {
            csOthersGroup.check(R.id.othersRingBtn);
        }
        else if (othersProfile.equalsIgnoreCase(getResources().getString(R.string.VIBRATE)))
        {
            csOthersGroup.check(R.id.othersVibrateBtn);
        }
        else if(othersProfile.equalsIgnoreCase(getResources().getString(R.string.SILENT)))
        {
            csOthersGroup.check(R.id.othersSilentBtn);
        }
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
