package edu.uga.cs.athensrideshare;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class offerRecyclerAdapter extends RecyclerView.Adapter<offerRecyclerAdapter.offerHolder> {
    public static final String DEBUG_TAG = "offerRecyclerAdapter";

    private List<Offer> offerList;
    private Context context;

    public offerRecyclerAdapter(List<Offer> offerList, Context context) {
        this.offerList  = offerList;
        this.context = context;
    }


    class offerHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView age;
        TextView number;
        TextView start;
        TextView destination;
        TextView date;
        TextView time;
        TextView seats;
        TextView social;


        public offerHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameList);
            age = itemView.findViewById(R.id.ageList);
            number = itemView.findViewById(R.id.phoneList);
            start = itemView.findViewById(R.id.startList);
            destination = itemView.findViewById(R.id.destinationList);
            date = itemView.findViewById(R.id.dateList);
            time = itemView.findViewById(R.id.timeList);
            seats = itemView.findViewById(R.id.seatsList);
            social = itemView.findViewById(R.id.socialList);

        }
    }

        @NonNull
        @Override
        public offerHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
            View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.offer_view, parent, false );
            return new offerHolder( view );
        }

    @Override
    public void onBindViewHolder( offerHolder holder, int position ) {
        Offer offer = offerList.get( position );

        Log.d( DEBUG_TAG, "onBindViewHolder: " + offer );

        String key = offer.getKey();
        String name = offer.getName();
        String age = offer.getAge();
        String number = offer.getNumber();
        String start = offer.getStart();
        String dest = offer.getDestination();
        String date = offer.getDate();
        String time = offer.getTime();
        String seats = offer.getSeats();
        String social = offer.getSocial();


        holder.name.setText( offer.getName());
        holder.age.setText( "Age : " + offer.getAge());
        holder.number.setText( "Number : " + offer.getNumber());
        holder.start.setText( "Meet Point : " + offer.getStart());
        holder.destination.setText( "Destination : " + offer.getDestination());
        holder.date.setText( "Date of Ride : " + offer.getDate());
        holder.time.setText( "Leaving At : " + offer.getTime());
        holder.seats.setText( "Number of Available Seats : " + offer.getSeats());
        holder.social.setText( "Social : " + offer.getSocial());


        // We can attach an OnClickListener to the itemView of the holder;
        // itemView is a public field in the Holder class.
        // It will be called when the user taps/clicks on the whole item, i.e., one of
        // the offers shown.
        // This will indicate that the user wishes to edit (modify or delete) this item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditOfferFragment editOfferFragment =
                        EditOfferFragment.newInstance( holder.getAdapterPosition(), key, name, age, number, start, dest, date, time, seats, social );
                editOfferFragment.show( ((AppCompatActivity)context).getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return offerList.size();
    }


}
