package net.jwheeler.android.cs3270a3;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class Score extends Fragment {
    TextView textViewGamesPlayedCount;
    int gamesPlayed = 0;

    TextView textViewPhoneWinCount;
    int phoneWinCount = 0;

    TextView textViewPlayerWinCount;
    int playerWinCount = 0;

    TextView textViewTieGamesCount;
    int tieGamesCount = 0;

    Button btnReset;

    public Score() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_score, container, false);
        textViewGamesPlayedCount = (TextView) rootView.findViewById(R.id.textViewGamesPlayedCount);
        textViewGamesPlayedCount.setText(Integer.toString(gamesPlayed));

        textViewPhoneWinCount = (TextView) rootView.findViewById(R.id.textViewPhoneWinCount);
        textViewPhoneWinCount.setText(Integer.toString(phoneWinCount));

        textViewPlayerWinCount = (TextView) rootView.findViewById(R.id.textViewPlayerWinCount);
        textViewPlayerWinCount.setText(Integer.toString(playerWinCount));

        textViewTieGamesCount = (TextView) rootView.findViewById(R.id.textViewTieGamesCount);
        textViewTieGamesCount.setText(Integer.toString(tieGamesCount));

        btnReset = (Button) rootView.findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toast = Toast.makeText(getActivity(), "Count Reset", Toast.LENGTH_LONG);
                toast.show();
                resetView();
            }
        });
        return rootView;
    }

    public void updateView(String name){
        gamesPlayed++;
        if(name == "Tie"){
            tieGamesCount++;
        }else if(name == "You Win"){
            playerWinCount++;
        }else if(name == "Phone Wins"){
            phoneWinCount++;
        }
        setView();
    }

    public void resetView(){
        gamesPlayed = 0;
        phoneWinCount = 0;
        playerWinCount = 0;
        tieGamesCount = 0;
        setView();
    }

    public void setView(){
        textViewGamesPlayedCount.setText(Integer.toString(gamesPlayed));
        textViewPhoneWinCount.setText(Integer.toString(phoneWinCount));
        textViewPlayerWinCount.setText(Integer.toString(playerWinCount));
        textViewTieGamesCount.setText(Integer.toString(tieGamesCount));
    }
}
