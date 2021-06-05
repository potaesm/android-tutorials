package com.suthinan.retrofitget.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.suthinan.retrofitget.R;
import com.suthinan.retrofitget.adapter.CountryAdapter;
import com.suthinan.retrofitget.model.Info;
import com.suthinan.retrofitget.model.Result;
import com.suthinan.retrofitget.service.GetCountryDataService;
import com.suthinan.retrofitget.service.RetrofitInstance;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Result> results;
    private CountryAdapter countryAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_countries_list);

        getCountries();
    }

    public Object getCountries() {
        GetCountryDataService getCountryDataService = RetrofitInstance.getService();
        Call<Info> call = getCountryDataService.getResults();
        call.enqueue(new Callback<Info>() {

            @Override
            public void onResponse(Call<Info> call, Response<Info> response) {
                Log.i("Debug", "Response");
                Info info = response.body();
                if (info != null && info.getRestResponse() != null) {
                    results = (ArrayList<Result>) info.getRestResponse().getResult();
//                    for (Result r : results) {
//                        Log.i("testing123", "*********************************" + r.getName());
//                    }
                     viewData();
                }
            }

            @Override
            public void onFailure(Call<Info> call, Throwable t) {
                Log.i("Failure", t.toString());
            }
        });
        return results;
    }

    private void viewData() {
        countryAdapter = new CountryAdapter(results);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(countryAdapter);
    }
}