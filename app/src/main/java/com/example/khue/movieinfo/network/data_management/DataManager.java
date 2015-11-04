package com.example.khue.movieinfo.network.data_management;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.example.khue.movieinfo.R;
import com.example.khue.movieinfo.database.DB;
import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.model.MovieList;
import com.example.khue.movieinfo.model.RelatedMovieList;
import com.example.khue.movieinfo.model.VideoList;
import com.example.khue.movieinfo.network.callbacks.DataOperationCallBack;
import com.example.khue.movieinfo.network.callbacks.WebServiceCallBack;
import com.example.khue.movieinfo.network.retrofit.WebServices;
import com.example.khue.movieinfo.utils.Const;
import com.example.khue.movieinfo.utils.NetworkUtil;
import com.example.khue.movieinfo.utils.Utils;

import org.json.JSONObject;

import java.util.List;

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
                Log.d(Const.TAG_APP, "Get MovieList fail");
                dataCallBack.failure();
            }

            @Override
            public void success(Object data) {

                handleCallBackOnSuccess(data, dataCallBack);
            }

            private void handleCallBackOnSuccess(Object data, DataOperationCallBack dataCallBack) {
                try {
                    Log.e(Const.TAG_APP, "Get MovieList  success");
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
                    Log.e(Const.TAG_APP, "MovieList Exception : " + e.getMessage());
                    e.printStackTrace();
                }
            }

        };

        WebServices.getNowPlayingMovieList(context, Const.API_KEY, page, getMovieListCallBack);
    }

    public void getRelatedMovie(final Context context, final String movieId, final int page,
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
                Log.d(Const.TAG_APP, "Get MovieList fail");
                dataCallBack.failure();
            }

            @Override
            public void success(Object data) {
                handleCallBackOnSuccess( data, dataCallBack);
            }

            private void handleCallBackOnSuccess(Object data, DataOperationCallBack dataCallBack) {
                try {
                    Log.e(Const.TAG_APP, "Get MovieList success");
                    RelatedMovieList movieList = (RelatedMovieList) data;
                    if( DataHolder.getInstance().getRelatedMovieMap().keySet().contains(movieId)){
                        if (page == 1){
                            //Clear the current list before add a new batch of movies
                            (DataHolder.getInstance().getRelatedMovieMap().get(movieId))
                                    .getMovies().clear();
                        }
                        (DataHolder.getInstance().getRelatedMovieMap().get(movieId))
                                    .getMovies().addAll(movieList.getMovies());

                    }else {
                        DataHolder.getInstance().getRelatedMovieMap().put(movieId, movieList);
                    }
                    for (int i = 0 ; i < movieList.getMovies().size() ; i ++){
                        Movie movie = movieList.getMovies().get(i);
                        if( movie.getPosterPath() == null){
                            Log.d(Const.TAG_APP," No poster: " + i);
                        }
                        if( movie.getBackdropPath() == null){
                            Log.d(Const.TAG_APP," No backdrop: " + i);
                        }
                    }
                    dataCallBack.success();
                } catch (Exception e) {
                    Log.e(Const.TAG_APP, "MovieList Exception : " + e.getMessage());
                    e.printStackTrace();
                }
            }

        };
        WebServices.getRelatedMovie(context, movieId, Const.API_KEY, page, getMovieListCallBack);

    }

    public void getMovieVideo(final Context context, final String movieId,
                                final DataOperationCallBack dataCallBack){

        if( movieId == null || TextUtils.isEmpty(movieId)){
            return;
        }

        if(NetworkUtil.getConnectivityStatus(context) == NetworkUtil.TYPE_NOT_CONNECTED) {
            Utils.showToast(context, context.getString(R.string.no_internet));
            return;
        }


        WebServiceCallBack getMovieVideoCallBack = new WebServiceCallBack(){
            @Override
            public void failure(JSONObject data) {
                Log.d(Const.TAG_APP, "Get MovieList fail");
                dataCallBack.failure();
            }

            @Override
            public void success(Object data) {
                handleCallBackOnSuccess( data, dataCallBack);
            }

            private void handleCallBackOnSuccess(Object data, DataOperationCallBack dataCallBack) {
                try {
                    Log.e(Const.TAG_APP, "Get MovieList success");
                    VideoList movieList = (VideoList) data;
                    if( DataHolder.getInstance().getMovieVideoMap().keySet().contains(movieId)){
                        //Clear before add the next batch of videos
                        (DataHolder.getInstance().getMovieVideoMap().get(movieId))
                                .getMovieVideos().clear();

                        (DataHolder.getInstance().getMovieVideoMap().get(movieId))
                                .getMovieVideos().addAll(movieList.getMovieVideos());
                    }else {
                        DataHolder.getInstance().getMovieVideoMap().put(movieId, movieList);
                    }
                    dataCallBack.success();
                } catch (Exception e) {
                    Log.e(Const.TAG_APP, "MovieList Exception : " + e.getMessage());
                    e.printStackTrace();
                }
            }

        };
        WebServices.getVideoList(context, movieId, Const.API_KEY,  getMovieVideoCallBack);

    }


    public void getFavoriteMovie(Context context,
                             final DataOperationCallBack dataCallBack){
        try {
            List<Movie> movieList = DB.getMovies(context);
            if( movieList != null){
                DataHolder.getInstance().setMovieListFromDatabase(movieList);
                Log.d(Const.TAG_APP, "Favorite movie list size: " + DataHolder.getInstance().getMovieListFromDatabase().size());
            }
            dataCallBack.success();
        } catch (Exception e) {
            e.printStackTrace();
            dataCallBack.failure();
        }
    }



}
