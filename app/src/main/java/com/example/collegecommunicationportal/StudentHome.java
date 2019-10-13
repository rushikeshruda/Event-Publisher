package com.example.collegecommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.collegecommunicationportal.Model.Post;
import com.example.collegecommunicationportal.adapters.PostAdapter;
import com.example.collegecommunicationportal.adapters.StudentPostAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StudentHome extends AppCompatActivity {

    List<Post> productList;
    EditText editTextTitle, editTextCollege, editTextEntryFee;
    TextView textViewSelectDate, textViewSelectTime;

    Post post;
    RecyclerView recyclerView;
    View createPost;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        Intent intent = getIntent();
        intent.getStringExtra("student");

        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.");
        progressDialog.show();


        productList = new ArrayList<>();


        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");

        Query query = databaseReference;


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                productList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    post = postSnapshot.getValue(Post.class);
                    productList.add(post);

                }

                StudentPostAdapter adapter = new StudentPostAdapter(StudentHome.this, productList);
                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });


    }
}
