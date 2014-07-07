package com.livebettips.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.livebettips.R;
import com.livebettips.objects.Prediction;

import java.util.ArrayList;
import java.util.List;

public class PredictionAdapter extends BaseAdapter {

    Context ctx;
    List<Prediction> predictions = new ArrayList<Prediction>();
    LayoutInflater inflater;

    public  PredictionAdapter(Context context , List<Prediction> predictions){

        this.ctx = context;
        this.predictions = predictions;
        inflater = LayoutInflater.from(ctx);

    }

    @Override
    public int getCount() {
        return predictions.size();
    }

    @Override
    public Object getItem(int position) {
        return predictions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MyViewHolder myViewHolder;

        if(convertView == null){

            convertView = inflater.inflate(R.layout.row_prediction, null);
            myViewHolder = new MyViewHolder();
            convertView.setTag(myViewHolder);
        }else{

            myViewHolder = (MyViewHolder) convertView.getTag();
        }

        myViewHolder.team = (TextView) convertView.findViewById(R.id.tv_row_prediction_team);
        myViewHolder.team.setText(predictions.get(position).getHomeTeam() + " vs " +
                                  predictions.get(position).getAwayTeam());

        return convertView;
    }

    public static class MyViewHolder{

        TextView team;
    }

}
