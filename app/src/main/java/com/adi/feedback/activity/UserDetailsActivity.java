package com.adi.feedback.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adi.feedback.R;
import com.adi.feedback.global.DbHelper;
import com.adi.feedback.global.GlobalFunctions;
import com.adi.feedback.global.SharedPrefsGetSet;

//import butterknife.Bind;
//import butterknife.ButterKnife;


/**
 * Created by root on 8/8/16.
 */
public class UserDetailsActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;

    EditText etName;

    EditText etEmail;

    EditText etPhone;

    EditText etAddress;

    TextView tvSubmit;

    String email, phone, name, address;

    DbHelper db;
    ProgressDialog dialog;
    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        toolbar = findViewById(R.id.toolbar);
        etName = findViewById(R.id.etName);
        etEmail = findViewById(R.id.etEmail);
        etPhone = findViewById(R.id.etPhone);
        etAddress = findViewById(R.id.etAddress);
        tvSubmit = findViewById(R.id.tvSubmit);

        mContext = this;
        db = new DbHelper(mContext);
        dialog = new ProgressDialog(mContext);

        setSupportActionBar(toolbar);
        TextView tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        tvToolbarTitle.setText("User Information");

        if(SharedPrefsGetSet.getUserId(mContext) != 0) {
            startQuestionActivity();
        }

        tvSubmit.setOnClickListener(this);
    }

    private boolean validateFields(){
        email = etEmail.getText().toString();
        name = etName.getText().toString();
        phone = etPhone.getText().toString();
        address = etAddress.getText().toString();

        if(email.isEmpty() || email.equalsIgnoreCase("")) {
            GlobalFunctions.toastShort(mContext, "Enter email");
            return false;
        } else if(name.isEmpty() || name.equalsIgnoreCase("")) {
            GlobalFunctions.toastShort(mContext, "Enter name");
            return false;
        } else if(phone.isEmpty() || phone.equalsIgnoreCase("")){
            GlobalFunctions.toastShort(mContext, "Enter phone");
            return false;
        } else if(address.isEmpty() || address.equalsIgnoreCase("")) {
            GlobalFunctions.toastShort(mContext, "Enter address");
            return false;
        } else {
            return true;
        }
    }

    private void startQuestionActivity(){
        Intent i = new Intent(mContext, QuestionActivity.class);
        startActivity(i);
        finish();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSubmit:
                if(validateFields()){
                    GlobalFunctions.startProgressDialog(dialog, "Please wait");
                    int userId = db.addUser(email, name, phone, address);
                    SharedPrefsGetSet.setUserId(mContext, userId);
                    SharedPrefsGetSet.setUserDetails(mContext, name,email,phone,address);
                    GlobalFunctions.stopProgressDialog(dialog);

                    startQuestionActivity();
                }
                break;
        }
    }
}
