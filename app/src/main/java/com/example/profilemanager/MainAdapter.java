package com.example.profilemanager;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.ViewHolder> {

    Context context;
    ArrayList arrayid;
    ArrayList arrayname;
    ArrayList arrayage;
    ArrayList arraygender;

    public MainAdapter(Context context, ArrayList arrayid, ArrayList arrayname, ArrayList arrayage, ArrayList arraygender) {
        this.context = context;
        this.arrayid = arrayid;
        this.arrayname = arrayname;
        this.arrayage = arrayage;
        this.arraygender = arraygender;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView profileid, profilename, profileage,profilegender;
        Button editbtn, deletebtn;
        SQLiteDatabase myDB;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            profileid = itemView.findViewById(R.id.RowOne_2);
            profilename = itemView.findViewById(R.id.RowTwo_2);
            profileage = itemView.findViewById(R.id.RowThree_2);
            profilegender = itemView.findViewById(R.id.RowFour_2);
            editbtn = itemView.findViewById(R.id.ButtonEdit);
            deletebtn = itemView.findViewById(R.id.ButtonDelete);

            String id = profileid.getText().toString();

            editbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, Profiling.class);
                    intent.putExtra("id", profileid.getText().toString());
                    intent.putExtra("name", profilename.getText().toString());
                    intent.putExtra("age", profileage.getText().toString());
                    intent.putExtra("gender", profilegender.getText().toString());

                    context.startActivity(intent);
                }
            });
            deletebtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDB = myDB.openOrCreateDatabase("profileAcc.db", null);

                    myDB.delete("profileTable", "name = " + profilename.getText().toString(),null);
                    myDB.close();
                }
            });
        }

    }



    @NonNull
    @Override
    public MainAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.profile_row_layout,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MainAdapter.ViewHolder holder, int position) {
        holder.profileid.setText(String.valueOf(arrayid.get(position)));
        holder.profilename.setText(String.valueOf(arrayname.get(position)));
        holder.profileage.setText(String.valueOf(arrayage.get(position)));
        holder.profilegender.setText(String.valueOf(arraygender.get(position)));
    }

    @Override
    public int getItemCount() {
        return arrayid.size();
    }


}
