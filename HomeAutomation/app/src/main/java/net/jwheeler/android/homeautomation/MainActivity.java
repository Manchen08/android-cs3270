package net.jwheeler.android.homeautomation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import net.jwheeler.android.homeautomation.Objects.Temperature;

public class MainActivity extends AppCompatActivity {

    TemperatureFragment tempFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new Main_Menu(), "MENU")
                .commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.tempMenuHelp:
                TempHelp helpFrag = new TempHelp();
                helpFrag.show(getSupportFragmentManager(), "HELP");

                return true;

            case R.id.tempMenuRefresh:
                reloadData();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void loadTemperatureFrag(){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new TemperatureFragment(), "TEMP")
                .addToBackStack("TEMP")
                .commit();
    }

    public void reloadData(){
        tempFrag = (TemperatureFragment) getSupportFragmentManager().findFragmentByTag("TEMP");
        tempFrag.reloadData();
    }
}
