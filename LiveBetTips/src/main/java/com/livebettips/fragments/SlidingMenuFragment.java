package com.livebettips.fragments;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.livebettips.R;
import com.livebettips.adapters.SectionListAdapter;
import com.livebettips.objects.Section;

import java.util.ArrayList;
import java.util.List;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class SlidingMenuFragment extends Fragment {

    private ListView sectionListView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        List<Section> sectionList = createMenu();

        View view = inflater.inflate(R.layout.slidingmenu_fragment, container, false);
        this.sectionListView = (ListView) view.findViewById(R.id.lv_slidingmenu_fragment_lv);


        SectionListAdapter sectionListAdapter = new SectionListAdapter(this.getActivity(), sectionList);
        this.sectionListView.setAdapter(sectionListAdapter);

//        this.sectionListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
//            @Override
//            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
//                return true;
//            }
//        });

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
