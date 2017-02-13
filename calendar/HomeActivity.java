package eventreminder.calendar;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.CalendarMode;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executors;


import eventreminder.calendar.decorators.EventDecorator;
import eventreminder.calendar.decorators.HighlightWeekendsDecorator;
import eventreminder.calendar.decorators.OneDayDecorator;


public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,OnDateSelectedListener {

    MaterialCalendarView            csCalendar              = null;
    FrameLayout                     csFrameLayout           = null;
    OneDayDecorator                 oneDayDecorator         = null;
    boolean                         backButtonCount;
    ArrayList<String>               eventsArr               = null;

    TextView addEvent,monthView,weekView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        getActivityResources();
        getCalenderEvents();
    }

    private void getActivityResources() {
        csCalendar          = (MaterialCalendarView)findViewById(R.id.calendarView);
        monthView           = (TextView)findViewById(R.id.monthView);
        weekView            = (TextView)findViewById(R.id.weekView);
        addEvent            = (TextView)findViewById(R.id.addEvent);
        oneDayDecorator     = new OneDayDecorator();

        // Create a new service client and bind our activity to this service

        csCalendar.setOnDateChangedListener(this);
        csCalendar.setShowOtherDates(MaterialCalendarView.SHOW_ALL);

        Calendar instance = Calendar.getInstance();
        csCalendar.setSelectedDate(instance.getTime());

        Calendar instance1 = Calendar.getInstance();
        instance1.set(instance1.get(Calendar.YEAR), Calendar.JANUARY, 1);

        Calendar instance2 = Calendar.getInstance();
        instance2.set(instance2.get(Calendar.YEAR), Calendar.DECEMBER, 31);

        csCalendar.state().edit()
                .setMinimumDate(instance1.getTime())
                .setMaximumDate(instance2.getTime())
                .commit();

        csCalendar.addDecorators(
                //new MySelectorDecorator(HomeActivity.this),
                new HighlightWeekendsDecorator(),
                oneDayDecorator
        );


        monthView.setOnClickListener(onclick);
        weekView.setOnClickListener(onclick);
        addEvent.setOnClickListener(onclick);

    }


    private View.OnClickListener onclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {

            if(view.getId() == weekView.getId())
            {
                csCalendar.state().edit()
                        .setCalendarDisplayMode(CalendarMode.WEEKS)
                        .commit();
            }

            else if(view.getId() == monthView.getId())
            {
                csCalendar.state().edit()
                        .setCalendarDisplayMode(CalendarMode.MONTHS)
                        .commit();
            }
            else if(view.getId() == addEvent.getId())
            {
                Intent intent   = new Intent(HomeActivity.this,EventCreateActivity.class);
                startActivity(intent);
            }
        }
    };


    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        oneDayDecorator.setDate(date.getDate());
        widget.invalidateDecorators();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //commented right side 3 dots used for sertings
        //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_events) {
            Intent intent = new Intent(HomeActivity.this,EventsListActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_settings) {
            Intent intent  = new Intent(HomeActivity.this,CallAndMessageSettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_profile_settings) {
            Intent intent  = new Intent(HomeActivity.this,ProfileSettingsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_exit) {

            HomeActivity.this.finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {

            if (backButtonCount == true)
            {
                HomeActivity.this.finish();
            }

            Toast.makeText(HomeActivity.this, "Press again to EXIT", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    backButtonCount = false;
                }
            }, 1000);
            backButtonCount = true;
        }
    }

    private void getCalenderEvents()
    {
        CalenderEventsUtility util = new CalenderEventsUtility();

        eventsArr = new ArrayList<String>();
        //eventsArr = util.readCalendarEvent(HomeActivity.this);

       // Log.d("===========",arr.get(1));
        Log.d("===========>"+"event array", String.valueOf(eventsArr));

        /*Iterator<String> iter = arr.iterator();
        while(iter.hasNext())
        {

        }*/

        new ApiSimulator(eventsArr).executeOnExecutor(Executors.newSingleThreadExecutor());
    }


    /**
     * Simulate an API call to show how to add decorators
     */
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {

        public ApiSimulator(ArrayList<String> arr) {

        }

        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MONTH, -2);
            ArrayList<CalendarDay> dates = new ArrayList<>();
            for (int i = 0; i < 30; i++) {
                CalendarDay day = CalendarDay.from(calendar);
                Log.d("===========>"+"day",""+ String.valueOf(day));
                dates.add(day);
                calendar.add(Calendar.DATE, 5);
            }
            Log.d("==========>"+"dates",""+String.valueOf(dates));
            return dates;
        }
        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);

            if (isFinishing()) {
                return;
            }
            Log.d("========>"+"cal days", String.valueOf(calendarDays));
            csCalendar.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }
    }
}
