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
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewsPromotionActivity extends AppCompatActivity {

    String url = "https://vast-dusk-18724.herokuapp.com/api/promotion";
    RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_promotion);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        final CustomAdapter adapter = new CustomAdapter(recyclerView.getContext(), new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        requestQueue = Volley.newRequestQueue(this);

        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>()
                {
                    @Override
                    public void onResponse(JSONArray response) {
                        // display response
                        Log.d("Response", response.toString());
                            try {
                               //JSONArray data = response.getJSONArray("data");
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

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView promotionImage;
        public TextView promotionTitle;
        public TextView promotionDesc;




        public ViewHolder(View itemView) {
            super(itemView);

            promotionImage = (ImageView) itemView.findViewById(R.id.card_image);
            promotionTitle = (TextView) itemView.findViewById(R.id.card_text);
            promotionDesc = (TextView) itemView.findViewById(R.id.card_description);

        }
    }

    public static class CustomAdapter extends RecyclerView.Adapter<ViewHolder>{

        private final List<JSONObject> jsonArray = new ArrayList<>();
        Context context;


        public CustomAdapter(Context context, AdapterView.OnItemClickListener onItemClickListener) {

            this.context = context;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_row_promotion, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            try {
                Log.d("debug",jsonArray.get(position).getString("promotion_image"));
                Picasso.with(context).load(jsonArray.get(position).getString("promotion_image")).into(holder.promotionImage);
                holder.promotionTitle.setText(jsonArray.get(position).getString("title"));
                holder.promotionDesc.setText(jsonArray.get(position).getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }


        @Override
        public int getItemCount() {
            return jsonArray.size();
        }

        public void addJsonObject(JSONObject jsonObject) {
            jsonArray.add(jsonObject);
        }
    }
}
