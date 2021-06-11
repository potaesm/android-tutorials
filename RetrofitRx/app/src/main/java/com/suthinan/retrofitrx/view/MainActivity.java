package com.suthinan.retrofitrx.view;

import android.annotation.SuppressLint;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.suthinan.retrofitrx.R;
import com.suthinan.retrofitrx.adapter.UsersAdapter;
import com.suthinan.retrofitrx.model.Doc;
import com.suthinan.retrofitrx.model.Jwt;
import com.suthinan.retrofitrx.model.User;
import com.suthinan.retrofitrx.service.RetrofitInstance;
import com.suthinan.retrofitrx.service.UserService;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private UsersAdapter usersAdapter;
    private RecyclerView recyclerView;
    private ArrayList<User> userArrayList = new ArrayList<>();
    private Observable<Jwt> loginResponseObservable;
    private Observable<ArrayList<User>> usersResponseObservable;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_users);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addAndEditUser(false, null));

        login();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void login() {
        UserService userService = RetrofitInstance.getService(getBaseContext());
        User defaultUser = new User();
        defaultUser.setUsername("potae");
        defaultUser.setPassword("12345");
        loginResponseObservable = userService.login(defaultUser);
        compositeDisposable.add(loginResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Jwt>() {
                    @Override
                    public void onNext(Jwt jwt) {
                        System.out.println("NEXT");
                        System.out.println(jwt.getAccessToken());
                        getUsers(userService);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("LOGGED IN");
                    }
                }));
    }

    public void getUsers(UserService userService) {
        userArrayList = new ArrayList<>();
        usersResponseObservable = userService.getUsers();

        compositeDisposable.add(usersResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<User>>() {
                    @Override
                    public void onNext(ArrayList<User> users) {
                        System.out.println(users);
                        userArrayList.addAll(users);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        init();
                        System.out.println("COMPLETE");
                    }
                }));
    }

    public void init() {
        usersAdapter = new UsersAdapter(userArrayList, MainActivity.this);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(usersAdapter);
        usersAdapter.notifyDataSetChanged();
    }

    @SuppressLint("SetTextI18n")
    public void addAndEditUser(boolean isUpdate, User user) {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getApplicationContext());
        View view = layoutInflaterAndroid.inflate(R.layout.add_edit_user, null);

        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(MainActivity.this);
        alertDialogBuilderUserInput.setView(view);

        TextView title = view.findViewById(R.id.title);
        EditText newUsername = view.findViewById(R.id.username);
        EditText newPassword = view.findViewById(R.id.password);
        EditText newPermissionLevel = view.findViewById(R.id.permission_level);

        title.setText(getString(!isUpdate ? R.string.new_user : R.string.update_user));

        if (isUpdate && user != null) {
            newUsername.setText(user.getUsername());
            newPermissionLevel.setText(user.getPermissionLevel().toString());
        }

        alertDialogBuilderUserInput
                .setCancelable(false)
                .setPositiveButton(getString(isUpdate ? R.string.update : R.string.save), (dialogBox, id) -> {
                })
                .setNegativeButton(getString(isUpdate ? R.string.delete : R.string.cancel),
                        (dialogBox, id) -> {
                            if (isUpdate) {
                                this.deleteUser(user);
                            } else {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (TextUtils.isEmpty(newUsername.getText().toString()) || TextUtils.isEmpty(newPermissionLevel.getText().toString()) || (!isUpdate && TextUtils.isEmpty(newPassword.getText().toString()))) {
                Toast.makeText(MainActivity.this, "Please enter all mandatory data", Toast.LENGTH_SHORT).show();
                return;
            } else {
                alertDialog.dismiss();
            }
            User newUser = new User();
            newUser.setUsername(newUsername.getText().toString());
            if (!TextUtils.isEmpty(newUsername.getText().toString())) {
                newUser.setPassword(newPassword.getText().toString());
            }
            newUser.setPermissionLevel(Integer.parseInt(newPermissionLevel.getText().toString()));
            if (isUpdate && user != null) {
                updateUser(newUser);
            } else {
                createUser(newUser);
            }
        });
    }

    private void createUser(User user) {
        Toast.makeText(MainActivity.this, "CREATE USER " + user.getId(), Toast.LENGTH_SHORT).show();
    }

    private void updateUser(User user) {
        Toast.makeText(MainActivity.this, "UPDATE USER " + user.getId(), Toast.LENGTH_SHORT).show();
    }

    private void deleteUser(User user) {
        Toast.makeText(MainActivity.this, "DELETE USER " + user.getId(), Toast.LENGTH_SHORT).show();
    }
}