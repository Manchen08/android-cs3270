package net.jwheeler.android.cs3270a7;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseEditFragment extends Fragment {

    View rootView;
    EditText txtEId, txtEName, txtECourse_code, txtEStart, txtEEnd;

    public CourseEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_course_edit, container, false);
        txtEId = (EditText) rootView.findViewById(R.id.txtEditCourseId);
        txtEName = (EditText) rootView.findViewById(R.id.txtEditCourseName);
        txtECourse_code = (EditText) rootView.findViewById(R.id.txtEditCourseCode);
        txtEStart = (EditText) rootView.findViewById(R.id.txtEditCourseStart);
        txtEEnd = (EditText) rootView.findViewById(R.id.txtEditCourseEnd);

        MainActivity ma = (MainActivity) getActivity();
        String id = ma.getCurrentCourse();

        // Check to see if we have a course id we are going to be changing.
        if(id != null){
            DatabaseHelper dbHelper = new DatabaseHelper(getActivity(), "Course", null, 1);
            Cursor cursor = dbHelper.getCourse(Long.parseLong(id));
            cursor.moveToFirst(); // THIS IS ESSENTIAL!

            String ID = cursor.getString(cursor.getColumnIndex("id"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String course_code = cursor.getString(cursor.getColumnIndex("course_code"));
            String start_at = cursor.getString(cursor.getColumnIndex("start_at"));
            String end_at = cursor.getString(cursor.getColumnIndex("end_at"));

            txtEId.setText(ID);
            txtEName.setText(name);
            txtECourse_code.setText(course_code);
            txtEStart.setText(start_at);
            txtEEnd.setText(end_at);
        }else{
            txtEId.setText("");
            txtEName.setText("");
            txtECourse_code.setText("");
            txtEStart.setText("");
            txtEEnd.setText("");
        }
        return rootView;
    }

    public void saveEdit(){
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity(),"Course",null,1);
        MainActivity ma = (MainActivity) getActivity();
        String _id = ma.getCurrentCourse();

        if(_id == null){
            long rowId = dbHelper.insertCourse(
                    txtEId.getText().toString(),
                    txtEName.getText().toString(),
                    txtECourse_code.getText().toString(),
                    txtEStart.getText().toString(),
                    txtEEnd.getText().toString());

            Log.d("test","EditFrag saveEdit inserted new rowId = " + rowId);

        }else{
            long l = Long.parseLong(_id);
            long rowId = dbHelper.updateCourse(
                    l,
                    txtEId.getText().toString(),
                    txtEName.getText().toString(),
                    txtECourse_code.getText().toString(),
                    txtEStart.getText().toString(),
                    txtEEnd.getText().toString());

            Log.d("test","EditFrag saveEdit updated rowId = " + rowId);
        }

        Log.d("test","TEST ");
        ma.reloadListFrag();
    }

    public void loadData(){

    }
}
