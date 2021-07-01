package com.adi.feedback.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.adi.feedback.R;
import com.adi.feedback.global.DbHelper;
import com.adi.feedback.global.GlobalFunctions;
import com.adi.feedback.model.UserResponce.UserLogin;
import com.adi.feedback.util.SessionManager;

import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = MainActivity.class.getSimpleName();


    Toolbar toolbar;

    TextView tvFeedback;

    TextView tvExportFeedback;

    TextView tvAddQuestion;

    TextView tvClear,addrs,name;

    private ProgressDialog dialog;
    private Context mContext;
    private DbHelper db;
    SessionManager sessionManager;
    UserLogin user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        tvFeedback=findViewById(R.id.tvFeedback);
        tvAddQuestion=findViewById(R.id.tvAddQuestion);
        tvExportFeedback=findViewById(R.id.tvExportFeedback);
        tvClear=findViewById(R.id.tvClear);
        mContext = this;
        name=findViewById(R.id.name);
        addrs=findViewById(R.id.addrs);
        dialog = new ProgressDialog(mContext);
        db = new DbHelper(mContext);
        sessionManager=new SessionManager(this);
        user= sessionManager.getUserDetails("UserLogin");
        name.setText("Welcome "+user.getName());
        addrs.setText(""+user.getAddress());

        TextView tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        tvToolbarTitle.setText("Welcome");

        tvAddQuestion.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvExportFeedback.setOnClickListener(this);
        tvFeedback.setOnClickListener(this);
    }

    private void clearPopup() {

        final Dialog popupDialog = new Dialog(mContext);
        popupDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        popupDialog.setContentView(R.layout.global_dialog);
        popupDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;

        TextView tvClose = (TextView) popupDialog.findViewById(R.id.tvClose);
        TextView tvClearAll = (TextView) popupDialog.findViewById(R.id.tvClearAll);
        TextView tvClearFeedback = (TextView) popupDialog.findViewById(R.id.tvClearFeedback);

        tvClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupDialog.dismiss();
            }
        });

        tvClearAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearAll();
                GlobalFunctions.toastShort(mContext, "Feedback and Questions cleared");
                popupDialog.dismiss();
            }
        });

        tvClearFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db.clearFeedback();
                GlobalFunctions.toastShort(mContext, "Feedback cleared");
                popupDialog.dismiss();
            }
        });

        try{
            popupDialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

        popupDialog.setCancelable(false);
        popupDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvFeedback:
                Intent feedbackIntent = new Intent(mContext, UserDetailsActivity.class);
                startActivity(feedbackIntent);
                break;

            case R.id.tvExportFeedback:
                db.exportData();
                GlobalFunctions.toastShort(mContext, "Feedback excel stored in SDCard");
                break;

            case R.id.tvAddQuestion:
                Intent questionIntent = new Intent(mContext, AddQuestionActivity.class);
                startActivity(questionIntent);
                break;

            case R.id.tvClear:
                sessionManager.logoutUser();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
                finish();
                break;
        }
    }

//    JSONObject jsonObject = new JSONObject();
//                    try {
//        jsonObject.put("uid", user.getId());
//        jsonObject.put("ref_code", code);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//
//    }
//    RequestBody bodyRequest = RequestBody.create(MediaType.parse(jsons), jsonObject.toString());
//    Call<CouponResponse> call = APIClient.getInterface().
}
