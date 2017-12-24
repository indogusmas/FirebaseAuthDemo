package com.indo.app.firebaseauthdemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnregister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignIn;
    private ProgressDialog progressdialog;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() != null){
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }




        progressdialog = new ProgressDialog(this);
        editTextEmail= (EditText)findViewById(R.id.etEmail);
        editTextPassword = (EditText)findViewById(R.id.etpassword);

        btnregister = (Button)findViewById(R.id.btnregister);

        textViewSignIn = (TextView)findViewById(R.id.textsignIn);

        btnregister.setOnClickListener(this);
        textViewSignIn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == btnregister){
            register();
        }
        if (v == textViewSignIn){
            //
            finish();
            startActivity(new Intent(this, LoginActivity.class));
        }

    }

    private void register() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //is Empty Email

            Toast.makeText(this, "Email not be null", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)){
            //is Empty Password
            Toast.makeText(this, "Password not be null", Toast.LENGTH_LONG).show();
            return;
        }
        progressdialog.setMessage("Register.....");
        progressdialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressdialog.dismiss();
                        if (task.isSuccessful()){
                            //Succesfully
                            finish();
                            startActivity(new Intent(getApplicationContext(), ProfileActivity.class));

                        }else {
                            Log.e("MainAvtivity", task.toString());
                            Toast.makeText(MainActivity.this, "gagal", Toast.LENGTH_LONG).show();

                        }
                    }
                });


    }
}