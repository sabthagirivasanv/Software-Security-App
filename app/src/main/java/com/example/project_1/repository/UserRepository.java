package com.example.project_1.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.project_1.dao.UserDao;
import com.example.project_1.db.UserDataBase;
import com.example.project_1.entity.User;

import java.util.List;

public class UserRepository {

    // below line is the create a variable
    // for dao and list for all courses.
    private UserDao userDao;
    private LiveData<List<User>> allUsers;

    // creating a constructor for our variables
    // and passing the variables to it.
    public UserRepository(Application application) {
        UserDataBase database = UserDataBase.getInstance(application);
        userDao = database.userDao();
        allUsers = userDao.getAllUsers();
    }

    // creating a method to insert the data to our database.
    public void insert(User model) {
        new InsertUserAsyncTask(userDao).execute(model);
    }

    // creating a method to update data in database.
    public void update(User model) {
        new UpdateUserAsyncTask(userDao).execute(model);
    }

    // creating a method to delete the data in our database.
    public void delete(User model) {
        new DeleteCourseAsyncTask(userDao).execute(model);
    }

    // below is the method to delete all the courses.
    public void deleteAllCourses() {
        new DeleteAllCoursesAsyncTask(userDao).execute();
    }

    // below method is to read all the courses.
    public LiveData<List<User>> getAllUsers() {
        return allUsers;
    }

    // we are creating a async task method to insert new course.
    private static class InsertUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao dao;

        private InsertUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... model) {
            // below line is use to insert our modal in dao.
            dao.insert(model[0]);
            return null;
        }
    }

    // we are creating a async task method to update our course.
    private static class UpdateUserAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao dao;

        private UpdateUserAsyncTask(UserDao dao) {
            this.dao = dao;
        }

        @Override
        protected Void doInBackground(User... models) {
            // below line is use to update
            // our modal in dao.
            dao.update(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete course.
    private static class DeleteCourseAsyncTask extends AsyncTask<User, Void, Void> {
        private UserDao userDao;

        private DeleteCourseAsyncTask(UserDao dao) {
            this.userDao = dao;
        }

        @Override
        protected Void doInBackground(User... models) {
            // below line is use to delete
            // our course modal in dao.
            userDao.delete(models[0]);
            return null;
        }
    }

    // we are creating a async task method to delete all courses.
    private static class DeleteAllCoursesAsyncTask extends AsyncTask<Void, Void, Void> {
        private UserDao dao;
        private DeleteAllCoursesAsyncTask(UserDao dao) {
            this.dao = dao;
        }
        @Override
        protected Void doInBackground(Void... voids) {
            // on below line calling method
            // to delete all courses.
            dao.deleteAllUsers();
            return null;
        }
    }
}

