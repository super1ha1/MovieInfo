package com.example.khue.movieinfo.network.retrofit;

import android.content.Context;
import android.util.Log;

import com.example.khue.movieinfo.model.MovieList;
import com.example.khue.movieinfo.model.RelatedMovieList;
import com.example.khue.movieinfo.model.VideoList;
import com.example.khue.movieinfo.network.callbacks.GeneralWebServiceCallback;
import com.example.khue.movieinfo.utils.Const;

import org.json.JSONException;
import org.json.JSONObject;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class WebServices {
    private static final String BASE_URL = "http://api.themoviedb.org/3";
    private static final String UPCOMING_END_POINT = "/movie/upcoming";

    public static void getNowPlayingMovieList(final Context context, String api_key,
                                              int page, final GeneralWebServiceCallback callback){
        try {
            RestClient client = getClient();
            APIService service = client.getApiService();
            service.getUpcomingMovie(api_key, page, new Callback<MovieList>() {
                @Override
                public void success(MovieList movieList, Response response) {
                    try {
                        callback.success(movieList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        Log.e(Const.TAG_FAILURE, "RetrofitError: " + error.getMessage());
                        error.printStackTrace();

                        JSONObject errorMessage = new JSONObject();
                        errorMessage.put("message", error.getMessage());
                        callback.failure(errorMessage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getRelatedMovie(final Context context,String movieId,  String api_key,
                                       int page, final GeneralWebServiceCallback callback){
        try {
            RestClient client = getClient();
            APIService service = client.getApiService();
            service.getRelatedMovie(movieId, api_key, page, new Callback<RelatedMovieList>() {
                @Override
                public void success(RelatedMovieList objectReceive, Response response) {
                    try {
                        callback.success(objectReceive);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        Log.e(Const.TAG_FAILURE, "RetrofitError: " + error.getMessage());
                        error.printStackTrace();

                        JSONObject errorMessage = new JSONObject();
                        errorMessage.put("message", error.getMessage());
                        callback.failure(errorMessage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getVideoList(final Context context,String movieId,  String api_key,
                                      final GeneralWebServiceCallback callback){
        try {
            RestClient client = getClient();
            APIService service = client.getApiService();
            service.getMovieVideo(movieId, api_key, new Callback<VideoList>() {
                @Override
                public void success(VideoList objectReceive, Response response) {
                    try {
                        callback.success(objectReceive);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    try {
                        Log.e(Const.TAG_FAILURE, "RetrofitError: " + error.getMessage());
                        error.printStackTrace();

                        JSONObject errorMessage = new JSONObject();
                        errorMessage.put("message", error.getMessage());
                        callback.failure(errorMessage);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static RestClient getClient(){
        RestClient client = new RestClient();
        return client;
    }

}
