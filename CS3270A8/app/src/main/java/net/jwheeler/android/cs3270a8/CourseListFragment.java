package net.jwheeler.android.cs3270a8;

import net.jwheeler.android.cs3270a8.CanvasObjects.Course;

import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.GsonBuilder;
import com.google.gson.Gson;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends ListFragment {

    protected ArrayAdapter<String> courseAdapter;
    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_course_list, container, false);
        MainActivity ma = (MainActivity) getActivity();
        setHasOptionsMenu(true);




        if(ma.getCanvas){
            Log.d("test", "Canvas? " + ma.getCanvas);
            ma.setCanvas(false);
            new getCanvasCourses().execute("");
        }else{
            updateAdapter();
        }

        // Add FAB should always be there if this fragment is.
        ma.showAddFAB();
        ma.hideSaveFAB();
        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.canvas_menu, menu);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);

        Log.d("test", "ListView position: " + position + " -id: " + id);
        MainActivity ma = (MainActivity) getActivity();
        ma.populateCourse(id);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        AdapterView.OnItemLongClickListener listener = new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity ma = (MainActivity) getActivity();
                DatabaseHelper dbhelper = new DatabaseHelper(getActivity(), "Course", null,1);
                Cursor cursor = dbhelper.getCourse(id);
                cursor.moveToFirst(); // THIS IS ESSENTIAL!
                String courseID = cursor.getString(cursor.getColumnIndex("id"));
                ma.loadAssignments(courseID);
                return false;
            }
        };
        getListView().setOnItemLongClickListener(listener);
    }


    public void updateAdapter(){
        MainActivity ma = (MainActivity) getActivity();
        DatabaseHelper dbhelper = new DatabaseHelper(getActivity(), "Course", null,1);
        Cursor cursor = dbhelper.getAllCourses(ma.getCanvas);
        String[] columns = new String[] {"name"};
        int[] views = new int[] {android.R.id.text1};

        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1,
                        cursor,columns,views,
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);

        setListAdapter(adapter);
    }

    public class getCanvasCourses extends AsyncTask<String, Integer, String> {
        String AUTH_TOKEN = CanvasAuth.api_token;
        String rawJson = "";

        @Override
        protected String doInBackground(String... params){
            Log.d("test", "In AsyncTask getCanvasCourses");

            try{
                URL url = new URL("https://weber.instructure.com/api/v1/courses");
                HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
                conn.connect();
                int status = conn.getResponseCode();
                switch(status){
                    case 200:
                    case 201:
                        BufferedReader br =
                                new BufferedReader(new InputStreamReader(conn.getInputStream()));
                        rawJson = br.readLine();
                        Log.d("test", "raw Json String Length = " + rawJson.length());
                        Log.d("test", "raw Json first 256 chars: " + rawJson.substring(0,256));

                }
            }catch(MalformedURLException e){

            }catch(IOException e){

            }
            return rawJson;
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);

            try{
                Course[] courses = jsonParse(result);
                DatabaseHelper dbhelper = new DatabaseHelper(getActivity(), "Course", null,1);
                dbhelper.deleteCourses();
                for (Course course : courses){
                    if(course.name != null){

                        dbhelper.insertCourse(course.id, course.name, course.course_code, course.end_at,course.start_at);
                    }
                }
            }catch(Exception e){
                Log.d("test", e.getMessage());
            }

            updateAdapter();
        }

        private Course[] jsonParse(String rawJson){
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Course[] courses = null;

            try{
                courses = gson.fromJson(rawJson, Course[].class);

                Log.d("test","Number of courses returend is: " + courses.length);
                Log.d("test", "First course returned is: " + courses[0].name);
            }catch (Exception e){
                Log.d("test", e.getMessage());
            }

            return courses;
        }
    }
}
