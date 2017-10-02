package com.example.itrain.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {

    String url = "https://vast-dusk-18724.herokuapp.com/api/login";

    TextView textView;
    EditText email;
    EditText password;
    Button button;
    RequestQueue requestQueue;

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_login, container, false);
        email = (EditText)view.findViewById(R.id.input_email);
        password = (EditText)view.findViewById(R.id.input_password);
        button = (Button)view.findViewById(R.id.btn_login);
        textView = (TextView)view.findViewById(R.id.link_signup);
        requestQueue = Volley.newRequestQueue(getActivity());

        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignupActivity.class);
                startActivity(intent);
            }
        });

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
                                SharedPreferences preferences = getContext().getSharedPreferences("myPrefs", MODE_PRIVATE);



                                Log.d("debug", preferences.toString());
                                try {
                                    JSONObject mytoken = new JSONObject(response);

                                    preferences.edit().putString("token", mytoken.getString("token")).commit();
                                    Log.d("debug", preferences.toString());
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
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
                        params.put("email", email.getText().toString());
                        params.put("password", password.getText().toString());

                        return params;
                    }
                };


                requestQueue.add(postRequest);

                Intent intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });
        return view;
    }

}
