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

public class Registration extends AppCompatActivity {

    private Button buttonRegistrationToLog;
    private Button buttonRegister;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    TextInputLayout regUserName, regPassword, regEmail, regPhoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        buttonRegistrationToLog = (Button) findViewById(R.id.buttonRegToLog);
        buttonRegister = (Button) findViewById(R.id.regButton);
        regUserName = findViewById(R.id.regUserName);
        regPassword = findViewById(R.id.regPassword);
        regEmail = findViewById(R.id.regEmail);
        regPhoneNumber = findViewById(R.id.regPhoneNumber);

        buttonRegistrationToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogButton();
            }
        });
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }
    public void openLogButton()
    {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
        finish();
    }

    private  Boolean validateUsername (){
        String val = regUserName.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty()){
            regUserName.setError("Поле не має бути пустим");
            return false;
        } else if(val.length() >= 15){
            regUserName.setError("Нікнейм має бути коротшим");
            return false;
        } else if(!val.matches(noWhiteSpace)){
            regUserName.setError("Пробіли у нікнеймі заборонені");
            return false;
        } else{
            regUserName.setError(null);
            regUserName.setErrorEnabled(false);
            return true;
        }
    }

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();
    }

    private  Boolean validatePassword (){
        String val = regPassword.getEditText().getText().toString();
        if(val.isEmpty()){
            regPassword.setError("Поле не має бути пустим");
            return false;
        } else if(val.length()<8 | !isValidPassword(val)){
            regPassword.setError("Варто додати спеціальний символ(!$#%^) та впевнетись, що пароль містить мінімум 8 символів, букви малого й великого регістрів та цифри");
            return false;
        } else{
            regPassword.setError(null);
            regPassword.setErrorEnabled(false);
            return true;
        }
    }

    private  Boolean validateEmail (){
        String emailPattern = ("^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        String val = regEmail.getEditText().getText().toString();
        if(val.isEmpty()){
            regEmail.setError("Поле не має бути пустим");
            return false;
        } else if(!val.matches(emailPattern)){
            regEmail.setError("Невірна адреса електронної пошти");
            return false;
        }
        else{
            regEmail.setError(null);
            regEmail.setErrorEnabled(false);
            return true;
        }
    }

    private  Boolean validatePhoneNumber (){
        String val = regPhoneNumber.getEditText().getText().toString();
        if(val.isEmpty()){
            regUserName.setError("Поле не має бути пустим");
            return false;
        } else{
            regUserName.setError(null);
           return true;
        }
    }

    public void registerUser() {
        if(!validateUsername() | !validateEmail() | !validatePassword() | !validatePhoneNumber()){
            return;
        }

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("User");

        //Get all the values
        String userName = regUserName.getEditText().getText().toString();
        String password = regPassword.getEditText().getText().toString();
        String emailAddress = regEmail.getEditText().getText().toString();
        String phoneNumber = regPhoneNumber.getEditText().getText().toString();

        UserHelperClass UserHelperClass = new UserHelperClass(userName, emailAddress, password, phoneNumber);
        reference.child(userName).setValue(UserHelperClass);

        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

}