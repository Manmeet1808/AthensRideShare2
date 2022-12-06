package edu.uga.cs.athensrideshare;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class OfferListAdapter extends ArrayAdapter<Offer> {
    private Context mContext;
    private int mResource;

    public OfferListAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Offer> objects) {
        super(context, resource, objects);
        this.mContext = context;
        this.mResource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater li = LayoutInflater.from(mContext);
        convertView = li.inflate(mResource, parent, false);

        TextView t1 = convertView.findViewById(R.id.part1);
        TextView t2 = convertView.findViewById(R.id.part2);
        TextView t3 = convertView.findViewById(R.id.part3);

        t1.setText(getItem(position).getName());
        t2.setText("Meeting Point: " + getItem(position).getStart() + ", Destination: " +  getItem(position).getDestination());
        t3.setText("Date: " + getItem(position).getDate() + ", Time: " + getItem(position).getTime());
        return convertView;
    }
}


