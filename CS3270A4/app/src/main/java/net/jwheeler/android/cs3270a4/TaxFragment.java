package net.jwheeler.android.cs3270a4;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class TaxFragment extends Fragment {

    View rootView;
    SeekBar seekBar;
    Integer taxRateAmount;
    TextView taxRate;
    TextView taxAmount;

    public TaxFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_tax, container, false);
        seekBar = (SeekBar) rootView.findViewById(R.id.seekBar);
        taxRate = (TextView) rootView.findViewById(R.id.txtTaxRate);
        taxAmount = (TextView) rootView.findViewById(R.id.txtTaxAmount);

        taxRate.setText("0%");
        taxAmount.setText("$0");

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("Test","TaxFragment onProgressChanged(" + seekBar.getProgress() + ")");
                MainActivity ma = (MainActivity) getActivity();
                taxRateAmount = seekBar.getProgress();
                taxRate.setText("Tax Rate " + taxRateAmount * .25 + "%");
                ma.setSBValue(taxRateAmount);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        // Inflate the layout for this fragment
        return rootView;
    }
    @Override
    public void onPause(){
        super.onPause();
        Log.d("Test", "TaxFrag - onPause");
        MainActivity ma = (MainActivity) getActivity();
        ma.saveState();
    }
    @Override
    public void onResume(){
        super.onResume();
        Log.d("Test", "TaxFrag - onResume");
        MainActivity ma = (MainActivity) getActivity();
        ma.restoreState();
        if(ma.getSBValue() != null){
            Log.d("Test", "TaxFrag - setProgress to " + ma.getSBValue());
            seekBar.setProgress(ma.getSBValue());
        }
    }

    public void setTaxAmount(Double amount){
        Log.d("Text", "TaxFrag - setTaxAmount(" + amount + ")");
        BigDecimal bdValue = new BigDecimal(amount);
        NumberFormat n = NumberFormat.getCurrencyInstance(Locale.US);
        taxAmount.setText("Tax Amount " + n.format(bdValue.doubleValue()));
    }

}
