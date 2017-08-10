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


/**
 * A simple {@link Fragment} subclass.
 */
public class CafeFragment extends Fragment {


    public CafeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        RecyclerView recyclerView = (RecyclerView)inflater.inflate(R.layout.recycler_view, container, false);
        ContentAdapter adapter = new ContentAdapter(recyclerView.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
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

        private final String[] food;
        private final Drawable[] foodImage;
        public ContentAdapter(Context context) {

            Resources resources = context.getResources();
            food = resources.getStringArray(R.array.food_name);
            TypedArray picFood = resources.obtainTypedArray(R.array.food_image);
            foodImage = new Drawable[picFood.length()];

                for(int i = 0; i < foodImage.length; i++){
                    foodImage[i] = picFood.getDrawable(i);
                }
                picFood.recycle();
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(parent.getContext()), parent);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.image.setImageDrawable(foodImage[position % foodImage.length]);
            holder.nameProduct.setText(food[position%food.length]);

        }

        @Override
        public int getItemCount() {
            return food.length;
        }
    }

}
