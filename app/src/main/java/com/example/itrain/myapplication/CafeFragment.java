package com.example.itrain.myapplication;


import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class CafeFragment extends Fragment {

    String url = "https://vast-dusk-18724.herokuapp.com/api/orders";

    public CafeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        RequestQueue queue = Volley.newRequestQueue(getActivity());

        // Inflate the layout for this fragment
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.recycler_view, container, false);
        final ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        JsonArrayRequest objrequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                    try {
                        for(int i = 0; i < response.length(); i++) {
                            adapter.addJsonObject(response.getJSONObject(i));
                        }
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });
        queue.add(objrequest);
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView image;
        public TextView nameProduct;
        public ViewHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.fragment_cafe, parent, false));
            image = (ImageView)itemView.findViewById(R.id.card_image);
            nameProduct = (TextView)itemView.findViewById(R.id.card_text);

        }
    }
    public static class ContentAdapter extends RecyclerView.Adapter<ViewHolder>{

        private final List<JSONObject> jsonArray = new ArrayList<>();
        Context context;
        //private final String[] food;
        //private final Drawable[] foodImage;

        public ContentAdapter(Context context) {

            this.context = context;
            /*Resources resources = context.getResources();
            food = resources.getStringArray(R.array.food_name);
            TypedArray picFood = resources.obtainTypedArray(R.array.food_image);
            foodImage = new Drawable[picFood.length()];

                for(int i = 0; i < foodImage.length; i++){
                    foodImage[i] = picFood.getDrawable(i);
                }
                picFood.recycle();*/
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            try {
                holder.nameProduct.setText(jsonArray.get(position).getString("location"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            // holder.image.setImageDrawable(foodImage[position % foodImage.length]);
           // holder.nameProduct.setText(food[position%food.length]);

        }

        @Override
        public int getItemCount() {
            return jsonArray.size();
            //return food.length;
        }

        public void addJsonObject(JSONObject jsonObject) {
            jsonArray.add(jsonObject);
        }
    }

}
