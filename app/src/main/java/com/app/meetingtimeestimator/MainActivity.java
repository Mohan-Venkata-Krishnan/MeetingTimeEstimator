package com.app.meetingtimeestimator;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.app.meetingtimeestimator.dto.CalendarEventEstimatorDTO;
import com.app.meetingtimeestimator.listeners.button.CalculateButtonOnClickListener;
import com.app.meetingtimeestimator.listeners.datePicker.DatePickerOnClickListener;
import com.app.meetingtimeestimator.listeners.datePicker.DatePickerOnSetListener;
import com.app.meetingtimeestimator.listeners.spinner.CalendarSpinnerOnItemSelectedListener;
import com.app.meetingtimeestimator.listeners.spinner.TimeSlotSpinnerOnItemSelectedListener;
import com.app.meetingtimeestimator.preferences.SettingsActivity;
import com.app.meetingtimeestimator.resource.GetCalendarEventResource;
import com.app.meetingtimeestimator.resource.ProcessCalendarEventResource;
import com.app.meetingtimeestimator.utils.FromAndToUtils;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    CalendarEventEstimatorDTO calendarEventEstimatorDTO;
    HashMap<String, String> calendarNameIdMap;

    String selectedTimeSlot = null;
    String selectedCalendarId = null;
    String selectedCalendarName = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // View Elements
        Toolbar toolbar = findViewById(R.id.toolbar);
        Button calculateButton = findViewById(R.id.calculate);
        EditText toDatePicker = findViewById(R.id.to_date_picker);
        EditText fromDatePicker = findViewById(R.id.from_date_picker);
        MaterialSpinner timeSlotSpinner = findViewById(R.id.time_slot);
        LinearLayout datePickerLinearLayout = findViewById(R.id.date_picker);
        MaterialSpinner syncedCalendarSpinner = findViewById(R.id.synced_calendar_spinner);

        // Set Toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Calendar Analytics");

        // Request Permission
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALENDAR}, 1);
            return;
        }

        // Get Available Calendars
        GetCalendarEventResource contentResolver = new GetCalendarEventResource(getApplicationContext());
        calendarNameIdMap = contentResolver.getCalendars();
        if (null == calendarNameIdMap || calendarNameIdMap.size() == 0) {
            Toast.makeText(this, "Could not find any calendars synced to your device", Toast.LENGTH_LONG).show();
            return;
        }


        // Populate Calendar Material Spinner
        Set<String> syncedCalendarSet = calendarNameIdMap.keySet();
        String[] syncedCalendarArray = Arrays.copyOf(syncedCalendarSet.toArray(), syncedCalendarSet.size(), String[].class);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, syncedCalendarArray);
        syncedCalendarSpinner.setAdapter(adapter);

        // Material Spinner Listener.
        final Object[] allCalendars = syncedCalendarSpinner.getItems().toArray();
        selectedCalendarName = allCalendars[syncedCalendarSpinner.getSelectedIndex()].toString();
        selectedCalendarId = calendarNameIdMap.get(selectedCalendarName);
        final ProcessCalendarEventResource processCalendarEventResource = new ProcessCalendarEventResource(getApplicationContext().getContentResolver(), selectedCalendarId, 1L, 1L);
        syncedCalendarSpinner.setOnItemSelectedListener(new CalendarSpinnerOnItemSelectedListener(allCalendars, selectedCalendarName, selectedCalendarId, calendarNameIdMap, processCalendarEventResource));

        // Populate Time Slot Spinner
        List<String> timeSlotList = Arrays.asList("Today", "Yesterday", "Tomorrow", "Current Week", "Last Week", "Last 30 Days", "Set Custom Date");
        String[] timeSlotArray = Arrays.copyOf(timeSlotList.toArray(), timeSlotList.size(), String[].class);
        ArrayAdapter<String> timeSlotAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlotArray);
        timeSlotSpinner.setAdapter(timeSlotAdapter);

        // Populate Time Slot Listener
        final Object[] allTimeSlots = timeSlotSpinner.getItems().toArray();
        selectedTimeSlot = allTimeSlots[0].toString();
        FromAndToUtils.setFromAndToDateInMillis(allTimeSlots[0].toString(), processCalendarEventResource);
        timeSlotSpinner.setOnItemSelectedListener(new TimeSlotSpinnerOnItemSelectedListener(allTimeSlots, selectedTimeSlot, datePickerLinearLayout, processCalendarEventResource));

        // Calculate Button Logic
        calendarEventEstimatorDTO = processCalendarEventResource.getAllEventData(selectedCalendarId, getApplicationContext());
        calculateButton.setOnClickListener(new CalculateButtonOnClickListener(
                MainActivity.this,
                this,
                calendarEventEstimatorDTO,
                processCalendarEventResource));

        // From Date Picker
        Calendar fromCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener fromDateSetListener = new DatePickerOnSetListener(true, this, fromCalendar, fromDatePicker, processCalendarEventResource);
        fromDatePicker.setOnClickListener(new DatePickerOnClickListener(this, fromCalendar, fromDateSetListener));

        // To Date Picker
        Calendar toCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener toDateSetListener = new DatePickerOnSetListener(false, this, toCalendar, toDatePicker, processCalendarEventResource);
        toDatePicker.setOnClickListener(new DatePickerOnClickListener(this, toCalendar, toDateSetListener));

        // Initiate a dummy click
        calculateButton.callOnClick();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_CALENDAR}, 2);
        } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.READ_CALENDAR) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
