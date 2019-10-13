package com.example.collegecommunicationportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


    Button student,faculty;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        student = findViewById(R.id.student);
        faculty= findViewById(R.id.faculty);

        student.setOnClickListener(this);
        faculty.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.student:
                Intent i = new Intent(MainActivity.this,StudentLogin.class);
                startActivity(i);
                finish();


                break;
            case R.id.faculty:
                Intent i1 = new Intent(MainActivity.this,FacultyLogin.class);
                startActivity(i1);
                finish();

                break;
        }
    }
}
