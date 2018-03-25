package com.example.petchi.driveremergency;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.petchi.driveremergency.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class MedicalRecord extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private FirebaseDatabase mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    Button btn_next;
    EditText et_name, et_age, et_gender, et_allergy_medicine, et_medical_history, et_address, et_mobile, et_vehicle;
    Spinner sp_blood_group;
    String bloodgroup;
    DatabaseReference UsersNode;

    SharedPreferences.Editor editor;

    String MY_PREFS_NAME = "MYDB";
    SharedPreferences prefs;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.medical_record);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Medical Record");
        }

        String MY_PREFS_NAME = "MYDB";
        editor = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE).edit();
        Gson gson = new Gson();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String json = prefs.getString("UserObj", "");

        User obj = gson.fromJson(json, User.class);
        if (obj == null) {
            /*startActivity(new Intent(this, MainActivity.class));

            finish();*/
        }
//im leaving bye

        btn_next = findViewById(R.id.next);
        et_address = findViewById(R.id.et_address);
        et_age = findViewById(R.id.et_age);
        et_gender = findViewById(R.id.et_gender);
        et_name = findViewById(R.id.et_name1);
        et_allergy_medicine = findViewById(R.id.et_allergy_medicine);
        et_medical_history = findViewById(R.id.et_medical_history);
        sp_blood_group = findViewById(R.id.et_blood_group);
        et_mobile = findViewById(R.id.et_mobile_no);
        et_vehicle = findViewById(R.id.et_vehicle_no);


        UsersNode = FirebaseDatabase.getInstance().getReference("Users");
        spinnersinit();

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int a ;
                a = nullcheck();
                if (a != -1) {

                    User user = new User();
                    String name = et_name.getText().toString().trim();
                    int Age = Integer.parseInt(et_age.getText().toString().trim());
                    String Gender = et_gender.getText().toString().trim();
                    String AllergyMedicine = et_allergy_medicine.getText().toString().trim();
                    String MedicalHistory = et_medical_history.getText().toString().trim();
                    String Address = et_address.getText().toString().trim();
                    long MobileNumber = Long.parseLong(et_mobile.getText().toString().trim());
                    String VehicleNumber=et_vehicle.getText().toString().trim();


                    user.setName(name);
                    user.setAge(Age);
                    user.setGender(Gender);
                    user.setBloodgroup(bloodgroup);
                    user.setVehicleNumber(VehicleNumber);
                    user.setMobileNumber(MobileNumber);
                    user.setAllergyMedicine(AllergyMedicine);
                    user.setMedicalHistory(MedicalHistory);
                    user.setAddress(Address);


                    String UUID = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    user.setUuid(UUID);
                    //pushing user to db


                    UsersNode.child(UUID).setValue(user);


                    //storing offline copy of UserObject in sharedpred under MYDB
                    Gson gson = new Gson();
                    String userobjectasstring = gson.toJson(user);
                    editor.putString("UserObj", userobjectasstring);

                    editor.apply();



                    Intent intent = new Intent(getApplicationContext(), EmergencyContacts.class);
                    startActivity(intent);
                    finish();

                }


            }
        });

    }

    private int nullcheck() {

        String name = et_name.getText().toString().trim();
        String Age = et_age.getText().toString().trim();
        String Gender = et_gender.getText().toString().trim();
        String AllergyMedicine = et_allergy_medicine.getText().toString().trim();
        String MedicalHistory = et_medical_history.getText().toString().trim();
        String Address = et_address.getText().toString().trim();
        String MobileNumber = (et_mobile.getText().toString().trim());
        String VehicleNumber=et_vehicle.getText().toString().trim();



        if (name.isEmpty() || TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
            return -1;
        }

        if (TextUtils.isEmpty(String.valueOf(Age))) {
            Toast.makeText(this, "Enter Age", Toast.LENGTH_SHORT).show();
        }
        if (Gender.isEmpty() || TextUtils.isEmpty(Gender)) {
            Toast.makeText(this, "Enter Gender", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (bloodgroup.equals("Null")) {
            Toast.makeText(this, "Choose Blood Group", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (VehicleNumber.isEmpty() || TextUtils.isEmpty(VehicleNumber)) {
            Toast.makeText(this, "Enter Vehicle Number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (TextUtils.isEmpty(String.valueOf(MobileNumber))) {
            Toast.makeText(this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (MobileNumber.length() != 10) {
            Toast.makeText(this, "Check the Number", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (AllergyMedicine.isEmpty() || TextUtils.isEmpty(AllergyMedicine)) {
            Toast.makeText(this, "Enter Allergy Medicine", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (MedicalHistory.isEmpty() || TextUtils.isEmpty(MedicalHistory)) {
            Toast.makeText(this, "Enter Medical History", Toast.LENGTH_SHORT).show();
            return -1;
        }
        if (Address.isEmpty() || TextUtils.isEmpty(Address)) {
            Toast.makeText(this, "Enter Address", Toast.LENGTH_SHORT).show();
            return -1;
        }


        return 0;
    }

    private void spinnersinit() {
        final Spinner spinner = findViewById(R.id.et_blood_group);
        // Initializing a String Array
        String[] blood = new String[]{
                "Select One...",
                "AB+ve",
                "B+ve",
                "O+ve",
                "A+ve",
                "AB-ve",
                "B-ve",
                "O-ve",
                "A-ve"
        };
        spinner.setOnItemSelectedListener(this);

        final List<String> plantsList = new ArrayList<>(Arrays.asList(blood));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, plantsList);

        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(spinnerArrayAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String string = adapterView.getItemAtPosition(i).toString().trim();
        bloodgroup = string.equals("Select One...") ? "Null" : string;

/*
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
*/

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
