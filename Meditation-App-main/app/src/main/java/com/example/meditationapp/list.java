package com.example.meditationapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class list extends ArrayAdapter<listview> {

    public list(Context context, ArrayList<listview> list) {
        super(context, R.layout.listview_layout , list);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        if(convertView == null)
        {

            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_layout, parent, false);

        }


        TextView header = convertView.findViewById(R.id.header_title);
        TextView description = convertView.findViewById(R.id.description);
        TextView frequency = convertView.findViewById(R.id.frequency);


        header.setText(getItem(position).getHeader());
        description.setText(getItem(position).getDescription());
        frequency.setText(getItem(position).getFrequency());






        return convertView;
    }
}
