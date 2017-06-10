package net.jwheeler.android.cs3270a4;

import android.content.ClipData;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.math.BigDecimal;

public class MainActivity extends AppCompatActivity {
    Integer _sbValue;
    String _amt1;
    String _amt2;
    String _amt3;
    String _amt4;
    Double _total;
    ItemsFragment ItemsFrag;
    TotalsFragment TotalsFrag;
    TaxFragment TaxFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        _sbValue = 0;
        _total = 0.0;

        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new TotalsFragment(), "TOTALS")
                .replace(R.id.fragContainer2, new TaxFragment(), "TAX")
                .replace(R.id.fragContainer3, new ItemsFragment(), "ITEMS")
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

    public void setSBValue(Integer sbValue){
        Log.d("Test", "MainActivity setSBValue(" + sbValue + ")");
        _sbValue = sbValue;
        updateView();
    }

    public void setTotal(Double total, String amt1, String amt2, String amt3, String amt4){
        _total = total;
        _amt1 = amt1;
        _amt2 = amt2;
        _amt3 = amt3;
        _amt4 = amt4;
        updateView();
    }

    public void saveState() {
        Log.d("Text", "MainActivity - saveState");
        SharedPreferences sp = getPreferences((MODE_PRIVATE));
        SharedPreferences.Editor spEditor = sp.edit();
        spEditor.putInt("sbValue", getSBValue());
        spEditor.putString("amt1", _amt1);
        spEditor.putString("amt2", _amt2);
        spEditor.putString("amt3", _amt3);
        spEditor.putString("amt4", _amt4);
        spEditor.commit();
    }

    public void restoreState() {
        SharedPreferences sp = getPreferences((MODE_PRIVATE));
        _sbValue = sp.getInt("sbValue",0);

        ItemsFrag = (ItemsFragment) getSupportFragmentManager().findFragmentByTag("ITEMS");
        if(ItemsFrag != null){
            ItemsFrag.restoreState(sp.getString("amt1","0"),sp.getString("amt2","0"),sp.getString("amt3","0"),sp.getString("amt4","0"));
        }

        Log.d("Test", "MainActivity restoreState(" + _sbValue + ")");
    }

    public Integer getSBValue(){
        return _sbValue;
    }

    public void updateView(){
        Double itemsTotal = _total;
        Double taxRate = _sbValue * .01 * .25;
        Double taxAmount = itemsTotal * taxRate;
        Double amountTotal = taxAmount + itemsTotal;

        TaxFrag = (TaxFragment) getSupportFragmentManager().findFragmentByTag("TAX");
        if(TaxFrag == null){
            Log.d("Test","MainActivity - TaxFragment is not ready!");
        }else{
            TaxFrag.setTaxAmount(taxAmount);
        }

        TotalsFrag = (TotalsFragment) getSupportFragmentManager().findFragmentByTag("TOTALS");
        if (TotalsFrag == null){
            Log.d("Test","MainActivity - TotalsFragment is not ready!");
            return;
        }else{
            TotalsFrag.setTotalAmount(amountTotal);
        }

    }
}
