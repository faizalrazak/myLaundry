package com.example.itrain.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;

import java.text.DateFormat;
import java.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class DateTimeActivity extends AppCompatActivity implements View.OnClickListener{

    String location;
    int service_id;
    Button btnDatePickup, btnTimePickup, btnDateDelivery, btnTimeDelivery, doneButton;
    EditText pickupDate, pickupTime, deliveryDate, deliveryTime;
    private int pYear, pMonth, pDay, pHour, pMinute;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time);

        pickupDate = (EditText)findViewById(R.id.pickDateText);
        pickupTime = (EditText) findViewById(R.id.pickTimeText);
        deliveryDate = (EditText)findViewById(R.id.deliDateText);
        deliveryTime = (EditText)findViewById(R.id.deliTimeText);

        btnDatePickup = (Button)findViewById(R.id.btn_datePick);
        btnTimePickup = (Button)findViewById(R.id.btn_timePick);
        btnDateDelivery = (Button) findViewById(R.id.btn_DateDeli);
        btnTimeDelivery = (Button)findViewById(R.id.btnTimeDeli);
        doneButton = (Button)findViewById(R.id.button);

        btnDatePickup.setOnClickListener(this);
        btnTimePickup.setOnClickListener(this);
        btnDateDelivery.setOnClickListener(this);
        btnTimeDelivery.setOnClickListener(this);
        doneButton.setOnClickListener(this);




        location = getIntent().getStringExtra("location");
        service_id = getIntent().getIntExtra("service_id", 0);

        //Toast.makeText(getApplication(), location, Toast.LENGTH_LONG).show();
        //Toast.makeText(getApplication(), service_id + "" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void onClick(View view) {
        if(view == doneButton){
            //DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());
            //String pickup = dateFormat.format(pickupDate.getText().toString());
            //String delivery = dateFormat.format(deliveryDate.getText().toString());

            //Toast.makeText(getApplicationContext(), pickup.toString(), Toast.LENGTH_LONG).show();

            Intent intent = new Intent(DateTimeActivity.this, SummaryActivity.class);
            intent.putExtra("pickupDate", pickupDate.getText().toString());
            intent.putExtra("pickupTime", pickupTime.getText().toString());
            intent.putExtra("deliveryDate", deliveryDate.getText().toString());
            intent.putExtra("deliveryTime", deliveryTime.getText().toString());
            intent.putExtra("service_id", service_id);
            intent.putExtra("location", location);
            startActivity(intent);
        }

        if(view == btnDatePickup){

            Log.d("Test", "Test1");

            final Calendar c = Calendar.getInstance();
            pYear = c.get(Calendar.YEAR);
            pMonth = c.get(Calendar.MONTH);
            pDay = c.get(Calendar.DAY_OF_MONTH);
            Log.d("Test", "Test2");


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            pickupDate.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);
                            //pickupDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                        }
                    }, pYear, pMonth, pDay);
            datePickerDialog.show();
        }
        if(view == btnDateDelivery){

            Log.d("Test", "Test1");

            final Calendar c = Calendar.getInstance();
            pYear = c.get(Calendar.YEAR);
            pMonth = c.get(Calendar.MONTH);
            pDay = c.get(Calendar.DAY_OF_MONTH);
            Log.d("Test", "Test2");


            DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                    new DatePickerDialog.OnDateSetListener() {

                        @Override
                        public void onDateSet(DatePicker view, int year,
                                              int monthOfYear, int dayOfMonth) {
                            //deliveryDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                            deliveryDate.setText( year + "-" + (monthOfYear + 1) + "-" + dayOfMonth);

                        }
                    }, pYear, pMonth, pDay);
            datePickerDialog.show();
        }

        if(view == btnTimePickup){

            final Calendar c = Calendar.getInstance();
            pHour = c.get(Calendar.HOUR_OF_DAY);
            pMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            pickupTime.setText(hourOfDay + ":" + minute);
                        }
                    }, pHour, pMinute, false);
            timePickerDialog.show();
        }

        if(view == btnTimeDelivery){

            final Calendar c = Calendar.getInstance();
            pHour = c.get(Calendar.HOUR_OF_DAY);
            pMinute = c.get(Calendar.MINUTE);

            // Launch Time Picker Dialog
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            deliveryTime.setText(hourOfDay + ":" + minute);
                        }
                    }, pHour, pMinute, false);
            timePickerDialog.show();
        }

    }
}
