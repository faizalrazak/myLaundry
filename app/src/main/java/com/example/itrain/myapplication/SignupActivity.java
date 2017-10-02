package com.example.itrain.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {

    String url = "https://vast-dusk-18724.herokuapp.com/api/register";

    EditText nameEdittext;
    EditText phoneNumber;
    EditText emailEdittext;
    EditText passwordEdittext;
    EditText addressEdittext;
    TextView textView;
    Button submit;
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        requestQueue = Volley.newRequestQueue(this);

        nameEdittext = (EditText)findViewById(R.id.input_name);
        phoneNumber = (EditText)findViewById(R.id.input_phone);
        emailEdittext = (EditText)findViewById(R.id.input_email);
        passwordEdittext = (EditText)findViewById(R.id.input_password);
        addressEdittext = (EditText)findViewById(R.id.input_address);

        submit = (Button)findViewById(R.id.btn_signup);

        submit.setOnClickListener(new View.OnClickListener() {
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
                        params.put("phone_number", phoneNumber.getText().toString());
                        params.put("password", passwordEdittext.getText().toString());
                        params.put("address", addressEdittext.getText().toString());

                        return params;
                    }
                };
                requestQueue.add(postRequest);

                Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        textView = (TextView) findViewById(R.id.link_login);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
