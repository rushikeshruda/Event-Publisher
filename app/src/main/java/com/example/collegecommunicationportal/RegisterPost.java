package com.example.collegecommunicationportal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.collegecommunicationportal.Model.Post;
import com.example.collegecommunicationportal.Model.PostRegister;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterPost extends AppCompatActivity implements View.OnClickListener{

    TextView textViewTitle;
    EditText editTextName,editTextClass,editTextMobile,editTextEmail;
    Button buttonRegister;
    Post post;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_post);

        post = (Post) getIntent().getSerializableExtra("Post");

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewTitle.setText(post.getTitle());
        editTextName = findViewById(R.id.editTextStudentName);
        editTextClass = findViewById(R.id.editTextClass);
        editTextMobile = findViewById(R.id.editTextMobile);
        editTextEmail = findViewById(R.id.editTextEmail);

        buttonRegister = findViewById(R.id.buttonRegister);
        buttonRegister.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.buttonRegister:
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Posts").child(post.getId());

                int count = Integer.parseInt(post.getCount());

                post.setCount(String.valueOf(count+1));

                databaseReference.setValue(post);

                String id = databaseReference.push().getKey();
                String name = editTextName.getText().toString().trim();
                String class1 = editTextClass.getText().toString().trim();
                String mobile = editTextMobile.getText().toString().trim();
                String email = editTextEmail.getText().toString().trim();
                String studentId = FirebaseAuth.getInstance().getUid();
                String postId = post.getId();

                PostRegister postRegister = new PostRegister(postId,id,studentId,name,class1,mobile,email);
                DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Post Register").child(id);

                databaseReference1.setValue(postRegister);

                editTextEmail.setText("");
                editTextMobile.setText("");
                editTextClass.setText("");
                editTextName.setText("");
                Toast.makeText(this, "Register For The Event", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(RegisterPost.this,StudentHome.class);
                startActivity(intent);
                finish();

                break;
        }
    }
}
