package com.example.khue.movieinfo.network.retrofit;

import com.example.khue.movieinfo.model.MovieList;
import com.example.khue.movieinfo.model.RelatedMovieList;
import com.example.khue.movieinfo.model.VideoList;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * API endpoint
 */
public interface APIService {
//    http://api.themoviedb.org/3/movie/now_playing?api_key=4df263f48a4fe2621749627f5d001bf0&page=1
    @GET("/movie/upcoming")
    void getUpcomingMovie(@Query("api_key") String api_key,
                          @Query("page") int page,
                          Callback<MovieList> callback);

//    http://api.themoviedb.org/3/movie/135397/similar?api_key=4df263f48a4fe2621749627f5d001bf0&page=1
    @GET("/movie/{movie_id}/similar")
    void getRelatedMovie(@Path("movie_id") String movieId,
                         @Query("api_key") String api_key,
                         @Query("page") int page,
                         Callback<RelatedMovieList> callback);
//    http://api.themoviedb.org/3/movie/135397/videos?api_key=4df263f48a4fe2621749627f5d001bf0
    @GET("/movie/{movie_id}/videos")
    void getMovieVideo(@Path("movie_id") String movieId,
                     @Query("api_key") String api_key,
                     Callback<VideoList> callback);
}
