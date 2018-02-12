package net.jwheeler.android.homeautomation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Main_Menu extends Fragment {

    View rootView;
    Button btnViewTemps, btnViewMotion;

    public Main_Menu() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_main__menu, container, false);
        btnViewTemps = (Button) rootView.findViewById(R.id.btnViewTemps);
        btnViewMotion = (Button) rootView.findViewById(R.id.btnViewMotion);

        btnViewTemps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Test", "Main_Menu - btnViewTemps.onClick()");
                MainActivity ma = (MainActivity) getActivity();
                ma.loadTemperatureFrag();
            }
        });

        btnViewMotion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast;
                toast = Toast.makeText(getActivity(), "In Development", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        return rootView;
    }

}
