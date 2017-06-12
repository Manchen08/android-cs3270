package net.jwheeler.android.cs3270a5;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeResultsFragment extends Fragment {

    View rootView;
    TextView txtvChangeToMake, txtvChangeTotal, txtvTimeRemaining;

    public ChangeResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("Test", "ChangeResult - onResume");
        MainActivity ma = (MainActivity) getActivity();
        ma.restoreState();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_results, container, false);
        txtvChangeToMake = (TextView) rootView.findViewById(R.id.txtvChangeToMake);
        txtvChangeTotal = (TextView) rootView.findViewById(R.id.txtvChangeTotalSoFar);
        txtvTimeRemaining = (TextView) rootView.findViewById(R.id.txtvTimeRemaining);

        MainActivity ma = (MainActivity) getActivity();
        ma.updateGame();

        return rootView;
    }

    public void textViewUpdate(BigDecimal changeToMake, BigDecimal changeTotalSoFar, Integer timeRemaining){
        Log.d("Test", "ResultFrag - textViewUpdate(" + changeToMake + ", " + changeTotalSoFar + ", "
        + timeRemaining + ")");
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        if(txtvChangeTotal != null){
            txtvChangeToMake.setText(n.format(changeToMake));
            txtvChangeTotal.setText(n.format(changeTotalSoFar));
            txtvTimeRemaining.setText(timeRemaining.toString());
        }

    }



}
