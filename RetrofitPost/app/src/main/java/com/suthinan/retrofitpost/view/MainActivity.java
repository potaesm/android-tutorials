package com.suthinan.retrofitpost.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.suthinan.retrofitpost.R;
import com.suthinan.retrofitpost.model.User;
import com.suthinan.retrofitpost.service.PostAppService;
import com.suthinan.retrofitpost.service.RetrofitInstance;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText userEmail;
    private EditText passWord;
    private Button submitButton;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userEmail = findViewById(R.id.et_email);
        passWord = findViewById(R.id.et_password);
        submitButton = findViewById(R.id.btn_submit);
        resultTextView = findViewById(R.id.tv_result);

        submitButton.setOnClickListener(view -> postData());
    }

    private void postData() {

        User user = new User();
        user.setUserEmail(userEmail.getText().toString());
        user.setPassWord(passWord.getText().toString());

        Log.i("responsetest", "******************* before id : " + user.getId());

        PostAppService postAppService = RetrofitInstance.getService();
        Call<User> call = postAppService.getResult(user);

        userEmail.setText("");
        passWord.setText("");

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                User returnedUser = response.body();
                resultTextView.setText("Id is : " + returnedUser.getId());
                Log.i("responsetest", "******************* after id : " + returnedUser.getId());
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
            }
        });
    }
}