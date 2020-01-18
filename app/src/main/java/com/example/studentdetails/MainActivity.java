package com.example.studentdetails;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    EditText Rollno,Name,Marks;
    Button Insert,Delete,Update,View,ViewAll;
    SQLiteDatabase db;
    /** Called when the activity is first created. */



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Rollno=findViewById(R.id.editRollno);
        Name=findViewById(R.id.Name);
        Marks=findViewById(R.id.editMarks);
        Insert=findViewById(R.id.Insert);
        Delete=findViewById(R.id.Delete);
        Update=findViewById(R.id.Update);
        View=findViewById(R.id.View);
        ViewAll=findViewById(R.id.ViewAll);


        Insert.setOnClickListener(this);
        Delete.setOnClickListener( this);
        Update.setOnClickListener( this);
        View.setOnClickListener(this);
        ViewAll.setOnClickListener( this);

        // Creating database and table
        db=openOrCreateDatabase("StudentDB", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS student(rollno VARCHAR,name VARCHAR,marks VARCHAR);");
    }
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.Insert:

                // Inserting a record to the Student table
                //if (view == Insert) {
                    // Checking for empty fields
                    if (Rollno.getText().toString().trim().length() == 0 ||
                            Name.getText().toString().trim().length() == 0 ||
                            Marks.getText().toString().trim().length() == 0) {
                        showMessage("Error", "Please enter all values");
                        return;
                    }
                    db.execSQL("INSERT INTO student VALUES('" + Rollno.getText() + "','" + Name.getText() +
                            "','" + Marks.getText() + "');");
                    showMessage("Success", "Record added");
                    clearText();
               // }


            case R.id.Delete:

                // Deleting a record from the Student table
               // if (view == Delete) {
                    // Checking for empty roll number
                    if (Rollno.getText().toString().trim().length() == 0) {
                        showMessage("Error", "Please enter Rollno");
                        return;
                    }
                    Cursor c = db.rawQuery("SELECT * FROM student WHERE rollno='" + Rollno.getText() + "'", null);
                    if (c.moveToFirst()) {
                        db.execSQL("DELETE FROM student WHERE rollno='" + Rollno.getText() + "'");
                        showMessage("Success", "Record Deleted");
                    } else {
                        showMessage("Error", "Invalid Rollno");
                    }
                    clearText();
              //  }



            case R.id.Update:

                // Updating a record in the Student table
               // if (view == Update) {
                    // Checking for empty roll number
                    if (Rollno.getText().toString().trim().length() == 0) {
                        showMessage("Error", "Please enter Rollno");
                        return;
                    }
                    Cursor c1 = db.rawQuery("SELECT * FROM student WHERE rollno='" + Rollno.getText() + "'", null);
                    if (c1.moveToFirst()) {
                        db.execSQL("UPDATE student SET name='" + Name.getText() + "',marks='" + Marks.getText() +
                                "' WHERE rollno='" + Rollno.getText() + "'");
                        showMessage("Success", "Record Modified");
                    } else {
                        showMessage("Error", "Invalid Rollno");
                    }
                    clearText();
               // }

        case R.id.View:

            // Display a record from the Student table
          //  if (view == View) {
                // Checking for empty roll number
                if (Rollno.getText().toString().trim().length() == 0) {
                    showMessage("Error", "Please enter Rollno");
                    return;
                }
                Cursor c2 = db.rawQuery("SELECT * FROM student WHERE rollno='" + Rollno.getText() + "'", null);
                if (c2.moveToFirst()) {
                    Name.setText(c2.getString(1));
                    Marks.setText(c2.getString(2));
                } else {
                    showMessage("Error", "Invalid Rollno");
                    clearText();
                }


            case R.id.ViewAll:


                // Displaying all the records
               // if (view == ViewAll) {
                    Cursor c3 = db.rawQuery("SELECT * FROM student", null);
                    if (c3.getCount() == 0) {
                        showMessage("Error", "No records found");
                        return;
                    }
                    StringBuffer buffer = new StringBuffer();
                    while (c3.moveToNext()) {
                        buffer.append("Rollno: " + c3.getString(0) + "\n");
                        buffer.append("Name: " + c3.getString(1) + "\n");
                        buffer.append("Marks: " + c3.getString(2) + "\n\n");
                    }
                    showMessage("Student Details", buffer.toString());
               }


        }




    public void showMessage(String title,String message)
    {
        Builder builder=new Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText()
    {
        Rollno.setText("");
        Name.setText("");
        Marks.setText("");

        Rollno.requestFocus();
    }
}