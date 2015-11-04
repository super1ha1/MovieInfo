package com.example.khue.movieinfo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class Utils {

    public static void showToast(Context context, String message){
        Toast.makeText(context, message,Toast.LENGTH_SHORT).show();
    }

    public static String getCompleteImageURL(String posterPath){
        return Const.BASE_IMAGE_URL +
                posterPath + Const.API_KEY;
    }

    public static String getYoutubeVideoURL(String videoId){
        return Const.BASE_YOUTUBE_URL +
                videoId + Const.BASE_YOUTUBE_IMAGE;
    }

    public static void writeToFile(Context context, String data, String fileName) {
        try {
            File file = new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_PICTURES), "mysdfile.txt");
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(data.getBytes());
            outputStream.close();
            Log.e(Const.TAG_APP, "Write to file success: ");
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }


    public static String readFromFile(Context context) {

        String ret = "";

        try {
            InputStream inputStream = context.openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }
    public static String saveBitmapToFile(String fileName, Bitmap bitmap){
        OutputStream output;
        File file;

        // Find the SD Card path
        File filepath = Environment.getExternalStorageDirectory();

        // Create a new folder in SD Card
        File dir = new File(filepath.getAbsolutePath()
                + Const.APP_FOLDER);
        dir.mkdirs();

        // Create a name for the saved image
        file = new File(dir, fileName + ".jpg");

        try {
            output = new FileOutputStream(file);
            // Compress into jpg format image from 0% - 100%
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, output);
            output.flush();
            output.close();
        }

        catch (Exception e) {
            e.printStackTrace();
        }
        return file.getAbsolutePath();
    }
}
