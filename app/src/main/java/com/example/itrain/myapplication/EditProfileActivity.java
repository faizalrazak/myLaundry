package com.example.itrain.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
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

public class EditProfileActivity extends AppCompatActivity {

    String url = "https://vast-dusk-18724.herokuapp.com/api/customers/101";
    EditText nameEdittext;
    EditText phoneEdittext;
    EditText emailEdittext;
    EditText addressEdittext;
    Button sendbutton;

    RequestQueue queue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        nameEdittext = (EditText) findViewById(R.id.editText);
        phoneEdittext = (EditText)findViewById(R.id.editText3);
        emailEdittext = (EditText) findViewById(R.id.editText4);
        addressEdittext = (EditText) findViewById(R.id.editText2);
        sendbutton = (Button)findViewById(R.id.button2);

        SharedPreferences preferences = getSharedPreferences("myPrefs", MODE_PRIVATE);
        final String token = preferences.getString("token","");

        Log.d("here", token.toString());

        queue = Volley.newRequestQueue(this);




// Request a string response from the provided URL.
        JsonObjectRequest objRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Display the first 500 characters of the response string.
                       Log.d("debug", response.toString());
                        try {
                            nameEdittext.setText(response.getString("username"));
                            phoneEdittext.setText(response.getString("phone_number"));
                            emailEdittext.setText(response.getString("email"));
                            addressEdittext.setText(response.getString("address"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("debug", error.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Authorization", "Bearer " + token);

                return params;
            }
        };
// Add the request to the RequestQueue.
        queue.add(objRequest);

        sendbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>()
                        {
                            @Override
                            public void onResponse(String response) {
                                // response
                                Log.d("Response", response);
                                finish();
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

                        params.put("name", nameEdittext.getText().toString());
                        params.put("email", emailEdittext.getText().toString());
                        params.put("address", addressEdittext.getText().toString());
                        params.put("phone_number", phoneEdittext.getText().toString());

                        return params;
                    }
                };
                queue.add(postRequest);

            }
        });
    }
}
