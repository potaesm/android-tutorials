package com.suthinan.roomrx.view;

import android.annotation.SuppressLint;
import android.arch.persistence.room.Room;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
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

import com.suthinan.roomrx.R;
import com.suthinan.roomrx.adapter.UsersAdapter;
import com.suthinan.roomrx.db.UserDatabase;
import com.suthinan.roomrx.db.entity.User;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final ArrayList<User> userArrayList = new ArrayList<>();
    private SwipeRefreshLayout swipeContainer;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private UserDatabase userDatabase;

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

        userDatabase = Room.databaseBuilder(getApplicationContext(), UserDatabase.class, getString(R.string.app_name)).build();

        initView();

        getUsers();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }

    public void getUsers() {
        compositeDisposable.add(userDatabase.getUserDAO().getUsers()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> {
                            userArrayList.clear();
                            userArrayList.addAll(users);
                            swipeContainer.setRefreshing(false);
                            initView();
                        }, e -> {
                            swipeContainer.setRefreshing(false);
                            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                )
        );
    }

    private void createUser(User user) {
        compositeDisposable.add(Completable.fromAction(() -> userDatabase.getUserDAO().addUser(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, getString(R.string.new_user) + ": " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void updateUser(User user) {
        compositeDisposable.add(Completable.fromAction(() -> userDatabase.getUserDAO().updateUser(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, getString(R.string.update_user) + ": " + user.getUsername(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void deleteUser(User user) {
        compositeDisposable.add(Completable.fromAction(() -> userDatabase.getUserDAO().deleteUser(user))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Toast.makeText(MainActivity.this, getString(R.string.delete_user), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
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
                                deleteUser(user);
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
                newUser.setId(user.getId());
                if (TextUtils.isEmpty(newUsername.getText().toString())) {
                    newUser.setPassword(user.getPassword());
                }
                updateUser(newUser);
            } else {
                createUser(newUser);
            }
        });
    }
}