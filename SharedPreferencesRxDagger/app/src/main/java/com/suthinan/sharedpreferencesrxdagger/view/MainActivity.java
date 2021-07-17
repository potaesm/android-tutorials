package com.suthinan.sharedpreferencesrxdagger.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.suthinan.sharedpreferencesrxdagger.R;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.suthinan.sharedpreferencesrxdagger.service.DaggerSharedPreferencesComponent;
import com.suthinan.sharedpreferencesrxdagger.service.SharedPreferencesRepository;
import com.suthinan.sharedpreferencesrxdagger.service.SharedPreferencesComponent;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    private TextView textViewData;
    private EditText editTextData;
    private Button buttonSubmit, buttonClear;

    @Inject SharedPreferencesRepository sharedPreferencesRepository;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewData = findViewById(R.id.text_view_data);
        editTextData = findViewById(R.id.edit_text_data);
        buttonSubmit = findViewById(R.id.button_submit);
        buttonClear = findViewById(R.id.button_clear);

        SharedPreferencesComponent sharedPreferencesComponent = DaggerSharedPreferencesComponent
                .builder()
                .context(this)
                .preferenceName(getString(R.string.app_name))
                .build();
        sharedPreferencesComponent.inject(this);

        handleData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
        sharedPreferencesRepository.destroy();
    }

    private void handleData() {
        compositeDisposable.add(sharedPreferencesRepository.getStringData(getString(R.string.data_prefs))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((data) -> textViewData.setText(data)));

        buttonSubmit.setOnClickListener(view -> {
            String name = editTextData.getText().toString();
            compositeDisposable.add(sharedPreferencesRepository.saveData(getString(R.string.data_prefs), name)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        });

        buttonClear.setOnClickListener(view -> {
            editTextData.setText("");
            compositeDisposable.add(sharedPreferencesRepository.clearData(getString(R.string.data_prefs))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe());
        });
    }
}