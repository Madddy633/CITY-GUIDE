package com.example.cityguide1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.proto.TargetGlobal;

import java.util.HashMap;
import java.util.Map;

public class signup extends AppCompatActivity{
    public static final String TAG = "TAG";
    //FirebaseDatabase database;
   // DatabaseReference login;
    FirebaseAuth fauth;
    FirebaseFirestore fstore;
    EditText uname, editage, editmob, editpass, editcpass;
    public Button sign, log;
    String userID;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        //database = FirebaseDatabase.getInstance();
        //login = database.getReference("login");

        textView=(TextView)findViewById(R.id.txt_view);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(signup.this,Registerbusiness.class);
                startActivity(intent);


            }
        });

        uname = (EditText) findViewById(R.id.uname);
        editage = (EditText) findViewById(R.id.editage);
        editmob = (EditText) findViewById(R.id.editmob);
        editpass = (EditText) findViewById(R.id.editpass);
        editcpass = (EditText) findViewById(R.id.editcpass);

        sign = (Button) findViewById(R.id.Register);
        log = (Button) findViewById(R.id.log);
        fauth = FirebaseAuth.getInstance();
        fstore=FirebaseFirestore.getInstance();
                     /*if(fauth.getCurrentUser()!=null)
                    {
                        startActivity(new Intent(getApplicationContext(),setting3.class));
                        finish();
                    }*/
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = uname.getText().toString().trim();
                final String age = editage.getText().toString().trim();
                final String mobile = editmob.getText().toString().trim();
                String password = editpass.getText().toString().trim();
                String confirmpassword = editcpass.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    uname.setError("username is required");
                    return;
                }
                if (TextUtils.isEmpty(age)) {
                    editage.setError("age is required");
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    editmob.setError("phoneno is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editpass.setError("password is required");
                    return;
                }
                if (TextUtils.isEmpty(confirmpassword)) {
                    editcpass.setError(" * required");
                    return;
                }
                fauth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       Toast.makeText(signup.this,"user is created",Toast.LENGTH_SHORT).show();
                       userID=fauth.getCurrentUser().getUid();
                       DocumentReference documentReference=fstore.collection("users").document(userID);
                       Map<String,Object> user=new HashMap<>();
                                  user.put("age",age);
                                  user.put("mobileno",mobile);
                                  documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                      @Override
                                      public void onSuccess(Void aVoid) {
                                          Log.d(TAG ,"onsuccess:user profile is updated"+userID);
                                      }
                                  });

                       startActivity(new Intent(getApplicationContext(),setting3.class));
                   }
                           else
                              {
                                  Toast.makeText(signup.this,"error occured!",Toast.LENGTH_SHORT).show();

                               }
                    }
                });
            }
        });
    }


}

