package com.suthinan.roomrx.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.suthinan.roomrx.db.entity.User;

@Database(entities = {User.class}, version = 1, exportSchema = false)
public abstract class UserDatabase extends RoomDatabase {

    public abstract UserDAO getUserDAO();

}
