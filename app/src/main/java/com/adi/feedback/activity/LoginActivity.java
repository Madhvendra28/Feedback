package com.adi.feedback.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.adi.feedback.R;
import com.adi.feedback.model.AddressResponse.Address;
import com.adi.feedback.model.AddressResponse.AdressResponse;
import com.adi.feedback.model.UserResponce.UserResponse;
import com.adi.feedback.retrofit.APIClient;
import com.adi.feedback.util.SessionManager;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener{

    EditText logemail,logpassword,signemail,signmobileno,signpassword,signconpass,sname;
    TextView loginbtn,signupbtn,registertext,logintext;
    LinearLayout loginLayout,signupLayout;

    String jsons="application/json";
    boolean isEmailValid, isPasswordValid, signemailValid, signpasswordValid, signmobilenoValid, signconpassValid;

    String address;
    Spinner spin;
    SessionManager sessionManager;

    ArrayList<String> country = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logemail = findViewById(R.id.email);
        logpassword = findViewById(R.id.password);
        signemail = findViewById(R.id.signemail);
        signmobileno = findViewById(R.id.signMobile);
        signpassword = findViewById(R.id.signPassword);
        signconpass = findViewById(R.id.confPassword);
        loginbtn = findViewById(R.id.login);
        signupbtn = findViewById(R.id.signup);
        registertext = findViewById(R.id.register);
        logintext = findViewById(R.id.loginNow);
        loginLayout = findViewById(R.id.loginLayout);
        signupLayout = findViewById(R.id.signupLayout);
        sname = findViewById(R.id.sname);
        sessionManager= new SessionManager(this);

        getData();


        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signData();
            }
        });

         spin = (Spinner) findViewById(R.id.spinner);
        spin.setOnItemSelectedListener(this);





        logintext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginLayout.setVisibility(View.VISIBLE);
                signupLayout.setVisibility(View.GONE);
                Log.d("LoginText","Register Now clicked");
            }
        });

        registertext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupLayout.setVisibility(View.VISIBLE);
                loginLayout.setVisibility(View.GONE);
                Log.d("LoginText","Login Now clicked");
            }
        });
    }

    private void getData() {
        Call<AdressResponse>call= APIClient.getInterface().getAddressData();
        call.enqueue(new Callback<AdressResponse>() {
            @Override
            public void onResponse(Call<AdressResponse> call, Response<AdressResponse> response) {
                AdressResponse adressResponse = response.body();

                Log.d("serajdata","response recieved");
                for (int i = 0; i < adressResponse.getAddresses().size(); i++)
                {
                    Log.d("serajdata","response recieved "+adressResponse.getAddresses().get(i).getCity());
                    Address address=adressResponse.getAddresses().get(i);
                    country.add(address.getCity()+","+address.getState()+","+address.getCountry());
                }

                setrSpinner();
                //Creating the ArrayAdapter instance having the country list

            }

            @Override
            public void onFailure(Call<AdressResponse> call, Throwable t) {
                Log.d("serajdata","response failed "+t.getLocalizedMessage());

            }
        });
    }

    private void setrSpinner() {
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,country);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        spin.setAdapter(aa);
    }

    private void signData() {
        String mobile=signmobileno.getText().toString().trim();
        String name=sname.getText().toString().trim();
        String email=signemail.getText().toString().trim();
        String password=signpassword.getText().toString().trim();
        String confirmPassword=signconpass.getText().toString().trim();

        if (TextUtils.isEmpty(mobile) || TextUtils.isEmpty(email) || TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(LoginActivity.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        }else{
            if (confirmPassword.equals(password)){
                registerUser(mobile,password,name,email);
            }else{
                signpassword.setError("Password must be same");
            }

        }

    }

    private void registerUser(String mobile, String password, String name, String email) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile",mobile);
            jsonObject.put("email",email);
            jsonObject.put("name",name);
            jsonObject.put("password",password);
            jsonObject.put("address",address);
        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse(jsons),jsonObject.toString());
        Call<UserResponse> call = APIClient.getInterface().getRegister(bodyRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse = response.body();
                    if (userResponse.getResult().equalsIgnoreCase("true")){
                        Log.d("mdData","Register Sucessfull");
                        Log.d("mdData", "Successfully register name :" + userResponse.getUserLogin().getName());

                        sessionManager.setUserDetails("UserLogin",userResponse.getUserLogin());
                        sessionManager.setBooleanData("isLoggedIn",true);

                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(LoginActivity.this, ""+userResponse.getResponseMsg(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {
                Log.d("mdData","fail "+t.getLocalizedMessage());
            }
        });
    }

    private void checkData() {
        if (logemail.getText().toString().isEmpty())
        {
            logemail.setError("Enter Email");
            isEmailValid = false;
        }
        else{
            isEmailValid = true;
        }
        if (logpassword.getText().toString().isEmpty())
        {
            logpassword.setError("Enter Password");
            isPasswordValid = false;
        }
        else{
            isPasswordValid = true;
        }
        if (isEmailValid && isPasswordValid) {
//            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(LoginActivity.this,MainActivity.class);
//            startActivity(intent);

            loginNow(logemail.getText().toString(),logpassword.getText().toString());

        }
    }

    private void loginNow(String email, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", email);
            jsonObject.put("password", password);

        } catch (Exception e) {
            e.printStackTrace();

        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse(jsons), jsonObject.toString());
        Call<UserResponse> call = APIClient.getInterface().login(bodyRequest);

        call.enqueue(new Callback<UserResponse>() {
            @Override
            public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {
                if (response.isSuccessful()){
                    UserResponse userResponse=response.body();
                    if (!(userResponse.getUserLogin() == null)) {
                        Log.d("Serajdata", "Successfully login");

                        Log.d("Serajdata", "Successfully login name :" + userResponse.getUserLogin().getName());
                        sessionManager.setUserDetails("UserLogin",userResponse.getUserLogin());
                        sessionManager.setBooleanData("isLoggedIn",true);
                        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        logpassword.setError("Invalide User or Password");
                        Toast.makeText(LoginActivity.this, "Invalid username or password !", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        address=country.get(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}