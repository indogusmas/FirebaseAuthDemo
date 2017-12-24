package com.indo.app.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnLogin;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp;
    private ProgressDialog progresdialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
        }

        btnLogin =(Button)findViewById(R.id.btnlogin);

        editTextEmail = (EditText)findViewById(R.id.txtemail);
        editTextPassword = (EditText)findViewById(R.id.txtpassword);

        textViewSignUp = (TextView) findViewById(R.id.txtsignUp);

        progresdialog = new ProgressDialog(this);

        btnLogin.setOnClickListener(this);
        textViewSignUp.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if (v == btnLogin){
            userLogin();
        }
        if (v == textViewSignUp){
            //Intent to MainActivity
            finish();
            startActivity(new Intent(this, MainActivity.class));
        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            Toast.makeText(this, "Enter Your Email", Toast.LENGTH_LONG).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(this, "Enter Your Password", Toast.LENGTH_LONG).show();
        }

        progresdialog.setMessage("Login....");
        progresdialog.show();

        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progresdialog.dismiss();
                        if (task.isSuccessful()){
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));
                        }
                    }
                });

    }
}
