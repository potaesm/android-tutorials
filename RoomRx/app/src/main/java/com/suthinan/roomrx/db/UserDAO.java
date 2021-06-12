package com.suthinan.roomrx.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.suthinan.roomrx.db.entity.User;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface UserDAO {
    @Insert
    void addUser(User user);

    @Query("select * from users")
    Flowable<List<User>> getUsers();

    @Update
    void updateUser(User user);

    @Delete
    void deleteUser(User user);
}
