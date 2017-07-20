package net.jwheeler.android.cs3270a7;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class CourseListFragment extends ListFragment {

    protected Cursor courseCursor;

    public CourseListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        DatabaseHelper dbhelper = new DatabaseHelper(getActivity(), "Course", null,1);
        Cursor cursor = dbhelper.getAllCourses();
        String[] columns = new String[] {"name"};
        int[] views = new int[] {android.R.id.text1};
        SimpleCursorAdapter adapter =
                new SimpleCursorAdapter(getActivity(), android.R.layout.simple_list_item_1,
                        cursor,columns,views,
                        CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
        setListAdapter(adapter);
        MainActivity ma = (MainActivity) getActivity();
        // Add FAB should always be there if this fragment is.
        ma.showAddFAB();
        ma.hideSaveFAB();
        return super.onCreateView(inflater,container,savedInstanceState);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id){
        super.onListItemClick(l,v,position,id);

        Log.d("test", "ListView position: " + position + " -id: " + id);
        MainActivity ma = (MainActivity) getActivity();
        ma.populateCourse(id);

    }


}
