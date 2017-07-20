package net.jwheeler.android.cs3270a7;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseViewFragment extends Fragment {

    TextView txtVId, txtVName, txtVCourse_code, txtVStart_at, txtVEnd_at;
    View rootView;
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

}
