package com.example.itrain.myapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    String url = "https://vast-dusk-18724.herokuapp.com/api/orders";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.recyclerview);

        final CustomAdapter adapter = new CustomAdapter(recyclerView.getContext(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });


        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());

                            try {
                                for(int i=0; i<response.length(); i++) {
                                    adapter.addJsonObject(response.getJSONObject(i));
                                    Log.d("debug", String.valueOf(response.getJSONObject(i)));
                                }
                                adapter.notifyDataSetChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
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

// add it to the RequestQueue
        requestQueue.add(getRequest);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        public TextView orderNumberTextview;
        public TextView serviceTextview;
        public TextView statusTextview;
        public TextView pickupTextview;
        public TextView deliveryTextview;

        public MyViewHolder(View itemView) {
            super(itemView);
            orderNumberTextview = (TextView)itemView.findViewById(R.id.orderNumber);
            serviceTextview = (TextView) itemView.findViewById(R.id.service);
            statusTextview = (TextView) itemView.findViewById(R.id.status);
            pickupTextview = (TextView) itemView.findViewById(R.id.pickupdate);
            deliveryTextview = (TextView) itemView.findViewById(R.id.deliveryDate);

        }
    }

    public static class CustomAdapter extends RecyclerView.Adapter<MyViewHolder>{

        private final List<JSONObject> jsonArray = new ArrayList<>();
        Context context;
        AdapterView.OnItemClickListener listner;



        public CustomAdapter(Context context, AdapterView.OnItemClickListener onItemClickListener) {
            this.context = context;
            this.listener = listner;
        }


        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_order, parent, false));
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

            try {
                holder.orderNumberTextview.setText(jsonArray.get(position).getString("order_id"));
                holder.serviceTextview.setText(jsonArray.get(position).getString("service_id"));
                holder.statusTextview.setText(jsonArray.get(position).getString("status_id"));
                holder.pickupTextview.setText(jsonArray.get(position).getString("pickup"));
                holder.deliveryTextview.setText(jsonArray.get(position).getString("delivery"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        private final AdapterView.OnItemClickListener listener;
        @Override
        public int getItemCount() {
            return jsonArray.size();
        }

        public void addJsonObject(JSONObject jsonObject) {
            jsonArray.add(jsonObject);
        }
    }
}
