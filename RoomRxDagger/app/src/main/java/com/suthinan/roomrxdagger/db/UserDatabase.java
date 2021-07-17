package com.suthinan.roomrxdagger.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.suthinan.roomrxdagger.db.entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();

}
