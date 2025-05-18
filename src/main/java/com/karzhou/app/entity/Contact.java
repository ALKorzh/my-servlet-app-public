package com.karzhou.app.entity;

public class Contact {
    private String lastName;
    private String phoneNumber;

    public Contact(String lastName, String phoneNumber) {
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
