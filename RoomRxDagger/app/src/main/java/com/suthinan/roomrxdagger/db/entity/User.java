package com.suthinan.roomrxdagger.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "users")
public class User {

    public User(long id, String username, String password, Integer permissionLevel) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.permissionLevel = permissionLevel;
    }

    @Ignore
    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    @ColumnInfo(name="user_id")
    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name="username")
    private String username;

    @ColumnInfo(name="password")
    private String password;

    @ColumnInfo(name="permission_level")
    private Integer permissionLevel;
}
