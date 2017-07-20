package net.jwheeler.android.cs3270a8;


import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * A simple {@link Fragment} subclass.
 */
public class CourseViewFragment extends Fragment {

    TextView txtVId, txtVName, txtVCourse_code, txtVStart_at, txtVEnd_at;
    View rootView;
    protected ArrayAdapter<CanvasObjects.Course> courseAdapter;
    public CourseViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_course_view, container, false);

        txtVId = (TextView) rootView.findViewById(R.id.txtCourseId);
        txtVName = (TextView) rootView.findViewById(R.id.txtCourseName);
        txtVCourse_code = (TextView) rootView.findViewById(R.id.txtCourseCode);
        txtVStart_at = (TextView) rootView.findViewById(R.id.txtCourseStart);
        txtVEnd_at = (TextView) rootView.findViewById(R.id.txtCourseEnd);
        setHasOptionsMenu(true);
        populateCourse();
        new getCanvasCourses();

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.main_menu, menu);
    }

    public void populateCourse(){
        MainActivity ma = (MainActivity) getActivity();

        long id = Long.parseLong(ma.getCurrentCourse());
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "Course", null, 1);
        Cursor cursor = dbHelper.getCourse(id);
        cursor.moveToFirst(); // THIS IS ESSENTIAL!

        String ID = cursor.getString(cursor.getColumnIndex("id"));
        String name = cursor.getString(cursor.getColumnIndex("name"));
        String course_code = cursor.getString(cursor.getColumnIndex("course_code"));
        String start_at = cursor.getString(cursor.getColumnIndex("start_at"));
        String end_at = cursor.getString(cursor.getColumnIndex("end_at"));

        txtVId.setText(ID);
        txtVName.setText(name);
        txtVCourse_code.setText(course_code);
        txtVStart_at.setText(start_at);
        txtVEnd_at.setText(end_at);
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

            courseAdapter.clear();
            try{
                CanvasObjects.Course[] courses = jsonParse(result);
                for (CanvasObjects.Course course : courses){
                    if(course.name != null){
                        courseAdapter.add(course);
                    }
                }

            }catch(Exception e){
                Log.d("test", e.getMessage());
            }
        }

        private CanvasObjects.Course[] jsonParse(String rawJson){
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            CanvasObjects.Course[] courses = null;

            try{
                courses = gson.fromJson(rawJson, CanvasObjects.Course[].class);

                Log.d("test","Number of courses returend is: " + courses.length);
                Log.d("test", "First course returned is: " + courses[0].name);
            }catch (Exception e){
                Log.d("test", e.getMessage());
            }

            return courses;
        }
    }


}
