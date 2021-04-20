package com.example.bugster;

public class UserHelperClass {
   // String FullName
    String UserName, EmailAddress, Password, PhoneNumber;


    public UserHelperClass( String userName, String emailAddress, String password, String phoneNumber) {
        //FullName = fullName;
        UserName = userName;
        EmailAddress = emailAddress;
        Password = password;
        PhoneNumber = phoneNumber;
    }

    public String getUserName() {
        return UserName;
    }
    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmailAddress() {
        return EmailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        EmailAddress = emailAddress;
    }

    public String getPassword() { return Password; }
    public void setPassword(String password) {
        Password = password;
    }

    public String getPhoneNumber() { return PhoneNumber; }
    public void setPhoneNumber(String phoneNumber) { PhoneNumber = phoneNumber; }
}
