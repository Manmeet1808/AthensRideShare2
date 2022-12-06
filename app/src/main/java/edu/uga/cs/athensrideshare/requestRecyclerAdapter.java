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

public class requestRecyclerAdapter extends RecyclerView.Adapter<requestRecyclerAdapter.requestHolder> {
    public static final String DEBUG_TAG = "requestRecyclerAdapter";

    private List<Request> requestList;
    private Context context;

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


        public requestHolder(View itemView) {
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
            fuel = itemView.findViewById(R.id.fuelList);

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

        Log.d( DEBUG_TAG, "onBindViewHolder: " + request );

        String key = request.getKey();
        String name = request.getName();
        String age = request.getAge();
        String number = request.getNumber();
        String start = request.getStart();
        String dest = request.getDestination();
        String date = request.getDate();
        String time = request.getTime();
        String seats = request.getSeats();
        String social = request.getSocial();
        String fuel = request.getFuel();


        holder.name.setText( request.getName());
        holder.age.setText( request.getAge());
        holder.number.setText( request.getNumber());
        holder.start.setText( request.getStart());
        holder.destination.setText( request.getDestination());
        holder.date.setText( request.getDate());
        holder.time.setText( request.getTime());
        holder.seats.setText( request.getSeats());
        holder.social.setText( request.getSocial());
        holder.fuel.setText( request.getSocial());


        // We can attach an OnClickListener to the itemView of the holder;
        // itemView is a public field in the Holder class.
        // It will be called when the user taps/clicks on the whole item, i.e., one of
        // the job leads shown.
        // This will indicate that the user wishes to edit (modify or delete) this item.
        // We create and show an EditJobLeadDialogFragment.
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

