package com.example.itrain.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.itrain.myapplication.R.id.image;


/**
 * A simple {@link Fragment} subclass.
 */
public class LaundryFragment extends Fragment {

    String url = "https://vast-dusk-18724.herokuapp.com/api/service";
    private static List<JSONObject> jsonArray = new ArrayList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        RequestQueue service = Volley.newRequestQueue(getActivity());

        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.recycler_view, container, false);
        final ContentAdapter adapter = new ContentAdapter(recyclerView.getContext(), new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                try {
                    intent.putExtra("service_id", jsonArray.get(position).getInt("service_id"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        Log.d("debug", "here");

        JsonArrayRequest serviceRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                try {
                    for (int i = 0; i < response.length(); i++) {
                        jsonArray.add(response.getJSONObject(i));
                        Log.d("debug", jsonArray.toString());
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.d("error", e.toString());
                    e.printStackTrace();
                }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
        Log.d("error",error.toString());
                    }
                });
        service.add(serviceRequest);
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView text;
        public TextView price;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_laundry, parent, false));

            image = (ImageView) itemView.findViewById(R.id.card_image);
            text = (TextView) itemView.findViewById(R.id.card_text);
            price = (TextView) itemView.findViewById(R.id.card_description);
        }

        public void bind(final int position, final ContentAdapter.OnItemClickListener listener){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(position);
                }
            });
        }
    }

    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>{


        Context context;

        public ContentAdapter(Context context, OnItemClickListener listener) {
            this.context = context;
            this.listener = listener;
            /*Resources resouces = context.getResources();
            service = resouces.getStringArray(R.array.item_services);
            TypedArray picServices = resouces.obtainTypedArray(R.array.service_image);
            serviceImage = new Drawable[picServices.length()];

                for (int i = 0; i<serviceImage.length; i++){
                    serviceImage[i] = picServices.getDrawable(i);
                }
            picServices.recycle();*/
        }

        public int getIdForObject(int position) throws JSONException {
            return jsonArray.get(position).getInt("service_id");
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            try {
               Log.d("debug",jsonArray.get(position).getString("service_image"));
                Picasso.with(context).load(jsonArray.get(position).getString("service_image")).into(holder.image);
                holder.text.setText(jsonArray.get(position).getString("service_title"));
                holder.price.setText(jsonArray.get(position).getString("service_price"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
            holder.bind(position, listener);
            //holder.image.setImageDrawable(serviceImage[position % serviceImage.length]);
            //holder.text.setText(service[position % service.length]);
        }

        public void addJsonObject(JSONObject jsonObject) {
            jsonArray.add(jsonObject);
        }

        public interface OnItemClickListener{
            void onItemClick(int position);
        }

        private final OnItemClickListener listener;
        //private final String[] service;
       // private final Drawable[] serviceImage;

        @Override
        public int getItemCount() {
            return jsonArray.size();
        }
    }
}
