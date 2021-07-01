package com.adi.feedback.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import com.adi.feedback.R;


/**
 * Created by root on 9/8/16.
 */
public class ThankyouActivity extends AppCompatActivity implements View.OnClickListener {

    Toolbar toolbar;

    TextView tvAddNewUser;

    Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thankyou);
        tvAddNewUser = findViewById(R.id.tvAddNewUser);
        toolbar = findViewById(R.id.toolbar);
        mContext = this;

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
        tvToolbarTitle.setText("Thank you");

        tvAddNewUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAddNewUser:
                finish();
                break;
        }
    }
}
