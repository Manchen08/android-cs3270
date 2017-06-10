package net.jwheeler.android.cs3270a3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class Game extends Fragment {
    Button btnRock;
    Button btnPaper;
    Button btnScissors;
    TextView textViewPhonePick;
    TextView textViewWinState;

    public Game() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_game, container, false);

        final MainActivity ma = (MainActivity) getActivity();
        btnRock = (Button) rootView.findViewById(R.id.btnRock);
        btnRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPaper.setBackgroundResource(android.R.drawable.btn_default);
                btnScissors.setBackgroundResource(android.R.drawable.btn_default);
                btnRock.setBackgroundColor(0xFFFF4081);
                ma.runGame("Rock");
            }
        });

        btnPaper = (Button) rootView.findViewById(R.id.btnPaper);
        btnPaper.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                btnScissors.setBackgroundResource(android.R.drawable.btn_default);
                btnRock.setBackgroundResource(android.R.drawable.btn_default);
                btnPaper.setBackgroundColor(0xFFFF4081);
                ma.runGame("Paper");
            }
        });

        btnScissors = (Button) rootView.findViewById(R.id.btnScissors);
        btnScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnPaper.setBackgroundResource(android.R.drawable.btn_default);
                btnRock.setBackgroundResource(android.R.drawable.btn_default);
                btnScissors.setBackgroundColor(0xFFFF4081);
                ma.runGame("Scissors");
            }
        });

        textViewPhonePick = (TextView) rootView.findViewById(R.id.textViewPhonePick);

        textViewWinState = (TextView) rootView.findViewById(R.id.textViewWinState);

        return rootView;
    }

    public void updateView(String phonePick, String winState){

        textViewPhonePick.setText(phonePick);
        textViewWinState.setText(winState);
    }
}
