package net.jwheeler.android.cs3270a5;

import android.content.SharedPreferences;
import android.icu.util.Currency;
import android.os.CountDownTimer;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;

import static java.lang.StrictMath.toIntExact;

public class MainActivity extends AppCompatActivity {

    BigDecimal _changeToMake = BigDecimal.ZERO;
    BigDecimal _currentTotal = BigDecimal.ZERO;
    Integer _time = 30;
    Integer _correctCount = 0;
    CountDownTimer _timer;
    Double _maxAmount = 10.00;

    ChangeResultsFragment ResultsFrag;
    ChangeActionsFragment ActionsFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("Text", "MainActivity - onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new ChangeResultsFragment(), "RESULTS")
                .replace(R.id.fragContainer2, new ChangeButtonsFragment(), "BUTTONS")
                .replace(R.id.fragContainer3, new ChangeActionsFragment(), "ACTIONS")
                .commit();

    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("test","MainActivity onPause()");
        saveState();
        if(_timer != null){
            _timer.cancel();
        }

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("test","MainActivity onResume()");
        restoreState();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast toast;
        switch (item.getItemId()){
            case R.id.mnuResetToZero:
                toast = Toast.makeText(this, "Reset", Toast.LENGTH_SHORT);

                // run code based on selection
                resetCounts();

                toast.show();
                return true;
            case R.id.mnuSetMax:
                toast = Toast.makeText(this,"Set Maximum",Toast.LENGTH_SHORT);
                toast.show();
                View frag2 = findViewById(R.id.fragContainer2);
                frag2.setVisibility(View.INVISIBLE);

                View frag3 = findViewById(R.id.fragContainer3);
                frag3.setVisibility(View.INVISIBLE);

                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragContainer1, new ChangeMaxFragment(), "MAX")
                        .commit();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addMoney(BigDecimal amount){
        _currentTotal = _currentTotal.add(amount);
        updateGame();
    }

    public void resetCounts(){
        _currentTotal = BigDecimal.ZERO;
        _time = 0;
        _changeToMake = BigDecimal.ZERO;
        if(_maxAmount == null){
            _maxAmount = 10.00;
        }
        updateGame();
    }

    public void updateGame(){
        Log.d("Test","MainActivity - updateGame -  Needed: " + _changeToMake
                + ", NeededDV: " + _changeToMake.doubleValue()
                + ", Total: " + _currentTotal
                + ", TotalDV: " + _currentTotal.doubleValue()
                + ", time: " + _time + "");

        // Check if they are matching values
        if(_changeToMake.doubleValue() == _currentTotal.doubleValue() && _changeToMake != _changeToMake.ZERO){
            Log.d("Test","MainActivity - They are equal!");
            _correctCount = _correctCount + 1;
            correctFragment correctFrag = new correctFragment();
            correctFrag.show(getSupportFragmentManager(), "CORRECT");

            if(_timer == null){
                //startTimer();
            }else{
                _timer.cancel();
            }

        }else if(_changeToMake.doubleValue() < _currentTotal.doubleValue()){
            Log.d("Test","MainActivity - updateGame - changeToMakeLessThan");
            incorrectFragment incorrectFrag = new incorrectFragment();
            incorrectFrag.show(getSupportFragmentManager(),"TIMESUP");

            if(_timer == null){
                //startTimer();
            }else{
                _timer.cancel();
            }

        }

        ResultsFrag = (ChangeResultsFragment) getSupportFragmentManager().findFragmentByTag("RESULTS");
        ActionsFrag = (ChangeActionsFragment) getSupportFragmentManager().findFragmentByTag("ACTIONS");

        if(ResultsFrag != null){
            ResultsFrag.textViewUpdate(_changeToMake,_currentTotal,_time);
        }
        if(ActionsFrag != null){
            ActionsFrag.updateCorrectChangeCount(_correctCount);
        }
    }

    public void startTimer(){
        if(_timer == null){
            _timer = new CountDownTimer(30000, 1000) {
                public void onTick(long millisecondsTillFinished){
                    double secondsTillFinished = millisecondsTillFinished / 1000;
                    _time = new BigDecimal(secondsTillFinished).intValueExact();
                    updateGame();
                }

                public void onFinish(){
                    TimesUpFrag timesUpFrag = new TimesUpFrag();
                    timesUpFrag.show(getSupportFragmentManager(),"TIMESUP");
                }
            }.start();
        }else{
            _timer.cancel();
            _timer.start();
        }
    }

    public void newAmount(){
        // Messy conversion to give me a pretty, comparable double.
        double start = .01;
        double end = _maxAmount;
        double random = new Random().nextDouble();
        double result = start + (random * (end - start));
        DecimalFormat df = new DecimalFormat("#.##");
        double p = Double.parseDouble(df.format(result));
        BigDecimal answer = new BigDecimal(p);
        _changeToMake = answer;
        updateGame();
    }

    public void saveMaxAmount(Double amount){
        Log.d("Test", "MainActivity - saveMaxAmount(" + amount + ")");
        _maxAmount = amount;
        View frag2 = findViewById(R.id.fragContainer2);
        frag2.setVisibility(View.VISIBLE);

        View frag3 = findViewById(R.id.fragContainer3);
        frag3.setVisibility(View.VISIBLE);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new ChangeResultsFragment(), "RESULTS")
                .commit();

        resetCounts();
        newAmount();
        updateGame();
    }

    public void alertMaxAmount(){
        Toast toast;
        toast = Toast.makeText(this, "Value must be bigger than one cent.", Toast.LENGTH_SHORT);
        toast.show();
    }

    public void saveState(){
        Log.d("Test", "MainActivity - saveState - changeToMake: " + _changeToMake.floatValue() +"");
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putFloat("changeToMake",_changeToMake.floatValue());
        spEditor.putFloat("changeSoFar", _currentTotal.floatValue());
        spEditor.putInt("correctCount", _correctCount);

    }

    public void restoreState(){
        SharedPreferences sp = getPreferences(MODE_PRIVATE);

        Float make = sp.getFloat("changeToMake", 0);
        if(make != 0){
            _changeToMake = new BigDecimal(make);
        }

        _currentTotal = new BigDecimal(sp.getFloat("changeSoFar", 0));
        Log.d("Test", "MainActivity - restoreState - changeToMake: " + _changeToMake.floatValue() +" float: " + make + "");
        _correctCount = sp.getInt("correctCount", 0);
    }


}
