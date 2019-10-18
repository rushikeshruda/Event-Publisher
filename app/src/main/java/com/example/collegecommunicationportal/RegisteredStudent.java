package com.example.collegecommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.collegecommunicationportal.Model.Post;
import com.example.collegecommunicationportal.Model.PostRegister;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisteredStudent extends AppCompatActivity {


    Post post;

    List<String> list;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    TextView textViewTitle;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_student);
        post = (Post) getIntent().getSerializableExtra("Post");

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait");
        listView = findViewById(R.id.listViewStudents);

        list = new ArrayList<>();

        textViewTitle = findViewById(R.id.textViewTitleEvent);
        textViewTitle.setText(post.getTitle());
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Post Register");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                list.clear();

                for (DataSnapshot studentSnapshot : dataSnapshot.getChildren())
                {
                    PostRegister postRegister = studentSnapshot.getValue(PostRegister.class);

                    if (postRegister.getPostId().equals(post.getId()))
                    {
                        list.add(postRegister.getStudentName());
                    }
                }

                arrayAdapter = new ArrayAdapter<String>(RegisteredStudent.this,R.layout.support_simple_spinner_dropdown_item,list);

                listView.setAdapter(arrayAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });

    }
}
