package com.example.collegecommunicationportal;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class FacultySignUp extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail,editTextPassword;
    Button buttonRegister;

    TextView goToLogin;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_sign_up);

        editTextEmail = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        goToLogin= findViewById(R.id.goToLogin);

        buttonRegister= findViewById(R.id.buttonRegister);

        goToLogin.setOnClickListener(this);
        buttonRegister.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.goToLogin:
                loginDonor();
                break;

            case R.id.buttonRegister:
                registerDonor();
                break;
        }
    }

    private void loginDonor() {
        Intent i = new Intent(FacultySignUp.this,StudentLogin.class);
        startActivity(i);
        finish();

    }

    private void registerDonor() {

        progressDialog.show();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(FacultySignUp.this, "Register successful", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent= new Intent(FacultySignUp.this,FacultyHome.class);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(FacultySignUp.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}
