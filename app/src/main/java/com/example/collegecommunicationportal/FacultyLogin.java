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

public class FacultyLogin extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail,editTextPassword;
    Button buttonLogin;

    TextView goToRegister;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty_login);

        editTextEmail = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        goToRegister = findViewById(R.id.goToRegister);

        buttonLogin = findViewById(R.id.buttonLogin);

        goToRegister.setOnClickListener(this);
        buttonLogin.setOnClickListener(this);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait...");
        progressDialog.setCanceledOnTouchOutside(false);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.goToRegister:
                registerDonor();
                break;

            case R.id.buttonLogin:
                loginDonor();
        }
    }


    private void loginDonor() {

        progressDialog.show();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(FacultyLogin.this, "Login successful", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent= new Intent(FacultyLogin.this,FacultyHome.class);
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(FacultyLogin.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });

    }

    private void registerDonor() {

        Intent i = new Intent(FacultyLogin.this,StudentSignUp.class);
        startActivity(i);
        finish();

    }
}
