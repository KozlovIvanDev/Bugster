package com.example.bugster;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    private Button buttonLogToRegister;

    TextInputLayout regUserName, regPassword, regEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);
        buttonLogToRegister = (Button) findViewById(R.id.buttonLogToRegister);
        buttonLogToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openButtonLogToRegister();
            }
        });
    }
    public void openButtonLogToRegister() {
        Intent intent = new Intent(this, Registration.class);
        startActivity(intent);
        finish();
    }

    private  Boolean validateEmail (){
        String val = regEmail.getEditText().getText().toString();
        if(val.isEmpty()){
            regEmail.setError("Поле не має бути пустим");
            return false;
        }
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }
    private  Boolean validatePassword (){
        String val = regPassword.getEditText().getText().toString();
        if(val.isEmpty()){
            regPassword.setError("Поле не має бути пустим");
            return false;
        }
         else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }


    public void loginUser(View view) {
        //Validate Login Info
        if (!validateEmail() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    public void isUser() {
        String userEnteredEmail = regUserName.getEditText().getText().toString().trim();
        String userEnteredPassword = regPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUser = reference.orderByChild("emailAddress").equalTo(userEnteredEmail);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){

                    regUserName.setError(null);
                    regUserName.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredEmail).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)){

                        regUserName.setError(null);
                        regUserName.setErrorEnabled(false);

                        String emailFromDB = snapshot.child(userEnteredEmail).child("emailAddress").getValue(String.class);
                        String phoneNumberFromDB = snapshot.child(userEnteredEmail).child("phoneNumber").getValue(String.class);
                        String userNameFromDB = snapshot.child(userEnteredEmail).child("userName").getValue(String.class);

                        Intent intent = new Intent(getApplicationContext(), UserProfile.class);

                        intent.putExtra("emailAddress", emailFromDB);
                        intent.putExtra("phoneNumber", phoneNumberFromDB);
                        intent.putExtra("userName", userNameFromDB);
                        intent.putExtra("password", passwordFromDB);

                        startActivity(intent);
                    }
                    else {
                        regPassword.setError("Неправильний пароль");
                        regPassword.requestFocus();
                    }
                }
                else{
                    regUserName.setError("Користувача не існує");
                    regUserName.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}