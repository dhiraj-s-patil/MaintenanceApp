package com.dhiraj.admin.project_maintenance;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth mAuth;
    TextView userEmail, userPassword;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        userEmail = findViewById(R.id.emailLTV);
        userPassword = findViewById(R.id.passwordLTV);
        findViewById(R.id.signinBT).setOnClickListener(this);
        progressBar = findViewById(R.id.progressbar);

        //Test
        startActivity(new Intent(this,UserHomeActivity.class));

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),FillUserDetailsActivity.class));
//            finish();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signinBT:
                verifyUser();
                break;
            default: break;
        }
    }

    public void verifyUser(){
        String email = userEmail.getText().toString().trim();
        String password = userPassword.getText().toString().trim();
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Signed in successfully", Toast.LENGTH_SHORT).show();
                    //go to user profile if sign in successful
                    Intent intent = new Intent(getApplicationContext(), FillUserDetailsActivity.class);
                    //so that after login if user presses back button, he does not go back to login screen again
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    finish();
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
