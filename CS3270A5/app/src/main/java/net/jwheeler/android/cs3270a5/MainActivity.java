package net.jwheeler.android.cs3270a5;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause(){
        super.onPause();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor id = sp.edit();

    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sp = getPreferences(MODE_PRIVATE);

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
                toast = Toast.makeText(this, "Set Maximum", Toast.LENGTH_SHORT);
                // run code based on selection

                toast.show();
                return true;
            case R.id.mnuSetMax:
                toast = Toast.makeText(this,"Set Maximum",Toast.LENGTH_SHORT);
                toast.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
