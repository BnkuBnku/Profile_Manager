package com.example.profilemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class Profiling extends AppCompatActivity {

    TextView modify;
    SQLiteDatabase myDB;
    EditText editname, editage,editgender;
    Button backbtn, savebtn;

    String countacc;
    int countindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiling);
        editname = findViewById(R.id.EditName);
        editage = findViewById(R.id.EditAge);
        editgender = findViewById(R.id.EditGender);
        backbtn = findViewById(R.id.ButtonBack);
        savebtn = findViewById(R.id.ButtonSave);
        modify = findViewById(R.id.TypeModify);

        //Get intent value
        //Intent from MainActivity
        Intent get = getIntent();
        modify.setText(get.getStringExtra("TypeModify"));

        //Intent from Adapter
        //Set String
        String id = get.getStringExtra("id");
        editname.setText(get.getStringExtra("name"));
        editage.setText(get.getStringExtra("age"));
        editgender.setText(get.getStringExtra("gender"));

        //Get String
        String name = editname.getText().toString();
        String age = editage.getText().toString();
        String gender = editgender.getText().toString();


        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Profiling.this, MainActivity.class);
                startActivity(intent);
            }
        });
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                myDB = openOrCreateDatabase("profileAcc.db", 0, null);
                Cursor profileAcc = myDB.rawQuery("SELECT COUNT(*) AS count FROM profiletable where profiletable.name = ? ;", new String[] {name});
                while (profileAcc.moveToNext()){
                    countindex = profileAcc.getColumnIndex("count");
                    countacc = profileAcc.getString(countindex);
                }

                profileAcc.close();
                myDB.close();

                if(Integer.parseInt(countacc) != 0){
                    myDB = openOrCreateDatabase("profileAcc.db", 0, null);
                    ContentValues cv2 = new ContentValues();

                    cv2.put("name", name);
                    cv2.put("age", age);
                    cv2.put("gender", gender);
                    myDB.update("profiletable", cv2, "profile_id = " + id, null);
                    myDB.close();

                    Intent intent = new Intent(Profiling.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(Profiling.this, "Updated Profile Successfully!", Toast.LENGTH_SHORT).show();
 }
                else{
                    myDB = openOrCreateDatabase("profileAcc.db", 0, null);
                    ContentValues cv = new ContentValues();
                    cv.put("name", name);
                    cv.put("age", age);
                    cv.put("gender", gender);
                    myDB.insert("profiletable ", null, cv);
                    myDB.close();

                    Intent intent = new Intent(Profiling.this, MainActivity.class);
                    startActivity(intent);

                    Toast.makeText(Profiling.this, "Add Profile Successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}