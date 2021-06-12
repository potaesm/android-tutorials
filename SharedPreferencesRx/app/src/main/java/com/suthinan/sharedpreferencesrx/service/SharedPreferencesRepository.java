package com.suthinan.sharedpreferencesrx.service;

import android.content.Context;
import android.content.SharedPreferences;
import java.util.HashSet;
import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class SharedPreferencesRepository {
    private final BehaviorSubject<SharedPreferences> prefSubject;
    private final SharedPreferences sharedPreferences;
    private final SharedPreferences.OnSharedPreferenceChangeListener listener;

    public SharedPreferencesRepository(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
        this.prefSubject = BehaviorSubject.createDefault(sharedPreferences);
        this.listener = (prefs, key) -> this.prefSubject.onNext(prefs);
        this.sharedPreferences.registerOnSharedPreferenceChangeListener(this.listener);
    }

    public static SharedPreferencesRepository createInstance(Context context, String preferenceName) {
        return new SharedPreferencesRepository(context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE));
    }

    public void destroyInstance() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this.listener);
    }

    public Completable saveStringData(String key, String data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(key, data);
            editor.apply();
        }));
    }

    public Completable saveBooleanData(String key, Boolean data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean(key, data);
            editor.apply();
        }));
    }

    public Completable saveFloatData(String key, Float data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat(key, data);
            editor.apply();
        }));
    }

    public Completable saveIntData(String key, Integer data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putInt(key, data);
            editor.apply();
        }));
    }

    public Completable saveLongData(String key, Long data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putLong(key, data);
            editor.apply();
        }));
    }

    public Completable saveStringSetData(String key, HashSet<String> data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putStringSet(key, data);
            editor.apply();
        }));
    }

    public Observable<String> getStringData(String key) {
        return this.prefSubject.map((prefs) -> prefs.getString(key, ""));
    }

    public Observable<Boolean> getBooleanData(String key) {
        return this.prefSubject.map((prefs) -> prefs.getBoolean(key, false));
    }

    public Observable<Float> getFloatData(String key) {
        return this.prefSubject.map((prefs) -> prefs.getFloat(key, 0f));
    }

    public Observable<Integer> getIntData(String key) {
        return this.prefSubject.map((prefs) -> prefs.getInt(key, 0));
    }

    public Observable<Long> getLongData(String key) {
        return this.prefSubject.map((prefs) -> prefs.getLong(key, 0L));
    }

    public Observable<HashSet<String>> getStringSetData(String key) {
        return this.prefSubject.map((prefs) -> (HashSet<String>) prefs.getStringSet(key, new HashSet<>()));
    }

    public Completable clearData(String key) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.remove(key);
            editor.apply();
        }));
    }
}
