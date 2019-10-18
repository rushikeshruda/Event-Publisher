package com.example.collegecommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.collegecommunicationportal.Model.Post;
import com.example.collegecommunicationportal.adapters.PostAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class FacultyHome extends AppCompatActivity implements View.OnClickListener {

    List<Post> productList;
    EditText editTextTitle, editTextCollege, editTextEntryFee;
    TextView textViewSelectDate, textViewSelectTime;

    Post post;
    RecyclerView recyclerView;
    View createPost;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    FirebaseAuth firebaseAuth;
    FloatingActionButton myFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_home);
        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        firebaseAuth = FirebaseAuth.getInstance();
        LayoutInflater inflater = getLayoutInflater();
        createPost = inflater.inflate(R.layout.dialog_add_post, null, false);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait.");
        progressDialog.show();

        editTextTitle = createPost.findViewById(R.id.editTextTitle);
        editTextCollege = createPost.findViewById(R.id.editTextCollege);
        editTextEntryFee = createPost.findViewById(R.id.editTextEntryFee);
        textViewSelectDate = createPost.findViewById(R.id.textViewSelectDate);
        textViewSelectTime = createPost.findViewById(R.id.textViewSelectTime);

        textViewSelectTime.setOnClickListener(this);
        textViewSelectDate.setOnClickListener(this);


        //initializing the productlist
        productList = new ArrayList<>();

        myFab = findViewById(R.id.fab);

        databaseReference = FirebaseDatabase.getInstance().getReference("Posts");

        Query query = databaseReference;


        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                productList.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    post = postSnapshot.getValue(Post.class);
                    if (firebaseAuth.getUid().equals(post.getAuthorID()))
                        productList.add(post);

                }

                if (productList.isEmpty())
                    Toast.makeText(FacultyHome.this, "No Post Available", Toast.LENGTH_SHORT).show();

                PostAdapter adapter = new PostAdapter(FacultyHome.this, productList);
                recyclerView.setAdapter(adapter);

                progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                progressDialog.dismiss();
            }
        });

    }


    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.textViewSelectDate:

                final Calendar calendar = Calendar.getInstance();
                int mYear = calendar.get(Calendar.YEAR);
                int mMonth = calendar.get(Calendar.MONTH);
                int mdayofMonth = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(FacultyHome.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        textViewSelectDate.setText(String.format("%02d-%02d-%02d", year, month + 1, dayOfMonth));
                    }
                }, mYear, mMonth, mdayofMonth);
                datePickerDialog.setTitle(R.string.choose_date);
                datePickerDialog.show();

                break;

            case R.id.textViewSelectTime:

                final Calendar c = Calendar.getInstance();
                int mHour = c.get(Calendar.HOUR_OF_DAY);
                int mMinute = c.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(FacultyHome.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                textViewSelectTime.setText(String.format("%02d:%02d", hourOfDay, minute));
                            }
                        }, mHour, mMinute, true);
                timePickerDialog.setTitle(R.string.choose_time);
                timePickerDialog.show();

                break;
        }

    }

    public void onClickc(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                String authorId = firebaseAuth.getUid();

                String id = databaseReference.push().getKey();
                String title = editTextTitle.getText().toString().trim();
                String college = editTextCollege.getText().toString().trim();
                String entryFees = editTextEntryFee.getText().toString().trim();
                String date = textViewSelectDate.getText().toString().trim();
                String time = textViewSelectTime.getText().toString().trim();

                if (title.isEmpty()||college.isEmpty()||entryFees.isEmpty()||date.equals("Select Date")||time.equals("Select Time"))
                {
                    Toast.makeText(FacultyHome.this, "All Fields Required", Toast.LENGTH_SHORT).show();
                }else {

                    Post post = new Post(authorId, college,"0", date, entryFees, id, time, title);

                    databaseReference.child(id).setValue(post);
                }
            }
        });

        if (createPost.getParent() != null) {
            ((ViewGroup) createPost.getParent()).removeView(createPost);
        }

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        builder.setView(createPost);
        builder.show();

    }
}
