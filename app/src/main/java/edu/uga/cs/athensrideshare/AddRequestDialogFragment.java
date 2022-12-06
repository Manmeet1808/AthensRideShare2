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

public class AddRequestDialogFragment extends DialogFragment {

    private EditText nameView;
    private EditText ageView;
    private EditText phoneView;
    private EditText startView;
    private EditText destView;
    private EditText dateView;
    private EditText timeView;
    private EditText seatsView;
    private EditText socialView;
    private EditText fuelView;

    // This interface will be used to obtain the new job lead from an AlertDialog.
    // A class implementing this interface will handle the new job lead, i.e. store it
    // in Firebase and add it to the RecyclerAdapter.
    public interface AddRequestDialogListener {
        void addRequest(Request request);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Create the AlertDialog view
        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View layout = inflater.inflate(R.layout.add_request_dialog,
                getActivity().findViewById(R.id.root));

        // get the view objects in the AlertDialog
        nameView = layout.findViewById( R.id.editText1 );
        phoneView = layout.findViewById( R.id.editText3 );
        ageView = layout.findViewById( R.id.editText2 );
        startView = layout.findViewById( R.id.editText4 );
        destView = layout.findViewById( R.id.editText5 );
        dateView = layout.findViewById(R.id.editText6);
        timeView = layout.findViewById( R.id.editText7 );
        seatsView = layout.findViewById( R.id.editText8 );
        socialView = layout.findViewById( R.id.editText9 );
        fuelView = layout.findViewById( R.id.editText10 );

        //creates the dialog for a new request form to open
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), R.style.AlertDialogStyle);
        builder.setView(layout);

        builder.setTitle( "New Request" );
        builder.setNegativeButton( android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.dismiss();
            }
        });

        builder.setPositiveButton( android.R.string.ok, new AddRequestListener() );

        return builder.create();
    }

    //Creates a new Request object from data input by the user
    private class AddRequestListener implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            // get the new job lead data from the user
            String fullName = nameView.getText().toString();
            String age = ageView.getText().toString();
            String phone = phoneView.getText().toString();
            String start = startView.getText().toString();
            String dest = destView.getText().toString();
            String date = dateView.getText().toString();
            String time = timeView.getText().toString();
            String seats = seatsView.getText().toString();
            String social = socialView.getText().toString();
            String fuel = fuelView.getText().toString();


            // create a new Request object
            Request request = new Request( fullName, age, phone, start, dest, date, time, seats, social, fuel );

            // get the Activity's listener to add the new request
            AddRequestDialogListener listener = (AddRequestDialogListener) getActivity();

            // add the new request
            listener.addRequest( request );

            // close the dialog
            dismiss();
        }
    }
}

