package net.jwheeler.android.cs3270a8;


import net.jwheeler.android.cs3270a8.CanvasObjects.Assignment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseAssignmentsFrag extends ListFragment {

    ArrayAdapter<String> assignmentArrayAdapter;

    public CourseAssignmentsFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_course_assignments, container, false);
        assignmentArrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1);
        new getCanvasCourseAssignments().execute("");

        setListAdapter(assignmentArrayAdapter);
        return rootView;
    }

    public void populateAssignment(){
        MainActivity ma = (MainActivity) getActivity();
        long id = Long.parseLong(ma.getCurrentCourse());


    }

    public class getCanvasCourseAssignments extends AsyncTask<String, Integer, String> {
        String AUTH_TOKEN = CanvasAuth.api_token;
        String rawJson = "";

        @Override
        protected String doInBackground(String... params){
            Log.d("test", "In AsyncTask getCanvasCourseAssignments");
            MainActivity ma = (MainActivity) getActivity();

            try{
                URL url = new URL("https://weber.instructure.com/api/v1/courses/" + ma.canvasCourse + "/assignments");
                Log.d("test", url.toString());
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
                Assignment[] assignments = jsonParse(result);

                for (Assignment assignment : assignments){
                    if(assignment != null){
                        assignmentArrayAdapter.add(assignment.name);
                    }

                }
            }catch(Exception e){
                Log.d("test", "PostExecute " + e.getMessage());
            }

            updateAdapter();
        }

        private Assignment[] jsonParse(String rawJson){
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Assignment[] Assignments = null;

            try{
                Assignments = gson.fromJson(rawJson, Assignment[].class);

                Log.d("test","Number of assignments returned is: " + Assignments.length);
                Log.d("test", "First course returned is: " + Assignments[0].name);
            }catch (Exception e){
                Log.d("test", "jsonParse " + e.getMessage());
            }

            return Assignments;
        }
    }

    public void updateAdapter(){

    }
}
