package com.example.project_1.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.project_1.entity.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE name LIKE :name AND " +
            "phone LIKE :phone LIMIT 1")
    User findByName(String name, String phone);

    // below method is use to
    // add data to database.
    @Insert
    void insert(User model);

    // below method is use to update
    // the data in our database.
    @Update
    void update(User model);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    // on below line we are making query to
    // delete all courses from our database.
    @Query("DELETE FROM user")
    void deleteAllUsers();

    // below line is to read all the courses from our database.
    // in this we are ordering our courses in ascending order
    // with our course name.
    @Query("SELECT * FROM user")
    LiveData<List<User>> getAllUsers();
}