package com.example.amank.personal_notes;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.amank.personal_notes.db.TaskContract;
import com.example.amank.personal_notes.db.TaskDbHelper;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TaskDbHelper mHelper;
    private ListView mTaskListView;
    private ArrayAdapter<String> mAdapter;

//    private Button btnDelete;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        FloatingActionButton action_add_task = (FloatingActionButton) findViewById(R.id.action_add_task);
        action_add_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final EditText taskEditText = new EditText(MainActivity.this);
                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
                        // AlertDialog dialog = new AlertDialog.Builder(this)
                        .setTitle("Add a new Note")
                        .setMessage("What do you want to do next?")
                        .setView(taskEditText)
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String task = String.valueOf(taskEditText.getText());
                                SQLiteDatabase db = mHelper.getWritableDatabase();
                                //SQLiteDatabase db = ;
                                ContentValues values = new ContentValues();
                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
                                        null,
                                        values,
                                        SQLiteDatabase.CONFLICT_REPLACE);
                                db.close();
                                updateUI();
                            }
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog.show();


                //return true;


            }
        });

        mHelper = new TaskDbHelper(this);
        mTaskListView = (ListView) findViewById(R.id.list_notes);

        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while(cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            Log.d(TAG, "Note: " + cursor.getString(idx));
        }
        cursor.close();
        db.close();

        updateUI();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_add_task:
//                final EditText taskEditText = new EditText(this);
//                AlertDialog dialog = new AlertDialog.Builder(this)
//                // AlertDialog dialog = new AlertDialog.Builder(this)
//                        .setTitle("Add a new Note")
//                        .setMessage("What do you want to do next?")
//                        .setView(taskEditText)
//                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                String task = String.valueOf(taskEditText.getText());
//                                SQLiteDatabase db = mHelper.getWritableDatabase();
//                                //SQLiteDatabase db = ;
//                                ContentValues values = new ContentValues();
//                                values.put(TaskContract.TaskEntry.COL_TASK_TITLE, task);
//                                db.insertWithOnConflict(TaskContract.TaskEntry.TABLE,
//                                        null,
//                                        values,
//                                        SQLiteDatabase.CONFLICT_REPLACE);
//                                db.close();
//                                updateUI();
//                            }
//                        })
//                        .setNegativeButton("Cancel", null)
//                        .create();
//                dialog.show();
//
//
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void updateUI() {
        ArrayList<String> taskList = new ArrayList<>();
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor cursor = db.query(TaskContract.TaskEntry.TABLE,
                new String[]{TaskContract.TaskEntry._ID, TaskContract.TaskEntry.COL_TASK_TITLE},
                null, null, null, null, null);
        while (cursor.moveToNext()) {
            int idx = cursor.getColumnIndex(TaskContract.TaskEntry.COL_TASK_TITLE);
            taskList.add(cursor.getString(idx));
        }

        if (mAdapter == null) {
            mAdapter = new ArrayAdapter<>(this,
                    R.layout.item_notes,
                    R.id.note_title,
                    taskList);
            mTaskListView.setAdapter(mAdapter);
        } else {
            mAdapter.clear();
            mAdapter.addAll(taskList);
            mAdapter.notifyDataSetChanged();
        }

        cursor.close();
        db.close();

//        btnDelete = (Button)findViewById(R.id.note_delete);
//        btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                View parent = (View) view.getParent();
//                TextView taskTextView = (TextView) parent.findViewById(R.id.note_title);
//                String task = String.valueOf(taskTextView.getText());
//                SQLiteDatabase db = mHelper.getWritableDatabase();
//                db.delete(TaskContract.TaskEntry.TABLE,
//                        TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
//                        new String[]{task});
//                db.close();
//                updateUI();
//            }
//        });
    }

    public void deleteTask(View view) {
        View parent = (View) view.getParent();
        TextView taskTextView = (TextView) parent.findViewById(R.id.note_title);
        String task = String.valueOf(taskTextView.getText());
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete(TaskContract.TaskEntry.TABLE,
                TaskContract.TaskEntry.COL_TASK_TITLE + " = ?",
                new String[]{task});
        db.close();
        updateUI();
    }

}
