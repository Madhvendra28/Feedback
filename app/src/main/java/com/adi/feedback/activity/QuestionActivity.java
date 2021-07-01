package com.adi.feedback.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;

import com.adi.feedback.R;
import com.adi.feedback.adapter.OptionsAdapter;
import com.adi.feedback.global.DbHelper;
import com.adi.feedback.global.GlobalFunctions;
import com.adi.feedback.global.SharedPrefsGetSet;
import com.adi.feedback.model.GetQuestionResponse.GetQuestionResponse;
import com.adi.feedback.model.GetQuestionResponse.Question;
import com.adi.feedback.model.OptionsData;
import com.adi.feedback.model.QuestionData;
import com.adi.feedback.retrofit.APIClient;


import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Madhvendra on 25/06/2021.
 */
public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = QuestionActivity.class.getSimpleName();


    Toolbar toolbar;

    TextView tvQuestion;
    String jsons="application/json";

    RecyclerView rvOptions;

    TextView tvSubmit;

    String question, option;
    int userId;
    List<OptionsData> options = new ArrayList<>();
    Context mContext;
    DbHelper db;
    ProgressDialog dialog;
    OptionsAdapter adapter;
    List<QuestionData> questions = new ArrayList<>();
    int position = 0, totalQuestions;
    boolean isSubmitted = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);

        mContext = this;
        db = new DbHelper(mContext);
        dialog = new ProgressDialog(mContext);
        getQuestion();
        tvQuestion = findViewById(R.id.tvQuestion);
        rvOptions = findViewById(R.id.rvOptions);
        tvSubmit = findViewById(R.id.tvSubmit);

        setSupportActionBar(toolbar);
        //TextView tvToolbarTitle = (TextView) toolbar.findViewById(R.id.tvToolbarTitle);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        }
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onBackPressed();
//            }
//        });
        //tvToolbarTitle.setText("Feedback Question");

        userId = SharedPrefsGetSet.getUserId(mContext);

        GlobalFunctions.createVerticalRecyclerView(mContext, rvOptions);

//        questions = db.getQuestion();
//        totalQuestions = questions.size();
//        if(questions.size() > 0) {
//            setupQuestion(position);
//        } else {
//            SharedPrefsGetSet.removeUserId(mCo91  `V5393ntext);
//            GlobalFunctions.toastShort(mContext, "Add Questions First");
//            Intent i = new Intent(mContext, UserDetailsActivity.class);
//            startActivity(i);
//            finish();
//        }

        tvSubmit.setOnClickListener(this);
    }

    private void getQuestion() {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("uid",7);

        }catch (Exception e){
            e.printStackTrace();
        }
        RequestBody bodyRequest = RequestBody.create(MediaType.parse(jsons),jsonObject.toString());
        Call<GetQuestionResponse> call = APIClient.getInterface().getQuestions(bodyRequest);
        call.enqueue(new Callback<GetQuestionResponse>() {
            @Override
            public void onResponse(Call<GetQuestionResponse> call, Response<GetQuestionResponse> response) {
                if (response.isSuccessful()){
                    GetQuestionResponse getQuestionResponse = response.body();

                    List<Question> myques = getQuestionResponse.getQuestions();

                    if(myques.size()>0){

                        for(int i=0; i<myques.size();i++){
                            List<String> options= new ArrayList<>();
                            String[] parts = myques.get(i).getOpI().split(":");
                            for(int j=0;j<parts.length;j++){
                                options.add(parts[j]);
                            }
                            QuestionData qd = new QuestionData(Integer.parseInt(myques.get(i).getId()),myques.get(i).getQuestion(),options);
                            questions.add(qd);
                        }

                        totalQuestions = questions.size();
                        setupQuestion(position);

                    }else{
                        SharedPrefsGetSet.removeUserId(mContext);
                        GlobalFunctions.toastShort(mContext, "Add Questions First");
                        Intent i = new Intent(mContext, UserDetailsActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<GetQuestionResponse> call, Throwable t) {

            }
        });

    }

    private void setupQuestion(int position) {
        tvQuestion.setText(questions.get(position).getQuestion());
        options = createOptionsData(questions.get(position).getOptions());
        adapter = new OptionsAdapter(mContext, options);
        rvOptions.setAdapter(adapter);
        Log.e(TAG, questions.get(position).getOptions().toString() + " ");
    }

    private List<OptionsData> createOptionsData(List<String> options){
        List<OptionsData> optionsDatas = new ArrayList<>();
        for(String option: options) {
            OptionsData data = new OptionsData(option, false);
            optionsDatas.add(data);
        }
        return optionsDatas;
    }

    private boolean validateOptions(){
        for(OptionsData data: options) {
            if(data.isSelected()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvSubmit:
                if(validateOptions()) {
                    isSubmitted = true;
                    db.addUserResponse(SharedPrefsGetSet.getUserId(mContext), tvQuestion.getText().toString(), SharedPrefsGetSet.getAnswer(mContext));
                    GlobalFunctions.toastShort(mContext, "Response submitted");
                    SharedPrefsGetSet.removeAnswer(mContext);
                    if((position+1) < totalQuestions) {
                        isSubmitted = false;
                        position = position + 1;
                        setupQuestion(position);
                    } else {
                        SharedPrefsGetSet.removeUserId(mContext);
                        //Go to thank you page
                        Intent i = new Intent(mContext, ThankyouActivity.class);
                        startActivity(i);
                        finish();
                    }
                } else {
                    GlobalFunctions.toastShort(mContext, "Select your choice");
                }
                break;

        }
    }
}
