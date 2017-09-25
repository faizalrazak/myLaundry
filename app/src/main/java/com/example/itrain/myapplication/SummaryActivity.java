package com.example.itrain.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity {

    String url = "https://vast-dusk-18724.herokuapp.com/api/orders";

    Button button;
    String location, pickupDate, pickupTime, deliveryDate, deliveryTime;
    int service_id;
    RequestQueue queue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        location = getIntent().getStringExtra("location");
        pickupDate = getIntent().getStringExtra("pickupDate");
        pickupTime = getIntent().getStringExtra("pickupTime");
        deliveryDate = getIntent().getStringExtra("deliveryDate");
        deliveryTime = getIntent().getStringExtra("deliveryTime");
        service_id = getIntent().getIntExtra("service_id", 0);

        Toast.makeText(getApplication(), location, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplication(), service_id + "" , Toast.LENGTH_LONG).show();
        Toast.makeText(getApplication(), pickupDate, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplication(), pickupTime + "" , Toast.LENGTH_LONG).show();
        Toast.makeText(getApplication(), deliveryDate, Toast.LENGTH_LONG).show();
        Toast.makeText(getApplication(), deliveryTime + "" , Toast.LENGTH_LONG).show();

        queue = Volley.newRequestQueue(this);



        button = (Button)findViewById(R.id.button3);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        },
                        new Response.ErrorListener()
                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Log.d("Error.Response", error.toString());
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams()
                    {
                        Map<String, String>  params = new HashMap<String, String>();
                        params.put("service_id", Integer.toString(service_id));
                        params.put("specific_location", location);
                        params.put("location", location);
                        params.put("pickup", pickupDate + " " + pickupTime);
                        params.put("pickup_time", pickupTime);
                        params.put("delivery", "2017-12-10 11:45:00");
                        params.put("delivery_time", deliveryTime + " " + deliveryTime);
                        params.put("customer_id", "21");
                        params.put("status_id", "1");
                        Log.d("debug", params.toString());

                        return params;
                    }
                };
                queue.add(postRequest);

            }
        });
    }
}
