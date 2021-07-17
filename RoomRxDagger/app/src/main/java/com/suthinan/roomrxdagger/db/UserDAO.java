package com.suthinan.roomrxdagger.db;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.suthinan.roomrxdagger.db.entity.User;

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
