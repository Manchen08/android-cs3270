package net.jwheeler.android.cs3270a8;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class DeleteConfirmDialog extends DialogFragment {


    public DeleteConfirmDialog() {
        // Required empty public constructor
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_delete_confirm_dialog, container, false);
//    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Dialog dialog =
                builder.setMessage("Are you sure you want to delete this course?")
                        .setCancelable(false)
                        .setTitle("Confirm Delete?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                MainActivity ma = (MainActivity) getActivity();
                                DatabaseHelper dbhelper = new DatabaseHelper(getActivity(), "Course", null,1);
                                Long id = Long.parseLong(ma.getCurrentCourse());
                                dbhelper.deleteCourse(id);
                                ma.reloadListFrag();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .create();

        return dialog;
    }

}
