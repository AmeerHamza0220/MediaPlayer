package com.example.lablnet.mediaplayer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 *Adapter/Controller
 */

public class Adapter extends ArrayAdapter<Data> {
    List<Data> myList;
    public Adapter(@NonNull Context context,List<Data> myList) {
        super(context, 0);
        this.myList=myList;
    }

    @Override
    public int getCount() {
        return myList.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView=LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        TextView txtTitle=(TextView)convertView.findViewById(R.id.txtTitle);
        TextView txtSize=(TextView)convertView.findViewById(R.id.txtSize);
        TextView txtDuration=(TextView)convertView.findViewById(R.id.txtDuration);
        Data data=myList.get(position);
        txtTitle.setText(data.getFileTitle());
        txtDuration.setText(Data.FormatTime(data.getDuration()));
        txtSize.setText(String.valueOf(Data.parseMbs(data.getSize()))+"MB");
        return convertView;

    }
}
