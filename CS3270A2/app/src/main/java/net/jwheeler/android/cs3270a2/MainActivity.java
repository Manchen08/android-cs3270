package net.jwheeler.android.cs3270a2;

import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button btnLoadFragmentB;
    Button btnLoadFragmentC;
    Button btnLoadFragmentD;
    Button btnSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("test","MainActivity onCreate()");
        setContentView(R.layout.activity_main);

        btnLoadFragmentB = (Button) findViewById(R.id.btnLoad2);
        btnLoadFragmentB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer2, new FragmentB(), "FB")
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnLoadFragmentC = (Button) findViewById(R.id.btnLoad3);
        btnLoadFragmentC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer3, new FragmentC(), "FC")
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnLoadFragmentD = (Button) findViewById(R.id.btnLoad4);
        btnLoadFragmentD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer4, new FragmentD(), "FD")
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnSwitch = (Button) findViewById(R.id.btnSwitch);
        btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer4, new FragmentC(), "FCS")
                        .addToBackStack(null)
                        .commit();

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer3, new FragmentD(), "FDS")
                        .addToBackStack(null)
                        .commit();
            }
        });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer1, new FragmentA(), "FA")
                .addToBackStack(null)
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
}
