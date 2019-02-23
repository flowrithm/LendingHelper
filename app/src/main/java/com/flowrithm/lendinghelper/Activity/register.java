package com.flowrithm.lendinghelper.Activity;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.flowrithm.lendinghelper.Api.WebApi;
import com.flowrithm.lendinghelper.Application;
import com.flowrithm.lendinghelper.R;
import com.flowrithm.lendinghelper.Utils.Utils;
import com.kaopiz.kprogresshud.KProgressHUD;

import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class register extends AppCompatActivity {

    TextView txtdate;
    EditText name;
    Spinner spinner;
    EditText contct;
    EditText email;
    EditText address;
    EditText password;
    Button registration;

    private int year, month, day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        txtdate=findViewById(R.id.txtdate);
        name=findViewById(R.id.edtname);
        spinner=findViewById(R.id.spn);
        registration=findViewById(R.id.btnregister);
        contct = findViewById(R.id.edtcontact);
        email=findViewById(R.id.edtemail);
        password=findViewById(R.id.edtpassword);
        address=findViewById(R.id.edtaddress);

        registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String Name=name.getText().toString();
                final String Date=txtdate.getText().toString();
                final String Contact=contct.getText().toString();
                final String Email=email.getText().toString();
                final String Address=address.getText().toString();
                final String Password=password.getText().toString();
                Register();


                //Type list
                if(spinner.getSelectedItem()!=null){

                }else{
                    Toast.makeText(register.this,"Select one",Toast.LENGTH_SHORT).show();
                }

                //Date of birth
                if(Date.isEmpty()){
                    Toast.makeText(register.this,"Select Date",Toast.LENGTH_SHORT).show();
                }else {
                   // Toast.makeText(activity_register.this,"OK",Toast.LENGTH_SHORT).show();
                }



                // Validate name
                if(Name.length()==0)

                {
                    name.requestFocus();
                    name.setError("Field Can not be Empty");
                }

                else if(!Name.matches("[a-zA-Z ]+"))
                {
                    name.requestFocus();
                    name.setError("Enter Only Alphabetical Character");
                }
                /*else
                {
                    Toast.makeText(activity_register.this,"Validation Successful",Toast.LENGTH_LONG).show();
                }*/

                // Validate contact
                if(Contact.length() < 10){
                    contct.requestFocus();
                    contct.setError("Number Can not be less than 10 Digit");
                } else if(!Contact.matches("[0-9]{10}")){
                    contct.requestFocus();
                    contct.setError("Invalid Number Format");
                }

                // Validate Email
                String emailReg = "^([a-z\\d.-_]+)@([a-z\\d-]+)\\.([a-z]{2,8})(\\.[a-z]{2,8})?$";

                if (!Email.matches(emailReg)) {
                    email.requestFocus();
                    email.setError("Enter valid email");
                } else {
                    // If Valid
                }

                // Validate address
                if(Address.length()==0)

                {
                    address.requestFocus();
                    address.setError("Field Can not be Empty");
                }

                else if(!Address.matches("[a-zA-Z ]+"))
                {
                    address.requestFocus();
                    address.setError("Enter Only Alphabetical Character");
                }

                //Validate Password
                String passReg = "^([a-zA-Z0-9]+)$";

                if (!(Password.length() < 3 || Password.length() > 15)) {
                    if (Password.matches(passReg)) {
                        /*password.requestFocus();
                        password.setError("Enter valid Password");*/
                    } else {
                        password.requestFocus();
                        password.setError("Password format error");
                    }
                } else {
                    password.requestFocus();
                    password.setError("Password should be 3-15 long");
                }

            }
        });
        txtdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar=Calendar.getInstance();
                year=calendar.get(Calendar.YEAR);
                month=calendar.get(Calendar.MONTH);
                day=calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog=new DatePickerDialog(register.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        txtdate.setText(dayOfMonth + "-" + (month+1) + "-" +year);
                    }
                }, year, month, day);
                datePickerDialog.show();


            }
        });

    }

    public void Register(){
        final KProgressHUD progress=Utils.ShowDialog(this);
        StringRequest request=new StringRequest(Request.Method.POST, WebApi.POST_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject Jobject = new JSONObject(response);
                    Toast.makeText(register.this,Jobject.getString("Message"),Toast.LENGTH_SHORT).show();
                    if(Jobject.getBoolean("Success")) {
                        register.this.setResult(RESULT_OK);
                        register.this.finish();
                    }

                }catch (Exception e){
                    Log.e("Error", e.getMessage());
                }
                progress.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(register.this,"Error",Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("Name",name.getText().toString());
                map.put("Type",spinner.getSelectedItem().toString());
                map.put("ContactNo",contct.getText().toString());
                map.put("Email",email.getText().toString());
                map.put("Address",address.getText().toString());
                map.put("Password",password.getText().toString());
                return map;
            }
        };
        Application.getInstance().addToRequestQueue(request);
    }
}
