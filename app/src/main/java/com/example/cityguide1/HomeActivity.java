package com.example.cityguide1;

import android.content.Intent;
import android.os.Bundle;
import android.os.UserHandle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class HomeActivity extends AppCompatActivity {

    //FirebaseDatabase database;
    //DatabaseReference login;
    EditText uname, pwd;
    FirebaseAuth firebaseAuth;
    public Button log, sign;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        uname=findViewById(R.id.uname);
        pwd=findViewById(R.id.pwd);
        sign = (Button) findViewById(R.id.signup);
        log = (Button) findViewById(R.id.log);
        firebaseAuth= FirebaseAuth.getInstance();
        /* if(firebaseAuth.getCurrentUser()!=null)
                    {
                        startActivity(new Intent(getApplicationContext(),setting3.class));
                        finish();
                    }*/
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
           });
                     log.setOnClickListener(new View.OnClickListener() {
                      @Override
                     public void onClick(View v) {
                          login();

                     }
                    });
    }

    public void login() {
        String email=uname.getText().toString();
        String password=pwd.getText().toString();

           if(email.isEmpty()){
            uname.setError("Enter your email ");
            uname.requestFocus();
             }
           else if(password.isEmpty()){
               pwd.setError("Enter your password");
               pwd.requestFocus();
           }
           else if(!email.isEmpty()&&!password.isEmpty()){
               firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                   @Override
                   public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(HomeActivity.this,"login successfull",Toast.LENGTH_SHORT).show();
                       startActivity(new Intent(HomeActivity.this,setting3.class));
                   }
                   else
                   {
                       Toast.makeText(HomeActivity.this,"login faild",Toast.LENGTH_SHORT).show();

                   }

                   }
               });
           }
    }

    public void signup(){
        Intent intent=new Intent(HomeActivity.this,signup.class);
        startActivity(intent);
    }
}




