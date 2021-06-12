package com.suthinan.sharedpreferencesrx.service;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class SharedPreferencesRepository {
    private final BehaviorSubject<SharedPreferences> prefSubject;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.OnSharedPreferenceChangeListener listener;

    public static SharedPreferencesRepository createInstance(Context context, String preferenceName) {
        return new SharedPreferencesRepository(context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE));
    }
    public SharedPreferencesRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.prefSubject = BehaviorSubject.createDefault(sharedPreferences);
        this.listener = (prefs, key) -> this.prefSubject.onNext(prefs);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.listener);
    }

    public Completable saveData(String key, String data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, data);
            editor.apply();
        }));
    }

    public Observable<String> getData(String key) {
        return this.prefSubject.map((prefs) -> prefs.getString(key, ""));
    }

    public Completable clearData(String key) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.remove(key);
            editor.apply();
        }));
    }

    public void destroyInstance() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this.listener);
    }
}
