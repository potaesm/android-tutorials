package com.suthinan.roomcrud.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.suthinan.roomcrud.db.entity.Contact;

@Database(entities = {Contact.class},version = 1)
public abstract class ContactsAppDatabase extends RoomDatabase {
    public abstract ContactDAO getContactDAO();
}
