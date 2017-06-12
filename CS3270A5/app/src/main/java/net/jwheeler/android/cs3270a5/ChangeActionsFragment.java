package net.jwheeler.android.cs3270a5;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeActionsFragment extends Fragment {

    View rootView;
    Button btnStartOver, btnNewAmount;
    TextView txtvCorrectChangeCount;

    public ChangeActionsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_change_actions, container, false);

        btnStartOver = (Button) rootView.findViewById(R.id.btnStartOver);
        btnNewAmount = (Button) rootView.findViewById(R.id.btnNewAmount);
        txtvCorrectChangeCount = (TextView) rootView.findViewById(R.id.txtvCorrectChangeCount);
        MainActivity ma = (MainActivity) getActivity();
        ma.updateGame();

        btnStartOver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) getActivity();
                ma.resetCounts();
                ma.newAmount();
                ma.updateGame();
                ma.startTimer();
            }
        });

        btnNewAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) getActivity();
                ma.newAmount();
            }
        });
        return rootView;
    }

    public void updateCorrectChangeCount(int amount){
        if(txtvCorrectChangeCount != null){
            txtvCorrectChangeCount.setText(Integer.toString(amount));
        }

    }



}
