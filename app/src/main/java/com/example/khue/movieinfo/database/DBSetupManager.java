package com.example.khue.movieinfo.database;

/**
 * Class to set up SQLite database
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.khue.movieinfo.utils.Const;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DBSetupManager {
	private static final String DB_NAME = "movieinfo.sqlite";
	private static String DB_PATH = null;

	public static boolean databaseExists(Context context){
		DB_PATH = context.getFilesDir().getPath() + File.separator + DB_NAME;
	    try{
            SQLiteDatabase database= SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READONLY);
			Log.e(Const.TAG_APP, "DB check exists. Able to open. So exists");
            database.close();
	    	return true;
	    }catch(Exception e){
			Log.e(Const.TAG_APP, "DB check exists. Crash. Can't open" + e.getMessage());
			e.printStackTrace();
	    	return false;
	    }
	}
	public static boolean setup(Context context){
		if(!databaseExists(context)){
			Log.e(Const.TAG_APP, "DB doesn't exist. Move.");
			return moveDatabase(context);
		}
		else{
			Log.e(Const.TAG_APP, "DB exists. Returning.");
			return false;
		}
	}

	public static boolean clear(Context context){
		if(databaseExists(context)){
			return deleteDatabase(context);
		}
		else
			return true;
	}

	private static boolean deleteDatabase(Context context) {
		File fdelete = new File(DB_PATH);
	    if (fdelete.exists()) {
	        if (fdelete.delete())
	        	return true;
        	else
        		return false;
	    }
		return false;
	}

	public static boolean moveDatabase(Context context){
		try {
			InputStream myInput = context.getAssets().open(DB_NAME);
			String outFileName = DB_PATH;
			OutputStream myOutput = new FileOutputStream(outFileName);
		    byte[] buffer = new byte[1024];
		    int length;
		    while ((length = myInput.read(buffer))>0){
		    	myOutput.write(buffer, 0, length);
		    }
		    myOutput.flush();
		    myOutput.close();
		    myInput.close();

			Log.e(Const.TAG_APP, "Move Successful");
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
