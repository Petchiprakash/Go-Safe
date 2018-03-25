package com.example.petchi.driveremergency.Model;

/**
 * Created by petch on 19-03-2018.
 */


public class User {

    String name;
    int age;
    String gender;
    String bloodgroup;
    String allergyMedicine;
    String medicalHistory;
    String address;
    long mobileNumber;
    String vehicleNumber;
    String uuid;
    UserContacts userContacts;


    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }


    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public UserContacts getUserContacts() {
        return userContacts;
    }

    public void setUserContacts(UserContacts userContacts) {
        this.userContacts = userContacts;
    }


    public User(String name, int age, String gender, String bloodGroup, String allergyMedicine, String medicalHistory, String address, long mobileNumber) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        bloodgroup = bloodGroup;
        this.allergyMedicine = allergyMedicine;
        this.medicalHistory = medicalHistory;
        this.address = address;
        this.mobileNumber = mobileNumber;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getAllergyMedicine() {
        return allergyMedicine;
    }

    public void setAllergyMedicine(String allergyMedicine) {
        this.allergyMedicine = allergyMedicine;
    }

    public String getMedicalHistory() {
        return medicalHistory;
    }

    public void setMedicalHistory(String medicalHistory) {
        this.medicalHistory = medicalHistory;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
