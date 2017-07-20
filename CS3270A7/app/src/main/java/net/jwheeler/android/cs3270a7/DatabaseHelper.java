package net.jwheeler.android.cs3270a7;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jonat on 6/25/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private SQLiteDatabase database;

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        Log.d("test", "DBHelper version: " + version);
    }

    public SQLiteDatabase open(){
        database = getWritableDatabase();
        return database;
    }

    public void close(){
        if(database != null)
            database.close();
    }

    public Cursor getAllCourses(){
        Cursor cursor = null;
        if(open() != null){
            Log.d("test", "DBHelper - getAllCourses");
            cursor = database.rawQuery("SELECT * FROM Course",null);
        }
        return cursor;
    }

    public Cursor getCourse(long id){
        String[] params = new String[1];
        params[0] = "" + id;
        Cursor cursor = null;
        if(open() != null){
            Log.d("test", "DBHelper - getCourse");
            cursor = database.rawQuery("SELECT * FROM Course WHERE _id = ?",params);
        }
        return cursor;
    }

    public void deleteCourse(long id){
        Log.d("test","DBHelper Deleting id = " + id);
        String[] params = new String[1];
        params[0] = "" + id;
        Cursor cursor = null;
        if(open() != null){
            Log.d("test", "DBHelper - getCourse");
            database.delete("Course", "_id="+id,null);
            //cursor = database.rawQuery("DELETE FROM Course WHERE _id = ?",params);
        }
        //return cursor;
    }


    public long insertCourse(String id, String name, String course_code, String start_at, String end_at){
        long rowID = -1;

        ContentValues newCourse = new ContentValues();
        newCourse.put("id", id);
        newCourse.put("name", name);
        newCourse.put("course_code", course_code);
        newCourse.put("start_at", start_at);
        newCourse.put("end_at", end_at);
        if(open() != null){
            rowID = database.insert("Course", null, newCourse);
            close();
        }
        Log.d("test", "DBHelper - insertCourse id= " + rowID);
        return rowID;
    }

    public long updateCourse(long _id, String id, String name, String course_code, String start_at, String end_at){
        long rowID = -1;

        ContentValues editCourse = new ContentValues();
        editCourse.put("id", id);
        editCourse.put("name", name);
        editCourse.put("course_code", course_code);
        editCourse.put("start_at", start_at);
        editCourse.put("end_at", end_at);
        if(open() != null){
            rowID = database.update("Course", editCourse, "_id="+_id, null);
            close();
        }
        Log.d("test", "DBHelper - updateCourse id= " + rowID);
        return rowID;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createQuery = "CREATE TABLE Course " +
                "(_id integer primary key autoincrement,"+
                "id TEXT, name TEXT, course_code TEXT, start_at TEXT, end_at TEXT)";

        db.execSQL(createQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
