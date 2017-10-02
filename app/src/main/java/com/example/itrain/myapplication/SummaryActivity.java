package com.example.itrain.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class SummaryActivity extends AppCompatActivity {

    String url = "https://vast-dusk-18724.herokuapp.com/api/orders";

    Button button;
    TextView serviceText, locationText, pickupText, deliveryText,price;
    String location, pickupDate, pickupTime, deliveryDate, deliveryTime;

    RequestQueue queue;
    RequestQueue serviceRequest;
    TextView cancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);

        serviceText = (TextView) findViewById(R.id.service);
        locationText = (TextView) findViewById(R.id.location);
        pickupText = (TextView)findViewById(R.id.pickup);
        deliveryText = (TextView) findViewById(R.id.delivery);
        price = (TextView)findViewById(R.id.textPrice);
        final int service_id = getIntent().getIntExtra("service_id", 0);
        String serviceUrl = "https://vast-dusk-18724.herokuapp.com/api/service/" + service_id + "";

        Log.d("debug", serviceUrl);

        serviceRequest = Volley.newRequestQueue(this);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, serviceUrl, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {
                        // display response
                        Log.d("Response", response.toString());
                        location = getIntent().getStringExtra("location");
                        pickupDate = getIntent().getStringExtra("pickupDate");
                        pickupTime = getIntent().getStringExtra("pickupTime");
                        deliveryDate = getIntent().getStringExtra("deliveryDate");
                        deliveryTime = getIntent().getStringExtra("deliveryTime");


                        try {
                            price.setText(response.getString("service_price"));
                            serviceText.setText(response.getString("service_title"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        locationText.setText(location);
                        pickupText.setText(pickupDate + " / " + pickupTime + "");
                        deliveryText.setText(deliveryDate+ " / " + deliveryTime + "");
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", error.toString());
                    }
                }
        );

        serviceRequest.add(request);
        Log.d("here", serviceRequest.toString());

        cancel = (TextView)findViewById(R.id.cancelTextView);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplication(), "clicked", Toast.LENGTH_SHORT).show();

                new AlertDialog.Builder(SummaryActivity.this)
                        .setTitle("CANCEL ORDER")
                        .setMessage("Are you sure want to cancel your order ?")

                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                Intent intent = new Intent(SummaryActivity.this, MainActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.dismiss();

                            }
                        }).show();
            }
        });



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
                                Toast.makeText(getApplication(), "Complete Your Order", Toast.LENGTH_LONG).show();
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
