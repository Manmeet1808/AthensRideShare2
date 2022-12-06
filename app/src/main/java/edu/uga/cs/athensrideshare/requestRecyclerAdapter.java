package edu.uga.cs.athensrideshare;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class requestRecyclerAdapter extends RecyclerView.Adapter<requestRecyclerAdapter.requestHolder> {
    public static final String DEBUG_TAG = "requestRecyclerAdapter";

    private List<Request> requestList;
    private Context context;
    AlertDialog.Builder build;

    public requestRecyclerAdapter(List<Request> requestList, Context context) {
        this.requestList  = requestList;
        this.context = context;
    }


    class requestHolder extends RecyclerView.ViewHolder {

        TextView name;
        TextView age;
        TextView number;
        TextView start;
        TextView destination;
        TextView date;
        TextView time;
        TextView seats;
        TextView social;
        TextView fuel;
        Button accept;


        public requestHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.nameR);
            age = itemView.findViewById(R.id.ageR);
            number = itemView.findViewById(R.id.phoneR);
            start = itemView.findViewById(R.id.startR);
            destination = itemView.findViewById(R.id.destinationR);
            date = itemView.findViewById(R.id.dateR);
            time = itemView.findViewById(R.id.timeR);
            seats = itemView.findViewById(R.id.seatsR);
            social = itemView.findViewById(R.id.socialR);
            fuel = itemView.findViewById(R.id.fuelR);
            accept = itemView.findViewById(R.id.reqAccept);
            build = new AlertDialog.Builder(accept.getContext());

        }
    }

    @NonNull
    @Override
    public requestHolder onCreateViewHolder(ViewGroup parent, int viewType ) {
        View view = LayoutInflater.from( parent.getContext()).inflate( R.layout.request_view, parent, false );
        return new requestHolder( view );
    }

    @Override
    public void onBindViewHolder( requestHolder holder, int position ) {
        Request request = requestList.get( position );

        ArrayList<String> send = new ArrayList<String>();

        Log.d( DEBUG_TAG, "onBindViewHolder: " + request );

        String key = request.getKey();
        send.add(key);
        String name = request.getName();
        send.add(name);
        String age = request.getAge();
        send.add(age);
        String number = request.getNumber();
        send.add(number);
        String start = request.getStart();
        send.add(start);
        String dest = request.getDestination();
        send.add(dest);
        String date = request.getDate();
        send.add(date);
        String time = request.getTime();
        send.add(time);
        String seats = request.getSeats();
        send.add(seats);
        String social = request.getSocial();
        send.add(social);
        String fuel = request.getFuel();
        send.add(fuel);


        holder.name.setText( request.getName());
        holder.age.setText( "Age : " + request.getAge());
        holder.number.setText( "Phone Number : " + request.getNumber());
        holder.start.setText( "Meet Point : " + request.getStart());
        holder.destination.setText( "Destination : " + request.getDestination());
        holder.date.setText( "Date of Ride : " + request.getDate());
        holder.time.setText( "Leave At : " + request.getTime());
        holder.seats.setText( "Number of Seats Needed : " + request.getSeats());
        holder.social.setText( "Social : " + request.getSocial());
        holder.fuel.setText( "Will Split on Fuel : " + request.getSocial());


        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               build.setTitle("CONFIRM").setMessage("Would you like to confirm?")
                        .setCancelable(true)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(view.getContext(), acceptedRequest.class);
                                intent.putExtra("list", send);
                                view.getContext().startActivity(intent);
                                holder.accept.setEnabled(false);
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
//                Intent intent = new Intent(view.getContext(), acceptedRequest.class);
//                intent.putExtra("list", send);
//                view.getContext().startActivity(intent);
            }
        });


        // We can attach an OnClickListener to the itemView of the holder;
        // itemView is a public field in the Holder class.
        // It will be called when the user taps/clicks on the whole item, i.e., one of
        // the requests shown.
        // This will indicate that the user wishes to edit (modify or delete) this item.
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditRequestFragment editRequestFragment =
                        EditRequestFragment.newInstance( holder.getAdapterPosition(), key, name, age, number, start, dest, date, time, seats, social, fuel );
                editRequestFragment.show( ((AppCompatActivity)context).getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return requestList.size();
    }


}

