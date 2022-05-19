package com.example.profilemanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> profileId, profileName, profileAge, profileGender;

    SQLiteDatabase myDB;
    Button addProfile;
    RecyclerView recyclerview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addProfile = findViewById(R.id.addButton);
        recyclerview = findViewById(R.id.recyclerView);

        //1 Create Database Create
        myDB = openOrCreateDatabase("profileAcc.db", 0, null);

        myDB.execSQL("create table if not exists profiletable (" +
                "profile_id integer primary key autoincrement," +
                "name varchar(255) UNIQUE," +
                "age integer," +
                "gender varchar(255)" +
                ")");

        myDB.close();

        addProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, Profiling.class);
                intent.putExtra("TypeModify", addProfile.getText().toString());
                startActivity(intent);
            }
        });

        profileId = new ArrayList<>();
        profileName = new ArrayList<>();
        profileAge = new ArrayList<>();
        profileGender = new ArrayList<>();

        LinearLayoutManager layout = new LinearLayoutManager(this);
        recyclerview.setLayoutManager(layout);
        recyclerview.setAdapter
                (new MainAdapter(this,
                        profileId,
                        profileName,
                        profileAge,
                        profileGender));

        Display();
    }

    private void Display(){
        myDB = openOrCreateDatabase("profileAcc.db", 0, null);
        Cursor cursor = myDB.query("profiletable", null, null, null, null, null, "profile_id DESC");

        while( cursor.moveToNext()){
            profileId.add(cursor.getString(0));
            profileName.add(cursor.getString(1));
            profileAge.add(cursor.getString(2));
            profileGender.add(cursor.getString(3));

        }
        cursor.close();
        myDB.close();
    }

    private void CreateTable(){

    }
}