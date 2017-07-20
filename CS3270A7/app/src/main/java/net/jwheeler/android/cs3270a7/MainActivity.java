package net.jwheeler.android.cs3270a7;

import android.app.ActionBar;
import android.app.ListFragment;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    String currentCourse;

    FloatingActionButton fabAdd;
    FloatingActionButton fabSave;

    CourseListFragment ListFrag;
    CourseViewFragment ViewFrag;
    CourseEditFragment EditFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new CourseListFragment(), "LIST")
                .commit();

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCourse = null;
                loadNewEdit();
            }
        });

        fabSave = (FloatingActionButton) findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditFrag = (CourseEditFragment) getSupportFragmentManager().findFragmentByTag("EDIT");

                EditFrag.saveEdit();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_editCourse:
                loadNewEdit();
                return true;
            case R.id.action_deleteCourse:
                DeleteConfirmDialog deleteDialog = new DeleteConfirmDialog();
                deleteDialog.show(getSupportFragmentManager(), "DELETE");

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("test","MainActivity onPause()");
        //saveState();

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("test","MainActivity onResume()");
        //restoreState();
    }

    public void reloadListFrag(){
        Log.d("Test", "MAIN - reloadListFrag");
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1, new CourseListFragment(), "LIST")
                .commit();
    }

    public void populateCourse(long id){
        fabAdd.hide();
        fabSave.hide();
        currentCourse = Long.toString(id);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1,new CourseViewFragment(),"VIEW")
                .addToBackStack("VIEW")
                .commit();
    }

    public void loadNewEdit(){
        //EditFrag = (CourseEditFragment) getSupportFragmentManager().findFragmentByTag("EDIT");
        fabAdd.hide();
        fabSave.show();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragContainer1,new CourseEditFragment(),"EDIT")
                .addToBackStack("EDIT")
                .commit();
    }

    public String getCurrentCourse(){
        return currentCourse;
    }

    public void showSaveFAB(){
        fabSave.show();
    }

    public void hideSaveFAB(){
        fabSave.hide();
    }

    public void showAddFAB(){
        fabAdd.show();
    }
}
