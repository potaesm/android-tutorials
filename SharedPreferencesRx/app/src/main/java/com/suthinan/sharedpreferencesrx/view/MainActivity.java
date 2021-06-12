package com.suthinan.sharedpreferencesrx.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.suthinan.sharedpreferencesrx.R;
import com.suthinan.sharedpreferencesrx.service.SharedPreferencesRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private TextView textViewName;
    private EditText editTextName;
    private Button buttonSubmit, buttonClear;

    private SharedPreferencesRepository sharedPreferencesRepository;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewName = findViewById(R.id.text_view_name);
        editTextName = findViewById(R.id.edit_text_name);
        buttonSubmit = findViewById(R.id.button_submit);
        buttonClear = findViewById(R.id.button_clear);

        sharedPreferencesRepository = SharedPreferencesRepository.createInstance(this, getString(R.string.app_name));
        handleData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        sharedPreferencesRepository.destroyInstance();
    }

    private void handleData() {
        compositeDisposable.add(sharedPreferencesRepository.getData("NAME")
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<String>() {
                    @Override
                    public void onNext(@NonNull String data) {
                        textViewName.setText(data);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(getBaseContext(), "COMPLETE", Toast.LENGTH_SHORT).show();
                    }
                }));
        buttonSubmit.setOnClickListener(view -> {
            String name = editTextName.getText().toString();
            compositeDisposable.add(sharedPreferencesRepository.saveData("NAME", name).subscribe());
        });
        buttonClear.setOnClickListener(view -> compositeDisposable.add(sharedPreferencesRepository.clearData("NAME").subscribe()));
    }
}