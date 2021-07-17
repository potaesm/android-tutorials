package com.suthinan.sharedpreferencesrxdagger.service;

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

    public void destroy() {
        this.sharedPreferences.unregisterOnSharedPreferenceChangeListener(this.listener);
    }

    public Completable saveData(String key, String data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().putString(key, data).apply()));
    }

    public Completable saveData(String key, Boolean data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().putBoolean(key, data).apply()));
    }

    public Completable saveData(String key, Float data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().putFloat(key, data).apply()));
    }

    public Completable saveData(String key, Integer data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().putInt(key, data).apply()));
    }

    public Completable saveData(String key, Long data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().putLong(key, data).apply()));
    }

    public Completable saveData(String key, HashSet<String> data) {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().putStringSet(key, data).apply()));
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
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().remove(key).apply()));
    }

    public Completable clearData() {
        return this.prefSubject.firstOrError().flatMapCompletable((prefs) -> Completable.fromAction(() -> prefs.edit().clear().apply()));
    }
}
