package com.example.collegecommunicationportal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class StudentSignUp extends AppCompatActivity implements View.OnClickListener {

    EditText editTextEmail,editTextPassword;
    Button buttonRegister;

    TextView goToLogin;

    ProgressDialog progressDialog;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_sign_up);

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
        Intent i = new Intent(StudentSignUp.this,StudentLogin.class);
        startActivity(i);
        finish();

    }

    private void registerDonor() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }
        progressDialog.show();


        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful())
                        {
                            Toast.makeText(StudentSignUp.this, "Register successful", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                            Intent intent= new Intent(StudentSignUp.this,StudentHome.class);
                            intent.putExtra("student","Student");
                            startActivity(intent);
                            finish();
                        }else
                        {
                            Toast.makeText(StudentSignUp.this, ""+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                });
    }
}
