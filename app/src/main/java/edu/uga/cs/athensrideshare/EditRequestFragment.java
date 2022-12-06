package edu.uga.cs.athensrideshare;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class EditRequestFragment extends DialogFragment {

    public static final int SAVE = 1;
    public static final int DELETE = 2;

    private EditText nameText;
    private EditText ageText;
    private EditText phoneText;
    private EditText startText;
    private EditText destinationText;
    private EditText timeText;
    private EditText dateText;
    private EditText seatsText;
    private EditText socialText;
    private EditText fuelText;

    int position;
    String key;
    String name;
    String age;
    String phone;
    String start;
    String dest;
    String date;
    String time;
    String seats;
    String social;
    String fuel;

    public interface EditRequestListener {
        void updateRequest(int position, Request request, int action);
    }

    public static EditRequestFragment newInstance(int position, String key, String name, String age, String phone, String start, String dest, String date, String time, String seats, String social, String fuel) {

        EditRequestFragment dialog = new EditRequestFragment();

        Bundle args = new Bundle();
        args.putString( "key", key );
        args.putInt( "position", position );
        args.putString("name", name);
        args.putString( "age", age );
        args.putString("phone", phone);
        args.putString( "start", start );
        args.putString("dest", dest);
        args.putString("date", date);
        args.putString("time", time);
        args.putString( "seats", seats );
        args.putString( "social", social );
        args.putString( "fuel", fuel );
        dialog.setArguments(args);

        return dialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog( Bundle savedInstanceState ) {

        key = getArguments().getString( "key" );
        position = getArguments().getInt( "position" );
        name = getArguments().getString( "name" );
        age = getArguments().getString( "age" );
        phone = getArguments().getString( "phone" );
        start = getArguments().getString( "start" );
        dest = getArguments().getString( "dest" );
        date = getArguments().getString("date");
        time = getArguments().getString( "time" );
        seats = getArguments().getString( "seats" );
        social = getArguments().getString( "social" );
        fuel = getArguments().getString( "fuel" );

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.add_request_dialog, getActivity().findViewById( R.id.root ) );

        nameText = layout.findViewById( R.id.editText1 );
        ageText = layout.findViewById( R.id.editText2 );
        phoneText = layout.findViewById( R.id.editText3 );
        startText = layout.findViewById( R.id.editText4 );
        destinationText = layout.findViewById( R.id.editText5 );
        dateText = layout.findViewById( R.id.editText6 );
        timeText = layout.findViewById( R.id.editText7 );
        seatsText = layout.findViewById( R.id.editText8 );
        socialText = layout.findViewById( R.id.editText9 );
        fuelText = layout.findViewById( R.id.editText10 );

        // Pre-fill the edit texts with the current values for this job lead.
        // The user will be able to modify them.
        nameText.setText(name);
        ageText.setText(age);
        phoneText.setText(phone);
        startText.setText(start);
        destinationText.setText(dest);
        dateText.setText(date);
        timeText.setText(time);
        seatsText.setText(seats);
        socialText.setText(social);
        fuelText.setText(fuel);

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "Edit Request" );

        // The Cancel button handler
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                // close the dialog
                dialog.dismiss();
            }
        });

        // The Save button handler
        builder.setPositiveButton( "SAVE", new SaveButtonClickListener() );

        // The Delete button handler
        builder.setNeutralButton( "DELETE", new DeleteButtonClickListener() );

        // Create the AlertDialog and show it
        return builder.create();
    }

    private class SaveButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            String name = nameText.getText().toString();
            String age = ageText.getText().toString();
            String phone = phoneText.getText().toString();
            String start = startText.getText().toString();
            String dest = destinationText.getText().toString();
            String date = dateText.getText().toString();
            String time = timeText.getText().toString();
            String seats = seatsText.getText().toString();
            String social = socialText.getText().toString();
            String fuel = fuelText.getText().toString();



            Request request = new Request( name, age, phone, start, dest, date, time, seats, social, fuel );
            request.setKey( key );

            // get the Activity's listener to add the new job lead
            EditRequestListener listener = (EditRequestFragment.EditRequestListener) getActivity();
            // add the new job lead
            listener.updateRequest( position, request, SAVE );

            // close the dialog
            dismiss();
        }
    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick( DialogInterface dialog, int which ) {

            Request request = new Request( name, age, phone, start, dest, date, time, seats, social, fuel );
            request.setKey( key );

            // get the Activity's listener to add the new job lead
            EditRequestFragment.EditRequestListener listener = (EditRequestFragment.EditRequestListener) getActivity();            // add the new job lead
            listener.updateRequest( position, request, DELETE );
            // close the dialog
            dismiss();
        }
    }
}

