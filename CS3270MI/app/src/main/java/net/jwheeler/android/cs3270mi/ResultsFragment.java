package net.jwheeler.android.cs3270mi;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.NumberFormat;


/**
 * A simple {@link Fragment} subclass.
 */
public class ResultsFragment extends Fragment {

    View rootView;
    TextView txtViewBMI, txtViewBFP;

    public ResultsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_results, container, false);

        txtViewBFP = (TextView) rootView.findViewById(R.id.txtViewBFP);
        txtViewBMI = (TextView) rootView.findViewById(R.id.txtViewBMI);

        return rootView;
    }

    public void textViewUpdate(Double bmi, Double bfp){
        Log.d("Test", "ResultsFrag - textViewUpdate(BMI: " + bmi + " bfp: " + bfp + ")");

        txtViewBMI.setText(String.format("%.2f",bmi));
        txtViewBFP.setText(String.format("%.2f",bfp));
    }

}
