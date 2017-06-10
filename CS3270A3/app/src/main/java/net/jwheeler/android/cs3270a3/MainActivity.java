package net.jwheeler.android.cs3270a3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.widget.Button;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new Game(), "GAME")
                // Don't want them to be able to back out of frag.
                .commit();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer2, new Score(), "SCORE")
                // Don't want them to be able to back out of frag.
                .commit();


    }
    @Override
    protected void onStart() {
        super.onStart();
        Log.d("test","MainActivity onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("test","MainActivity onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("test","MainActivity onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("test","MainActivity onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("test","MainActivity onDestroy()");
    }

    protected void runGame(String choice){
        Random rand = new Random();
        String phoneChoice = "";
        String result;

        // Phone makes a choice.
        int n = rand.nextInt(3);
        if(n == 0){
            phoneChoice = "Rock";
        }else if(n == 1){
            phoneChoice = "Paper";
        }else if(n == 2){
            phoneChoice = "Scissors";
        }

        // Choose a winner
        if(choice == phoneChoice){
            result = "Tie";
        }else if(choice == "Rock" && phoneChoice == "Scissors"){
            result = "You Win";
        }else if(choice == "Paper" && phoneChoice == "Rock") {
            result = "You Win";
        }else if(choice == "Scissors" && phoneChoice == "Paper"){
            result = "You Win";
        }else{
            result = "Phone Wins";
        }

        // Update display
        Log.d("test","MainActivity " + choice + " " + phoneChoice +  " " + result);
        Score scoreFrag = (Score) getSupportFragmentManager().findFragmentByTag("SCORE");
        Game gameFrag = (Game) getSupportFragmentManager().findFragmentByTag("GAME");
        gameFrag.updateView(phoneChoice,result);
        scoreFrag.updateView(result);
    }
}
