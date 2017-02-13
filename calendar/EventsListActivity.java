
package eventreminder.calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class EventsListActivity extends AppCompatActivity {

    ListView                    csListView              = null;
    MyListAdapter               csAdapter               = null;

    ArrayList<ArrayList>               eventsArr               = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getActivityResources();
    }

    private void getActivityResources() {


        csListView  = (ListView) findViewById(R.id.events_listView);
        csAdapter   = new MyListAdapter();

        getCalenderEvents();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private class MyListAdapter extends BaseAdapter
    {

        @Override
        public int getCount() {

            //add your json array count here the return value creaer no of list items
            //here i will return 5 so it creates only 20 list items

            return eventsArr.size();
            //return 20;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


        private class Holder
        {
            ImageView       angleImg        = null;
            TextView        name            = null;
            TextView        description     = null;
            TextView        date          = null;

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            Holder csHolder = null;


            if(convertView != null)
            {
                csHolder = (Holder) convertView.getTag();
            }
            else
            {
                csHolder = new Holder();
                LayoutInflater inflater = (LayoutInflater) EventsListActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView             = inflater.inflate(R.layout.events_list_custom_design,null,false);

                csHolder.angleImg       = (ImageView) convertView.findViewById(R.id.eventsListCustom_listImg);
                csHolder.name           = (TextView) convertView.findViewById(R.id.eventsListCustom_NameTxt);
                csHolder.description    = (TextView) convertView.findViewById(R.id.eventsListCustom_descriptionTxt);
                csHolder.date           = (TextView) convertView.findViewById(R.id.eventsListCustom_dateTxt);

                convertView.setTag(csHolder);
            }

            ArrayList<String>   event = new ArrayList<>();

            event = eventsArr.get(position);

            csHolder.name.setText(event.get(0).isEmpty() ? "No title available" : event.get(0));
            csHolder.description.setText(event.get(1).isEmpty()?"No description available":event.get(1));
            csHolder.date.setText(event.get(2)== null?"No date":event.get(2));

            csListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    openInfoDialog(position);
                }
            });

            return convertView;
        }
    }

    private void openInfoDialog(int position)
    {
        TextView titleTxt,startTxt,endTxt,descriptionTxt,locationTxt;
        final Dialog dialog = new Dialog(EventsListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.event_info_dialog);

        titleTxt = (TextView)dialog.findViewById(R.id.eventInfo_TitleTxt);
        startTxt = (TextView)dialog.findViewById(R.id.eventInfo_StartTxt);
        endTxt = (TextView)dialog.findViewById(R.id.eventInfo_EndTxt);
        locationTxt = (TextView)dialog.findViewById(R.id.eventInfo_LocationTxt);
        descriptionTxt = (TextView)dialog.findViewById(R.id.eventInfo_DescriptionTxt);


        //custamizing dialog dimentions
        int width = (int)(getResources().getDisplayMetrics().widthPixels*1);
        int height = (int)(WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setLayout(width, height);

        ArrayList<String>   event = new ArrayList<>();

        event = eventsArr.get(position);

        titleTxt.setText(event.get(0));
        descriptionTxt.setText(event.get(1));
        startTxt.setText(event.get(2));
        endTxt.setText(event.get(3) == null ? "-" : convetTimeStamp(event.get(3)));
        locationTxt.setText(event.get(4));

        dialog.setCancelable(true);
        dialog.show();


    }


    private void getCalenderEvents()
    {
        CalenderEventsUtility util = new CalenderEventsUtility();

        eventsArr = new ArrayList<ArrayList>();
        eventsArr = util.readCalendarEvent(EventsListActivity.this);

        csListView.setAdapter(csAdapter);
        csAdapter.notifyDataSetChanged();
    }


    private String convetTimeStamp(String time) {
        Calendar cal = Calendar.getInstance();
        if(time!=null) {
            long Time = Long.parseLong(time);
            cal.setTimeInMillis(Time * 1000);
        }
            String date = DateFormat.format("dd-MM-yyyy  hh:mm a", cal).toString();


        return date;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        /*Intent intent = new Intent(this,HomeActivity.class);
        startActivity(intent);*/
        super.onBackPressed();
        return super.onOptionsItemSelected(item);

    }

}
