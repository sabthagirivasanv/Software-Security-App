package com.example.project_1.view;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.project_1.entity.User;
import com.example.project_1.repository.UserRepository;

import java.util.List;

public class ViewModal extends AndroidViewModel {

    // creating a new variable for course repository.
    private UserRepository repository;

    // below line is to create a variable for live
    // data where all the courses are present.
    private LiveData<List<User>> allUsers;

    // constructor for our view modal.
    public ViewModal(@NonNull Application application) {
        super(application);
        repository = new UserRepository(application);
        allUsers = repository.getAllUsers();
    }

    // below method is use to insert the data to our repository.
    public void insert(User model) {
        repository.insert(model);
    }

    // below line is to update data in our repository.
    public void update(User model) {
        repository.update(model);
    }

    // below line is to delete the data in our repository.
    public void delete(User model) {
        repository.delete(model);
    }

    // below method is to delete all the courses in our list.
    public void deleteAllCourses() {
        repository.deleteAllCourses();
    }

    // below method is to get all the courses in our list.
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }
}

