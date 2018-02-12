package net.jwheeler.android.homeautomation;

import net.jwheeler.android.homeautomation.Objects.Temperature;

import android.app.ActionBar;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


import javax.net.ssl.HttpsURLConnection;


/**
 * A simple {@link Fragment} subclass.
 */
public class TemperatureFragment extends Fragment {
    LineGraphSeries<DataPoint> series;

    public TemperatureFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_temperature, container, false);

        setHasOptionsMenu(true);

        if(series == null){
            new getTempData().execute("");
        }

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater){
        inflater.inflate(R.menu.temp_menu, menu);
    }

    public class getTempData extends AsyncTask<String, Integer, String> {
        String rawJson = "";

        @Override
        protected String doInBackground(String... params){
            Log.d("test", "In AsyncTask getCanvasCourseAssignments");
            MainActivity ma = (MainActivity) getActivity();

            try{
                URL url = new URL("http://security.jwheeler.net/Security/api/Api");
                Log.d("test", url.toString());
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                //conn.setRequestProperty("Authorization", "Bearer " + AUTH_TOKEN);
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

            LinearLayout layout = (LinearLayout) getView().findViewById(R.id.graphLinearLayout);
            LinearLayout layoutCurrentTemps = (LinearLayout) getView().findViewById(R.id.layoutCurrentTemps);

            // Remove all views in case of reloading data
            layout.removeAllViews();
            layoutCurrentTemps.removeAllViews();
            TextView txtViewTitle =  new TextView(getActivity().getApplicationContext());
            txtViewTitle.setText("Current Temperatures");
            layoutCurrentTemps.addView(txtViewTitle);

            if (Build.VERSION.SDK_INT > 23) {
                txtViewTitle.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large);
            }

            Temperature[] temps = jsonParse(result);

            Set<String> setUniqueNumbers = new LinkedHashSet<String>();
            int uniqueCount = 0;
            for(Temperature x : temps) {
                setUniqueNumbers.add(x.sensorID);
                uniqueCount++;
            }

            // For each unique location, add data points to a list, add graph to layout.
            for(String y : setUniqueNumbers){
                List<DataPoint> dataPointList = new ArrayList<>();
                boolean firstPoint = false;
                Date date = null;

                GraphView graph = new GraphView(getActivity().getApplicationContext());
                graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
                graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space
                graph.getViewport().setYAxisBoundsManual(true);
                graph.getViewport().setMinY(10);
                graph.getViewport().setMaxY(30);
                // activate horizontal zooming and scrolling
                graph.getViewport().setScalable(true);
                // activate horizontal scrolling
                graph.getViewport().setScrollable(true);
                graph.getViewport().setXAxisBoundsManual(true);
                graph.setId(Integer.parseInt(y));

                for(int j = 0; j < temps.length; j++){

                    if(temps[j].sensorID.equals(y)){
                        String strDate = temps[j].date;
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        try{
                            date = dateFormat.parse(strDate);
                            if(!firstPoint){
                                graph.getViewport().setMinX(date.getTime());
                                firstPoint = true;

                            }

                            if(date != null){
                                dataPointList.add(new DataPoint(date, Double.parseDouble(temps[j].temp)));
                            }

                        }catch(Exception e){
                            Log.d("test", "PostExecute " + e.getMessage());
                        }
                    }
                }

                if(date != null){
                    graph.getViewport().setMaxX(date.getTime());
                }




                TextView curTemp = new TextView(getActivity().getApplicationContext());
                if("1".equals(y)){
                    curTemp.setText("Office: " + dataPointList.get(dataPointList.size()-1).getY() + "C");
                }else if("2".equals(y)){
                    curTemp.setText("Living Room: " + dataPointList.get(dataPointList.size()-1).getY() + "C");
                }
                curTemp.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.uuuuuLayoutParams.WRAP_CONTENT));
                layoutCurrentTemps.addView(curTemp);

                DataPoint[] dataPoints = new DataPoint[dataPointList.size()];
                dataPoints = dataPointList.toArray(dataPoints);

                series = new LineGraphSeries<>(dataPoints);
                graph.addSeries(series);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT,500
                );

                // Add title text to layout
                TextView valueTV = new TextView(getActivity().getApplicationContext());
                if("1".equals(y)){
                    valueTV.setText("Office");
                }else if("2".equals(y)){
                    valueTV.setText("Living Room");
                }
                valueTV.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT));

                // Add to layout
                layout.addView(valueTV);
                layout.addView(graph, params);
            }
        }

        private Temperature[] jsonParse(String rawJson){
            GsonBuilder gsonb = new GsonBuilder();
            Gson gson = gsonb.create();
            Temperature[] Temperatures = null;

            try{
                Temperatures = gson.fromJson(rawJson, Temperature[].class);

                Log.d("test","Number of temperatures returned is: " + Temperatures.length);
                Log.d("test", "First temp returned is: " + Temperatures[0].temp);
            }catch (Exception e){
                Log.d("test", "jsonParse " + e.getMessage());
            }
            return Temperatures;
        }
    }

    public void reloadData(){
        Toast toast;
        toast = Toast.makeText(getActivity(), "Data Refreshed", Toast.LENGTH_SHORT);
        toast.show();
        new getTempData().execute("");
    }

}
