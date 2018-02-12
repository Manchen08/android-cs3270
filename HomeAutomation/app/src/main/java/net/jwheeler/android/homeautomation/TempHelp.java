package net.jwheeler.android.homeautomation;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TempHelp extends DialogFragment {


    public TempHelp() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_temp_help, container, false);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Dialog dialog =
                builder.setMessage("You can pinch to zoom and slide on graphs!")
                        .setCancelable(true)
                        .setTitle("Info")
                        .setPositiveButton("Cool!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                MainActivity ma = (MainActivity) getActivity();
//                                ma.resetCounts();
//                                ma.updateGame();
                                // On the click of the positive button execute wanted code.
                            }
                        })
                        .create();

        return dialog;
    }
}
