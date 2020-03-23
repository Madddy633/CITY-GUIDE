package com.example.cityguide1;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registerbusiness extends AppCompatActivity {

    public static final String TAG = "TAG";
    //FirebaseDatabase database;
    // DatabaseReference login;
    FirebaseAuth fauthent;
    FirebaseFirestore firestore;
    EditText orgname, ownname, email, editpass, confirmpass,mob;
    public Button sign;
    String busID;
    String item;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerbusiness);
        //database = FirebaseDatabase.getInstance();
        //login = database.getReference("login");

        final Spinner spinner=(Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.TypeofOrganization,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        orgname = (EditText) findViewById(R.id.orgname);
        ownname = (EditText) findViewById(R.id.ownername);
        mob = (EditText) findViewById(R.id.busmob);
        email = (EditText) findViewById(R.id.busemail);
        editpass = (EditText) findViewById(R.id.buspass);
        confirmpass = (EditText) findViewById(R.id.buscpass);

        sign = (Button) findViewById(R.id.busregister);

        fauthent= FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
                     /*if(fauth.getCurrentUser()!=null)
                    {
                        startActivity(new Intent(getApplicationContext(),setting3.class));
                        finish();
                    }*/
        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        if (parent.getItemAtPosition(position).equals("organizatin type"))
                        {


                        }
                        else
                            {
                                item=parent.getItemAtPosition(position).toString();
                                    }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }

                });
                String username = email.getText().toString().trim();
                final String ownername = ownname.getText().toString().trim();
                final String organization = orgname.getText().toString().trim();
                final String mobile = mob.getText().toString().trim();
                String password = editpass.getText().toString().trim();
                String confirmpassword = confirmpass.getText().toString().trim();
                if (TextUtils.isEmpty(username)) {
                    email.setError("email is required");
                    return;
                }
                if (TextUtils.isEmpty(ownername)) {
                    ownname.setError("name is required");
                    return;
                }
                if (TextUtils.isEmpty(mobile)) {
                    mob.setError("phoneno is required");
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    editpass.setError("password is required");
                    return;
                }
                if (TextUtils.isEmpty(confirmpassword)) {
                    confirmpass.setError(" * required");
                    return;
                }
                fauthent.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task1) {

                        if(task1.isSuccessful()){
                            Toast.makeText(Registerbusiness.this,"user is created",Toast.LENGTH_SHORT).show();
                            busID=fauthent.getCurrentUser().getUid();
                            DocumentReference docReference=firestore.collection("business").document(busID);
                            Map<String,Object> business=new HashMap<>();
                            business.put("orgname",orgname);
                            business.put("ownname",ownname);
                            business.put("mob",mobile);
                            business.put("orgtype",item);
                            docReference.set(business).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG ,"onsuccess:user profile is updated"+busID);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                        }
                        else
                        {
                            Toast.makeText(Registerbusiness.this,"unsuccess",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });
    }

}
