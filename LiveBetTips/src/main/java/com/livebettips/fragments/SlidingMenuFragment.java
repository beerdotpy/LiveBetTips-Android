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
import com.livebettips.activities.ContactUs;
import com.livebettips.activities.InAppBilling;
import com.livebettips.activities.Info;
import com.livebettips.activities.Login;
import com.livebettips.activities.Logout;
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
    SharedPreferences.Editor editor;
    Context ctx;
    Boolean isLoggedIn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ctx =getActivity();

        prefs = Api.applicationContext.getSharedPreferences("bettips", Context.MODE_PRIVATE);
        editor = prefs.edit();
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
                           Intent myIntent = new Intent(getActivity(), Login.class);
                           getActivity().startActivity(myIntent);
                       }
                       break;
                   case 1:
                       if(isLoggedIn) {
                       Intent myIntent2 = new Intent(getActivity(), ContactUs.class);
                       getActivity().startActivity(myIntent2);
                       }else{
                       Toast.makeText(getActivity().getApplicationContext(), "Please Login", Toast.LENGTH_LONG).show();
                       Intent myIntent2 = new Intent(getActivity(), Login.class);
                       getActivity().startActivity(myIntent2);
                       }
                       break;
                   case 2:
                       Intent myIntent2 = new Intent(getActivity(), Info.class);
                       Log.d("intent3", "info");
                       getActivity().startActivity(myIntent2);
                       break;
                   case 3:
                       if(isLoggedIn){
                           Intent myIntent3 = new Intent(getActivity(), Logout.class);
                           Log.d("intent3", "logout");
                           getActivity().startActivity(myIntent3);

                       }else {
                           Intent myIntent3 = new Intent(getActivity(), Login.class);
                           Log.d("intent3", "login");
                           getActivity().startActivity(myIntent3);
                       }
                       break;
                   case 4:
                       Intent myIntent4 = new Intent(getActivity(), Register.class);
                       Log.d("intent4","register");
                       getActivity().startActivity(myIntent4);
                       break;
                   case 5:if(isLoggedIn) {

                       Intent myIntent5 = new Intent(getActivity(), InAppBilling.class);
                       Log.d("intent5", "inappbilling");
                       getActivity().startActivity(myIntent5);
                   }else{
                       Toast.makeText(getActivity().getApplicationContext(), "Please Login", Toast.LENGTH_LONG).show();
                       Intent myIntent = new Intent(getActivity(), Login.class);
                       getActivity().startActivity(myIntent);
                   }
                       break;
               }
           }
       });

        return view;
    }

    private List<Section> createMenu() {
        List<Section> sectionList = new ArrayList<Section>();


        sectionList.add(new Section(1,getString(R.string.title_activity_purchased_predictions)));
        sectionList.add(new Section(2,getString(R.string.title_activity_contact_us)));
        sectionList.add(new Section(3,getString(R.string.title_activity_info)));
        if(isLoggedIn) {
            sectionList.add(new Section(4, "Logout"));
        }else{
            sectionList.add(new Section(4, getString(R.string.title_activity_login)));
        }
        sectionList.add(new Section(5,getString(R.string.title_activity_register)));
        sectionList.add(new Section(6,getString(R.string.title_activity_in_app_billing)));
        return sectionList;
    }


}
