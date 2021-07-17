package com.suthinan.retrofitrxdagger.view;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.suthinan.retrofitrxdagger.R;
import com.suthinan.retrofitrxdagger.adapter.UsersAdapter;
import com.suthinan.retrofitrxdagger.service.DaggerRetrofitComponent;
import com.suthinan.retrofitrxdagger.service.RetrofitComponent;
import com.suthinan.retrofitrxdagger.model.Jwt;
import com.suthinan.retrofitrxdagger.model.User;
import com.suthinan.retrofitrxdagger.service.UserService;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final ArrayList<User> userArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;
    private Completable completable;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Inject UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view_users);
        swipeContainer = findViewById(R.id.swipe_layout);
        swipeContainer.setColorSchemeResources(R.color.design_default_color_primary);
        swipeContainer.setOnRefreshListener(this::getUsers);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> addAndEditUser(false, null));

        RetrofitComponent retrofitComponent = DaggerRetrofitComponent
                .builder()
                .context(this)
                .baseUrl(getString(R.string.default_base_url))
                .build();
        retrofitComponent.inject(this);

        initView();

        login();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void login() {
        User defaultUser = new User();
        defaultUser.setUsername("potae");
        defaultUser.setPassword("12345");
        Observable<Jwt> loginResponseObservable = userService.login(defaultUser);
        compositeDisposable.add(loginResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<Jwt>() {
                    @Override
                    public void onNext(@NonNull Jwt jwt) { getUsers(); }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        System.out.println("LOGGED IN");
                    }
                }));
    }

    public void getUsers() {
        swipeContainer.setRefreshing(true);
        Observable<ArrayList<User>> usersResponseObservable = userService.getUsers();
        compositeDisposable.add(usersResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<User>>() {
                    @Override
                    public void onNext(@NonNull ArrayList<User> users) {
                        userArrayList.clear();
                        userArrayList.addAll(users);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        swipeContainer.setRefreshing(false);
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        swipeContainer.setRefreshing(false);
                        initView(); }
                }));
    }



    private void createUser(User user) {
        Observable<User> userResponseObservable = userService.postUser(user);
        compositeDisposable.add(userResponseObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<User>() {
                    @Override
                    public void onNext(@NonNull User user) { }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        getUsers();
                    }
                }));
        Toast.makeText(MainActivity.this, getString(R.string.new_user) + ": " + user.getUsername(), Toast.LENGTH_SHORT).show();
    }

    private void updateUser(String id, User user) {
        completable = userService.patchUserById(id, user);
        compositeDisposable.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getUsers, e -> Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show()));
        Toast.makeText(MainActivity.this, getString(R.string.update_user) + ": " + user.getUsername(), Toast.LENGTH_SHORT).show();
    }

    private void deleteUser(String id) {
        completable = userService.deleteUserById(id);
        compositeDisposable.add(completable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::getUsers, e -> Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show()));
        Toast.makeText(MainActivity.this, getString(R.string.delete_user), Toast.LENGTH_SHORT).show();
    }

    public void initView() {
        UsersAdapter usersAdapter = new UsersAdapter(userArrayList, MainActivity.this);
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
                            if (isUpdate && user != null) {
                                deleteUser(user.getId());
                            } else {
                                dialogBox.cancel();
                            }
                        });

        final AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_rounded_background);
        alertDialog.show();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(v -> {
            if (TextUtils.isEmpty(newUsername.getText().toString()) || TextUtils.isEmpty(newPermissionLevel.getText().toString()) || (!isUpdate && TextUtils.isEmpty(newPassword.getText().toString()))) {
                Toast.makeText(getBaseContext(), "Please enter all mandatory data", Toast.LENGTH_SHORT).show();
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
                updateUser(user.getId(), newUser);
            } else {
                createUser(newUser);
            }
        });
    }
}