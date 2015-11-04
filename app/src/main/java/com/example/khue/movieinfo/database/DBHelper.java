/**
 * DBHelper class to create and upgrade database
 */

package com.example.khue.movieinfo.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.utils.Const;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	private static final String DB_NAME = "movieinfo";
	private static final int DB_VERSION = 1;

	private RuntimeExceptionDao<Movie, Integer> MovieDao = null;

	public DBHelper(Context context) {
		super(context, DB_NAME ,null, DB_VERSION);
		Log.e(Const.TAG_APP, "DB Helper Constructor");
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		Log.e(Const.TAG_APP, "on Create DB Helper");
		try {

			TableUtils.createTableIfNotExists(arg1, Movie.class);

		} catch (SQLException e) {
			Log.e(Const.TAG_APP,"Error on create open helper : " + e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int oldVersion, int newVersion) {
		Log.e(Const.TAG_APP,"onUpgrade DB Helper");
		Log.e(Const.TAG_APP,"onUpgrade Old Version : " + oldVersion);
		Log.e(Const.TAG_APP,"onUpgrade New Version : " + newVersion);
		try {
			onCreate(arg0, arg1);
		} catch (Exception e) {
			Log.e(Const.TAG_APP,"onUpgrade DB Helper Exception" + e.getMessage());
			e.printStackTrace();
		}
	}

	public RuntimeExceptionDao<Movie, Integer> getMovieDao() throws SQLException{
		if(MovieDao == null)
			MovieDao = getRuntimeExceptionDao(Movie.class);
		return MovieDao;
	}


}

