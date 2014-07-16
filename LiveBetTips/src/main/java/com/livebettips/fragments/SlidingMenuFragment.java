package com.livebettips.fragments;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.livebettips.R;
import com.livebettips.activities.Login;
import com.livebettips.activities.PurchasedPredictions;
import com.livebettips.activities.Register;
import com.livebettips.adapters.SectionListAdapter;
import com.livebettips.objects.Api;
import com.livebettips.objects.Section;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SlidingMenuFragment extends Fragment {

    private ListView sectionListView;
    SharedPreferences prefs;
    Boolean isLoggedIn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        prefs = Api.applicationContext.getSharedPreferences("bettips", Context.MODE_PRIVATE);
        isLoggedIn = prefs.getBoolean("isLoggedIn",false);

        List<Section> sectionList = createMenu();

        View view = inflater.inflate(R.layout.slidingmenu_fragment, container, false);
        this.sectionListView = (ListView) view.findViewById(R.id.lv_slidingmenu_fragment_lv);


        SectionListAdapter sectionListAdapter = new SectionListAdapter(this.getActivity(), sectionList);
        this.sectionListView.setAdapter(sectionListAdapter);

       sectionListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


               switch (position) {

                   case 0:
                       if(isLoggedIn) {
                           Intent myIntent = new Intent(getActivity(), PurchasedPredictions.class);
                           getActivity().startActivity(myIntent);
                       }else{
                           Toast.makeText(getActivity().getApplicationContext(), "Please Login", Toast.LENGTH_LONG).show();
                       }
                       break;
                   case 1:break;
                   case 2:break;
                   case 3:
                           Intent myIntent3 = new Intent(getActivity(), Login.class);
                           Log.d("intent3", "login");
                           getActivity().startActivity(myIntent3);

                       break;
                   case 4:
                       Intent myIntent4 = new Intent(getActivity(), Register.class);
                       Log.d("intent4","register");
                       getActivity().startActivity(myIntent4);
                       break;
                   case 5:break;
                   case 6:break;
               }
           }
       });

        return view;
    }

    private List<Section> createMenu() {
        List<Section> sectionList = new ArrayList<Section>();


        sectionList.add(new Section(1,"My Tips"));
        sectionList.add(new Section(2,"Contact Us"));
        sectionList.add(new Section(3,"Info"));
        sectionList.add(new Section(4,"Login"));
        sectionList.add(new Section(5,"Register"));
        sectionList.add(new Section(6,"Buy Tips"));
        sectionList.add(new Section(7,"Settings"));

        return sectionList;
    }


}
