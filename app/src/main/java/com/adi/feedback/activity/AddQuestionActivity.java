package com.adi.feedback.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.adi.feedback.R;
import com.adi.feedback.adapter.AddOptionsAdapter;
import com.adi.feedback.global.DbHelper;
import com.adi.feedback.global.GlobalFunctions;

import java.util.ArrayList;
import java.util.List;


public class AddQuestionActivity extends AppCompatActivity implements OnClickListener {

    private static final String TAG = AddQuestionActivity.class.getSimpleName();


    Toolbar toolbar;

    EditText etQuestion;

    RecyclerView rvOptions;


    TextView tvAddQuestions;

    AddOptionsAdapter adapter;
    Context mContext;
    List<String> options = new ArrayList<>();
    DbHelper db;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_questions);


        toolbar=findViewById(R.id.toolbar);
        etQuestion=findViewById(R.id.etQuestion);
        rvOptions=findViewById(R.id.rvOptions);
        tvAddQuestions=findViewById(R.id.tvAddQuestion);
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
        tvToolbarTitle.setText("Create Questions");

        GlobalFunctions.createVerticalRecyclerView(mContext, rvOptions);

        options.add("option");
        adapter = new AddOptionsAdapter(mContext, options);
        rvOptions.setAdapter(adapter);

        tvAddQuestions.setOnClickListener(this);

    }

    private void addQuestionToDB(){
        String question = etQuestion.getText().toString();
        db.addQuestion(question, removeJunkOptions());
        GlobalFunctions.stopProgressDialog(dialog);

        Intent i = new Intent(mContext, AddQuestionActivity.class);
        startActivity(i);
        finish();
    }

    private List<String> removeJunkOptions(){
        List<String> opt = new ArrayList<>();
        Log.e(TAG, "Option: " + options.toString());
        for(String option: AddOptionsAdapter.options) {
            Log.e(TAG, "Option: " + option);
            if(!option.equalsIgnoreCase("option")){
                opt.add(option);
            }
        }
        return opt;
    }

    private boolean checkOptions(){
        for(String option: AddOptionsAdapter.options) {
            if(option.equalsIgnoreCase("") || option.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvAddQuestion:
                GlobalFunctions.startProgressDialog(dialog, "Please wait");
                String question = etQuestion.getText().toString();
                if(question.isEmpty() || question.equalsIgnoreCase("")) {
                    GlobalFunctions.toastShort(mContext, "Enter question");
                } else if(!checkOptions()) {
                    GlobalFunctions.toastShort(mContext, "Enter options for question");
                } else {
                    addQuestionToDB();
                }
                break;
        }
    }
}
