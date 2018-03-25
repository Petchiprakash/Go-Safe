package com.example.petchi.driveremergency;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.petchi.driveremergency.Model.User;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager locationManager;
    DatabaseReference usersNode;
    TextView tv_data, tv_contacts;
    android.support.v7.widget.Toolbar tbprakash;
    String MY_PREFS_NAME = "MYDB";
    SharedPreferences prefs;
    BroadcastReceiver sendBroadcastReceiver;
    BroadcastReceiver deliveryBroadcastReceiver;
    PendingIntent deliveredPI;
    PendingIntent sentPI;
    String mytext;
    String contacts;
    String number;
    String text;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        Gson gson = new Gson();
        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);

        String json = prefs.getString("UserObj", "");

        User obj = gson.fromJson(json, User.class);
        if (obj == null) {
            startActivity(new Intent(this, MedicalRecord.class));

            finish();
        }

        tv_data = findViewById(R.id.tv_name);
        tv_contacts = findViewById(R.id.tv_contacts);

        tbprakash = findViewById(R.id.tbprakash);


        //database reference pointing to root of database
        usersNode = FirebaseDatabase.getInstance().getReference("Users");
        //database reference pointing to demo node
        FirebaseAuth firebaseAuth
                = FirebaseAuth.getInstance();
        FirebaseUser user = null;
        if (firebaseAuth != null)
            user = firebaseAuth.getCurrentUser();
        else {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }
        if (user != null) {
            String UUID = user.getUid();
            usersNode.child(UUID).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User user = dataSnapshot.getValue(User.class);
                    mytext = user.getName() + "\n" + user.getAge() + "\n" + user.getGender() + "\n" + user.getBloodgroup() + "\n" + user.getVehicleNumber() + "\n" + user.getMobileNumber() + "\n" + user.getAllergyMedicine() + "\n" + user.getMedicalHistory() + "\n" + user.getAddress();
                    tv_data.setText(mytext);
                    contacts = "Person 1 Details \n" + user.getUserContacts().getPname1() + "\n" + user.getUserContacts().getPmob1() + "\n" + user.getUserContacts().getRel1() + "\n" + user.getUserContacts().getPname2() + "\n" + user.getUserContacts().getPmob2() + "\n" + user.getUserContacts().getRel2() + "\n" + user.getUserContacts().getPname3() + "\n" + user.getUserContacts().getPmob3() + "\n" + user.getUserContacts().getRel3();
                    tv_contacts.setText(contacts);
                    number= String.valueOf(user.getUserContacts().getPmob1());
                    text=user.getName();
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            startActivity(new Intent(this, MedicalRecord.class));
            finish();

        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        sendSMS(number,text);



    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        mMap.getUiSettings().setCompassEnabled(true);

        mMap.getUiSettings().setTiltGesturesEnabled(true);
        mMap.getUiSettings().setRotateGesturesEnabled(true);
        mMap.getUiSettings().setScrollGesturesEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);


        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        // Add a marker in Sydney and move the camera
        // LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        // return true so that the menu pop up is opened
        return true;
    }

    @SuppressLint("CommitPrefEdits")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                //delete SharedPref
                prefs.edit().clear().apply();
                //clearing App data

                startActivity(new Intent(MapsActivity.this, MainActivity.class));
                finish();
                /*
                        startActivity(new Intent(this, SignUp.class));
                        finish();
*/

                break;

            case R.id.share:
                String textlong = "Through this digital platform," +
                        " we urge you to fulfill your supreme duty as a human being" +
                        " to save a life, by becoming a proud registered volunteer." +
                        "\n" +
                        "\n" +
                        "You may register as blood donor (Your 1 unit of blood can save 3 lives)\n" +
                        "Share opportunity with others – Build blood donors network in your city/town by sharing the app with your friends\n";

                // String message =  installapp;

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, textlong);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void sendSMS(String phoneNumber, String message) {
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        sentPI = PendingIntent.getBroadcast(this, 0, new Intent(SENT), 0);


        deliveredPI = PendingIntent.getBroadcast(this, 0, new Intent(DELIVERED), 0);
        //---SMS has been sent---
        sendBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
        registerReceiver(sendBroadcastReceiver, new IntentFilter(SENT));
        //---when the SMS has been delivered---
        deliveryBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };


        registerReceiver(deliveryBroadcastReceiver, new IntentFilter(DELIVERED));
        SmsManager sms = SmsManager.getDefault();
        try{
            android.telephony.SmsManager mSmsManager = android.telephony.SmsManager.getDefault();
            sms.sendTextMessage(phoneNumber, null, message,sentPI, deliveredPI);
            Toast.makeText(getApplicationContext(), "Your SMS has sent successfully!", Toast.LENGTH_LONG).show();

        }

        catch(Exception e) {
            Toast.makeText(getApplicationContext(), "Your SMS sent has failed!", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }
    @Override
    protected void onStop() {
        try {
            if (sentPI != null) {
                unregisterReceiver(sendBroadcastReceiver);
                sentPI = null;
            }

            if (deliveredPI != null) {
                unregisterReceiver(deliveryBroadcastReceiver);
                deliveredPI =null;
            }
        } catch (Exception e) {

        }
        super.onStop();
    }
}
