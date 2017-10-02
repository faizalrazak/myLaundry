package com.example.itrain.myapplication;


import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ProfileFragment extends Fragment {


    public ProfileFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        final ListView listview = (ListView) view.findViewById(R.id.listview);
        String[] values = new String[]{"Edit Profile", "My Order",
                "News & Promotion"};

        final ArrayList<String> list = new ArrayList<String>();
            for (int i = 0; i < values.length; ++i) {
            list.add(values[i]);
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, values);
        listview.setAdapter(adapter);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                switch (i){
                    case 0:
                        //Log.d("Succuess", "test");
                        Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getActivity(), OrderActivity.class);
                        startActivity(intent1);
                        break;

                    case 2:
                        Intent intent3 = new Intent(getActivity(), NewsPromotionActivity.class);
                        startActivity(intent3);
                        break;
                }

            }
        });
        return view;
    }
}
