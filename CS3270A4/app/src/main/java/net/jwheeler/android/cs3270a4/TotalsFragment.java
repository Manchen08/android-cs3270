package net.jwheeler.android.cs3270a4;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class TotalsFragment extends Fragment {

    View rootView;
    TextView txtViewTotalAmount;

    public TotalsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d("Test", "TotalsFragment - onCreateView");
        rootView = inflater.inflate(R.layout.fragment_totals, container, false);

        // Inflate the layout for this fragment
        return rootView;
    }

    public void setTotalAmount(Double amount){
        Log.d("Test", "TotalsFragment - setTotalAmount(" + amount + ")");
        txtViewTotalAmount = (TextView) rootView.findViewById(R.id.txtTotalAmount);
        if(txtViewTotalAmount != null){
            BigDecimal bdValue = new BigDecimal(amount);
            NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
            txtViewTotalAmount.setText(n.format(bdValue.doubleValue()));
        }else{
            Log.d("Test", "TotalsFragment - setTotalAmount - txtViewTotalAmount is null");
        }

    }

}
