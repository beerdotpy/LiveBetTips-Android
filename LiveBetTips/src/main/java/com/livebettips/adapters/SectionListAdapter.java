package com.livebettips.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.livebettips.R;
import com.livebettips.objects.Section;

import java.util.List;

public class SectionListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<Section> sections;

    public SectionListAdapter(Context context, List<Section> sections) {
        this.sections = sections;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return this.sections.size();
    }

    @Override
    public Object getItem(int position) {
        return this.sections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            convertView = inflater.inflate(R.layout.slidingmenu_section,
                    parent, false);
        }

        TextView textView = (TextView) convertView
                .findViewById(R.id.tv_slidingmenu_section_title);
        textView.setText(((Section) getItem(position)).getTitle());

        if(position%2 == 0){
            textView.setBackgroundColor(Color.parseColor("#706D2A"));
        }else{
            textView.setBackgroundColor(Color.parseColor("#6C7073"));
        }



        return convertView;
    }

}
