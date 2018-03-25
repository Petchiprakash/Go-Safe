package com.example.petchi.driveremergency;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.petchi.driveremergency.Model.UserContacts;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class EmergencyContacts extends AppCompatActivity {
    EditText et_name1, et_name2, et_name3, et_num1, et_num2, et_num3, et_rel1, et_rel2, et_rel3;
    Button submit;
    DatabaseReference usernodes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emergency_contacts);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Emergency Contacts");
        }

        usernodes = FirebaseDatabase.getInstance().getReference("Users");

        et_name1 = findViewById(R.id.et_name_emergency_1);
        et_name2 = findViewById(R.id.et_name_emergency_2);
        et_name3 = findViewById(R.id.et_name_emergency_3);
        et_num1 = findViewById(R.id.et_mobile_no_emergency_1);
        et_num2 = findViewById(R.id.et_mobile_no_emergency_2);
        et_num3 = findViewById(R.id.et_mobile_no_emergency_3);
        et_rel1 = findViewById(R.id.et_relationship_1);
        et_rel2 = findViewById(R.id.et_relationship_2);
        et_rel3 = findViewById(R.id.et_relationship_3);
        submit = findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a;
                a = nullcheck();
                if (a != -1) {
                    UserContacts userContacts = new UserContacts();
                    String name1 = et_name1.getText().toString().trim();
                    String name2 = et_name2.getText().toString().trim();
                    String name3 = et_name3.getText().toString().trim();
                    String num1 = et_num1.getText().toString().trim();
                    String num2 = et_num2.getText().toString().trim();
                    String num3 = et_num3.getText().toString().trim();
                    String rel1 = et_rel1.getText().toString().trim();
                    String rel2 = et_rel2.getText().toString().trim();
                    String rel3 = et_rel3.getText().toString().trim();
                    userContacts.setPname1(name1);
                    userContacts.setPmob1(Long.parseLong(num1));
                    userContacts.setRel1(rel1);
                    userContacts.setPname2(name2);
                    userContacts.setPmob2(Long.parseLong(num2));
                    userContacts.setRel2(rel2);
                    userContacts.setPname3(name3);
                    userContacts.setPmob3(Long.parseLong(num3));
                    userContacts.setRel3(rel3);

                    //userContacts
                    String UUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    usernodes.child(UUID).child("userContacts").setValue(userContacts);
                    Toast.makeText(EmergencyContacts.this, "Successfully Stored", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EmergencyContacts.this, MapsActivity.class));
                    finish();

                }
            }
        });

    }

    private int nullcheck() {

        String name1 = et_name1.getText().toString().trim();
        String name2 = et_name2.getText().toString().trim();
        String name3 = et_name3.getText().toString().trim();
        String num1 = et_num1.getText().toString().trim();
        String num2 = et_num2.getText().toString().trim();
        String num3 = et_num3.getText().toString().trim();
        String rel1 = et_rel1.getText().toString().trim();
        String rel2 = et_rel2.getText().toString().trim();
        String rel3 = et_rel3.getText().toString().trim();


        if (name1.isEmpty() || TextUtils.isEmpty(name1)) {
            Toast.makeText(this, "Enter Person one Name", Toast.LENGTH_SHORT).show();
            return -1;
        }

        if (num1.isEmpty() || TextUtils.isEmpty(num1)) {
            Toast.makeText(this, "Enter Person one Number", Toast.LENGTH_SHORT).show();
            return -1;
        }


        if (num1.length() != 10) {
            Toast.makeText(this, "check Person one Number", Toast.LENGTH_SHORT).show();
            return -1;
        }


        if (rel1.isEmpty() || TextUtils.isEmpty(rel1)) {
            Toast.makeText(this, "Enter Person one Relation", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (name2.isEmpty() || TextUtils.isEmpty(name2)) {
            Toast.makeText(this, "Enter Person two Name", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (num2.isEmpty() || TextUtils.isEmpty(num2)) {
            Toast.makeText(this, "Enter Person two Number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (num2.length() != 10) {
            Toast.makeText(this, "Check Person two Number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (rel2.isEmpty() || TextUtils.isEmpty(rel2)) {
            Toast.makeText(this, "Enter Person two Relation", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (name3.isEmpty() || TextUtils.isEmpty(name3)) {
            Toast.makeText(this, "Enter Person three Name", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (num3.isEmpty() || TextUtils.isEmpty(num3)) {
            Toast.makeText(this, "Enter Person three Number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (num3.length() != 10) {
            Toast.makeText(this, "Check Person three Number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (rel3.isEmpty() || TextUtils.isEmpty(rel3)) {
            Toast.makeText(this, "Enter Person three relation", Toast.LENGTH_SHORT).show();
            return -1;

        }
        return 0;
    }


}


