package com.example.khue.movieinfo.database;

/**
 * DB hold all operation with Movie table
 */

import android.content.Context;

import com.example.khue.movieinfo.model.Movie;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.sql.SQLException;
import java.util.List;

public class DB {

    private static DBHelper dbHelper = null;

    private static void openDb(Context context) {
        if (dbHelper == null)
            dbHelper = OpenHelperManager.getHelper(context, DBHelper.class);
    }



    private static void closeDb() {
//        return;
		if(dbHelper.isOpen())
		{
			dbHelper.close();
			dbHelper = null;
		}

    }

    public static List<Movie> getMovies(Context context) {
        List<Movie> res = null;
        try {
            openDb(context);
            RuntimeExceptionDao<Movie, Integer> Dao = dbHelper.getMovieDao();
            res = Dao.queryForAll();
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void addMovie(Context context, Movie data) {
        try {
            openDb(context);
            RuntimeExceptionDao<Movie, Integer> Dao = dbHelper.getMovieDao();
            Dao.create(data);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Movie getMovieById(Context context, int id) {
        Movie res = null;
        try {
            openDb(context);
            RuntimeExceptionDao<Movie, Integer> Dao = dbHelper.getMovieDao();
            res = Dao.queryForId(id);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static void deleteMovie(Context context, Movie data) {
        try {
            openDb(context);
            RuntimeExceptionDao<Movie, Integer> Dao = dbHelper.getMovieDao();
            Dao.delete(data);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateMovie(Context context, Movie data) {
        try {
            openDb(context);
            RuntimeExceptionDao<Movie, Integer> Dao = dbHelper.getMovieDao();
            Dao.update(data);
            closeDb();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}