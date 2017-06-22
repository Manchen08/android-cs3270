package net.jwheeler.android.cs3270mi;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    Double _weight,_age,_inches,_bmi,_bfp;
    String _gender;

    ResultsFragment ResultsFrag;
    ActionFragment ActionFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new ActionFragment(), "ACTION")
                .replace(R.id.fragContainer2, new ResultsFragment(), "RESULTS")
                .commit();
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("test","MainActivity onPause()");
        saveState();

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("test","MainActivity onResume()");
        restoreState();
    }

    public void saveState(){
        Log.d("Test", "Main - SaveState");
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor spEditor = sp.edit();

        spEditor.putString("Weight", _weight.toString());
        spEditor.putString("Age", _age.toString());
        spEditor.putString("Inches", _inches.toString());
        spEditor.putString("Gender", _gender);
        spEditor.commit();
    }

    public void restoreState() {
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        _weight = Double.parseDouble("0" + sp.getString("Weight",""));
        _age = Double.parseDouble("0" + sp.getString("Age",""));
        _inches = Double.parseDouble("0" + sp.getString("Inches",""));
        _gender = sp.getString("Gender","M");

        ActionFrag = (ActionFragment) getSupportFragmentManager().findFragmentByTag("ACTION");
        if(ActionFrag != null){
            ActionFrag.restoreState(_weight,_inches,_age,_gender);
        }
    }

    public void Calculate(Double weight, Double inches, Double age, String gender){
        _weight = weight;
        _inches = inches;
        _age = age;
        _gender = gender;
        Double g = 0.0;

        if(gender == "M"){
            g = 0.0;
        }else{
            g = 1.0;
        }

        _bmi = weight / (inches*inches) * 703;
        _bfp = (1.2 * _bmi) + (.23 * _age) - (10.8 * g) - 5.4;

        ResultsFrag = (ResultsFragment) getSupportFragmentManager().findFragmentByTag("RESULTS");

        ResultsFrag.textViewUpdate(_bmi,_bfp);
    }
}
