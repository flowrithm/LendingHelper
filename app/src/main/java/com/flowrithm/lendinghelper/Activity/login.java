package com.flowrithm.lendinghelper.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.flowrithm.lendinghelper.R;
import com.google.firebase.FirebaseException;


public class login extends AppCompatActivity {

    EditText loginusername;
    EditText loginpassword;
    Button login;
    TextView registernow;
    TextView forgetpassword;
    
    FirebaseException e;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginusername=findViewById(R.id.edtusername);
        loginpassword=findViewById(R.id.edtloginpassword);
        login=findViewById(R.id.btnlogin);
        registernow=findViewById(R.id.txtregisternow);
        forgetpassword=findViewById(R.id.txtforgotpassword);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                registernow.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(login.this,register.class);
                        startActivity(intent);
                    }
                });

                final String User=loginusername.getText().toString();
                final String Password=loginpassword.getText().toString();

                if(User.length()==0){
                    loginusername.requestFocus();
                    loginusername.setError("Field Can not be Empty");
                }
                else if(!User.matches("[a-zA-Z ]+"))
                {
                    loginusername.requestFocus();
                    loginusername.setError("Enter Only Alphabetical Character");
                }

                String passReg = "^([a-zA-Z0-9]+)$";

                if (!(Password.length() < 3 || Password.length() > 15)) {
                    if (Password.matches(passReg)) {
                        /*password.requestFocus();
                        password.setError("Enter valid Password");*/
                    } else {
                        loginpassword.requestFocus();
                        loginpassword.setError("Password format error");
                    }
                } else {
                    loginpassword.requestFocus();
                    loginpassword.setError("Password should be 3-15 long");
                }


            }
        });


    }
}
