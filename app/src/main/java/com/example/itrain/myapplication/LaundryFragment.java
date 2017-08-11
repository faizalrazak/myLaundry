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

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LaundryFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext(), new ContentAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(getActivity(), MapActivity.class);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return recyclerView;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView image;
        public TextView text;

        public ViewHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.fragment_laundry, parent, false));

            image = (ImageView) itemView.findViewById(R.id.card_image);
            text = (TextView) itemView.findViewById(R.id.card_text);
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

        public interface OnItemClickListener{
            void onItemClick(int position);
        }

        private final OnItemClickListener listener;
        private final String[] service;
        private final Drawable[] serviceImage;

        public ContentAdapter(Context context, OnItemClickListener listener) {

            this.listener = listener;
            Resources resouces = context.getResources();
            service = resouces.getStringArray(R.array.item_services);
            TypedArray picServices = resouces.obtainTypedArray(R.array.service_image);
            serviceImage = new Drawable[picServices.length()];

                for (int i = 0; i<serviceImage.length; i++){
                    serviceImage[i] = picServices.getDrawable(i);
                }
            picServices.recycle();
        }
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.bind(position, listener);
            holder.image.setImageDrawable(serviceImage[position % serviceImage.length]);
            holder.text.setText(service[position % service.length]);
        }

        @Override
        public int getItemCount() {
            return service.length;
        }
    }
}
