package com.suthinan.fcmnotifications.view;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.suthinan.fcmnotifications.R;
import com.suthinan.fcmnotifications.model.User;
import com.suthinan.fcmnotifications.service.MyFirebaseMessagingService;
import com.suthinan.fcmnotifications.service.RetrofitInstance;
import com.suthinan.fcmnotifications.service.UserService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private MyFirebaseMessagingService myFirebaseMessagingService;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private String currentToken = "";
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = findViewById(R.id.txt);

        this.userService = RetrofitInstance.getUserService();
        this.myFirebaseMessagingService = new MyFirebaseMessagingService();
        compositeDisposable.add(this.myFirebaseMessagingService.retrieveCurrentToken()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String token) {
                        currentToken = token;
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        sendRegistrationToServer(currentToken, "potae", "1234");
                        Log.i("GetToken", "Complete");
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void sendRegistrationToServer(String token, String username, String password) {
        User loginUser = new User();
        loginUser.setToken(token);
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        compositeDisposable.add(this.userService.login(loginUser)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(@NonNull User user) {
                        Log.i("Login", user.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Log.i("Login", "Complete");
                    }
                }));
    }

    public void showToken(View view) {
        mTextView.setText(this.currentToken);
        Log.i("ShowToken", "Complete");
    }

    public void subscribe(View view) {
        this.myFirebaseMessagingService.subscribeToTopic("news");
        mTextView.setText(R.string.subscribed);
    }

    public void unsubscribe(View view) {
        this.myFirebaseMessagingService.unsubscribeFromTopic("news");
        mTextView.setText(R.string.unsubscribed);
    }
}