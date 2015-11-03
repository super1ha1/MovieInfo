package com.example.khue.movieinfo.network.data_management;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.khue.movieinfo.R;
import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.model.MovieList;
import com.example.khue.movieinfo.network.callbacks.DataOperationCallBack;
import com.example.khue.movieinfo.network.callbacks.WebServiceCallBack;
import com.example.khue.movieinfo.network.retrofit.WebServices;
import com.example.khue.movieinfo.utils.Const;
import com.example.khue.movieinfo.utils.NetworkUtil;
import com.example.khue.movieinfo.utils.Utils;
import com.google.gson.Gson;

import org.json.JSONObject;

import javax.inject.Singleton;
/**
 * DataManager class to handle all API request
 */

/**
 * Singleton Class to handle all  retrieve and update data request
 */
@Singleton
public class DataManager {
    private  Context context;
    private static DataManager instance ;

    private DataManager(Context c){
        context = c;
    }
    public static DataManager getInstance(Context context) {
        if (null == instance) {
            instance = new DataManager(context);
        }
        return instance;
    }


    public void getMovieList(Context context, final int page,
                             final DataOperationCallBack dataCallBack){

        if(0 >= page) {
            return;
        }

        if(NetworkUtil.getConnectivityStatus(context) == NetworkUtil.TYPE_NOT_CONNECTED) {
            Utils.showToast(context, context.getString(R.string.no_internet));
            return;
        }


        WebServiceCallBack getMovieListCallBack = new WebServiceCallBack(){
            @Override
            public void failure(JSONObject data) {
                Log.d(Const.TAG_APP, "Login fail, update data failure");
                dataCallBack.failure();
            }

            @Override
            public void success(Object data) {

                handleCallBackOnSuccess(data, dataCallBack);
            }

            private void handleCallBackOnSuccess(Object data, DataOperationCallBack dataCallBack) {
                try {
                    Log.e(Const.TAG_APP, "Get MovieList Info success");
//                    Gson gson = new Gson();
//                    MovieList movieList =  gson.fromJson(data.toString(), MovieList.class);
                      MovieList movieList = (MovieList) data;
//                    JSONArray movieArray = data.getJSONArray("results");
//                    for( int i = 0 ; i < movieArray.length(); i ++){
//                        JSONObject movieObject = movieArray.getJSONObject(i);
//                        if( movieObject.has("poster_path")){
//                            String poster = movieObject.getString("poster_path");
//                            movieList.getMovies().get(i).setPosterPath(poster);
//                        }else {
//                            Log.d(Const.TAG_APP," No poster: " + i);
//                        }
//                        if( movieObject.has("backdrop_path")){
//                            String backdrop = movieObject.getString("backdrop_path");
//                            movieList.getMovies().get(i).setBackdropPath(backdrop);
//                        }else {
//                            Log.d(Const.TAG_APP," No backdrop: " + i);
//                        }
//                    }

                    DataHolder.getInstance().setNowShowingMovieList(movieList);
                    DataHolder.getInstance().getMovieListFromAPI().addAll(movieList.getMovies());
                    for (int i = 0 ; i < movieList.getMovies().size() ; i ++){
                        Movie movie = movieList.getMovies().get(i);
//                        System.out.println("id: "  + movie.getId() + "ImagePoster url: " + movie.getPosterPath()
//                                + "\n" + " complete path: " + Utils.getCompleteImageURL(movie.getPosterPath()
//                         + "\n"));
                        if( movie.getPosterPath() == null){
                            Log.d(Const.TAG_APP," No poster: " + i);
                        }
                        if( movie.getBackdropPath() == null){
                            Log.d(Const.TAG_APP," No backdrop: " + i);
                        }

                    }
                    dataCallBack.success();

                } catch (Exception e) {
                    Log.e(Const.TAG_APP, "MovieList JSONException : " + e.getMessage());
                    e.printStackTrace();
                }
            }

        };

        WebServices.getNowPlayingMovieList(context, Const.API_KEY,  page, getMovieListCallBack);
    }

    public void getRelatedMovie(final String movieId, final int page,
                             final DataOperationCallBack dataCallBack){

        if(0 >= page ) {
            return;
        }

        if( movieId == null || TextUtils.isEmpty(movieId)){
            return;
        }

        if(NetworkUtil.getConnectivityStatus(context) == NetworkUtil.TYPE_NOT_CONNECTED) {
            Utils.showToast(context, context.getString(R.string.no_internet));
            return;
        }


        WebServiceCallBack getMovieListCallBack = new WebServiceCallBack(){
            @Override
            public void failure(JSONObject data) {
                Log.d(Const.TAG_APP, "Login fail, update data failure");
                dataCallBack.failure();
            }

            @Override
            public void success(JSONObject data) {
                handleCallBackOnSuccess( data, dataCallBack);
            }

            private void handleCallBackOnSuccess(JSONObject data, DataOperationCallBack dataCallBack) {
                try {
                    Log.e(Const.TAG_APP, "Get MovieList Info success");
                    Gson gson = new Gson();

                    dataCallBack.success();
                } catch (Exception e) {
                    Log.e(Const.TAG_APP, "MovieList JSONException : " + e.getMessage());
                    e.printStackTrace();
                }
            }

        };
//        WebServices.getNowPlayingMovieList(context, movieId, Const.API_KEY, page, getMovieListCallBack);

    }

}
