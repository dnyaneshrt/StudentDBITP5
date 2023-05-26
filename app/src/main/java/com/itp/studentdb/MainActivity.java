package com.itp.studentdb;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btn_insert,btn_read,btn_update,btn_delete;
    EditText et_roll, et_name,et_dob,et_address;
    ListView listView;
    SQLiteDatabase sqLiteDatabase;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_insert=findViewById(R.id.btn_insert);
        btn_read=findViewById(R.id.btn_read);
        btn_update=findViewById(R.id.btn_update);
        btn_delete=findViewById(R.id.btn_delete);

        et_roll=findViewById(R.id.et_roll);
        et_name=findViewById(R.id.et_name);
        et_dob=findViewById(R.id.et_date);
        et_address=findViewById(R.id.et_address);

        listView=findViewById(R.id.list_view);

        //1.create a database. by "ITPMay" name.
         sqLiteDatabase = openOrCreateDatabase("ITPMay", Context.MODE_PRIVATE,null);

        //2.create table (Students)
        sqLiteDatabase.execSQL("create table if not exists Students(_id integer primary key autoincrement,roll integer,name varchar2(20),dob date,address varchar2(20))");


        btn_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                cv.put("roll",et_roll.getText().toString());
                cv.put("name",et_name.getText().toString());
                cv.put("dob",et_dob.getText().toString());
                cv.put("address",et_address.getText().toString());

               long status= sqLiteDatabase.insert("Students",null,cv);
               if(status==-1)
               {
                   Toast.makeText(MainActivity.this, "Failed to insert data..", Toast.LENGTH_SHORT).show();
               }else {
                   Toast.makeText(MainActivity.this, "Data inserted Successfully..", Toast.LENGTH_SHORT).show();
                   et_roll.setText("");
                   readData();
               }

            }
        });

        btn_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                readData();
             }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv=new ContentValues();
                cv.put("roll",et_roll.getText().toString());
                cv.put("name",et_name.getText().toString());
                cv.put("dob",et_dob.getText().toString());
                cv.put("address",et_address.getText().toString());
                int status=sqLiteDatabase.update("Students",cv,"roll=?",new String[]{et_roll.getText().toString()});
               if(status>0)
               {
                   Toast.makeText(MainActivity.this, "Data updatad successfully.", Toast.LENGTH_SHORT).show();
                   readData();
               }else {
                   Toast.makeText(MainActivity.this, "Failed to update data..", Toast.LENGTH_SHORT).show();
               }
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int status=sqLiteDatabase.delete("Students","roll=?",new String[]{et_roll.getText().toString()});
                if(status>0)
                {
                    Toast.makeText(MainActivity.this, "Data deleted successfully.", Toast.LENGTH_SHORT).show();
                    readData();
                }else {
                    Toast.makeText(MainActivity.this, "Failed to delete data..", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void readData() {
        Cursor cursor=  sqLiteDatabase.query("Students",null,null,null,null,null,null,null);

        String[] from=new String[]{"roll","name","dob","address"};
        int[] to=new int[]{R.id.tv1,R.id.tv2,R.id.tv3,R.id.tv4};
        SimpleCursorAdapter simpleCursorAdapter=  new SimpleCursorAdapter(MainActivity.this,R.layout.my_design,cursor,from,to);
        listView.setAdapter(simpleCursorAdapter);

    }
}