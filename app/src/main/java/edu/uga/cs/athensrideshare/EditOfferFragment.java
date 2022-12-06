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


public class EditOfferFragment extends DialogFragment {

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

    public interface EditOfferListener {
        void updateOffer(int position, Offer offer, int action);
    }

    public static EditOfferFragment newInstance(int position, String key, String name, String age, String phone, String start, String dest, String date, String time, String seats, String social) {

        EditOfferFragment dialog = new EditOfferFragment();

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

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate( R.layout.add_offer_dialog, getActivity().findViewById( R.id.root ) );

        nameText = layout.findViewById( R.id.editText1 );
        ageText = layout.findViewById( R.id.editText2 );
        phoneText = layout.findViewById( R.id.editText3 );
        startText = layout.findViewById( R.id.editText4 );
        destinationText = layout.findViewById( R.id.editText5 );
        dateText = layout.findViewById( R.id.editText6 );
        timeText = layout.findViewById( R.id.editText7 );
        seatsText = layout.findViewById( R.id.editText8 );
        socialText = layout.findViewById( R.id.editText9 );

        // Pre-fill the edit texts with the current values for this offer.
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

        AlertDialog.Builder builder = new AlertDialog.Builder( getActivity(), R.style.AlertDialogStyle );
        builder.setView(layout);

        // Set the title of the AlertDialog
        builder.setTitle( "Edit Offer" );

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

            Offer offer = new Offer( name, age, phone, start, dest, date, time, seats, social );
            offer.setKey( key );

            // get the Activity's listener to add the new offer
            EditOfferListener listener = (EditOfferFragment.EditOfferListener) getActivity();
            // add the new offer
            listener.updateOffer( position, offer, SAVE );

            // close the dialog
            dismiss();
        }
    }

    private class DeleteButtonClickListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick( DialogInterface dialog, int which ) {

            Offer offer = new Offer( name, age, phone, start, dest, date, time, seats, social );
            offer.setKey( key );

            // get the Activity's listener to add the new offer
            EditOfferFragment.EditOfferListener listener = (EditOfferFragment.EditOfferListener) getActivity();
            listener.updateOffer( position, offer, DELETE );
            // close the dialog
            dismiss();
        }
    }
}
