package com.example.khue.movieinfo.network.data_management;


import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.model.MovieList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Singleton;

/**
 * Class holder for all the object data (not in Preference manager)
 * object retrieved from database or API would be reference here
 */
@Singleton
public class DataHolder {
    private static DataHolder instance;
    private DataHolder(){
    };

    public static DataHolder getInstance(){
        if( instance == null)
            instance = new DataHolder();
        return instance;
    }

    private  MovieList nowShowingMovieList = null;
    private  List<Movie> movieListFromAPI = new ArrayList<Movie>();
    private  List<Movie> movieListFromDatabase = new ArrayList<Movie>();
    private Movie currentMovieDetail = null;

    public MovieList getNowShowingMovieList() {
        return nowShowingMovieList;
    }

    public void setNowShowingMovieList(MovieList nowShowingMovieList) {
        this.nowShowingMovieList = nowShowingMovieList;
    }

    public List<Movie> getMovieListFromAPI() {
        return movieListFromAPI;
    }

    public void setMovieListFromAPI(List<Movie> movieListFromAPI) {
        this.movieListFromAPI = movieListFromAPI;
    }

    public List<Movie> getMovieListFromDatabase() {
        return movieListFromDatabase;
    }

    public void setMovieListFromDatabase(List<Movie> movieListFromDatabase) {
        this.movieListFromDatabase = movieListFromDatabase;
    }

    public Movie getCurrentMovieDetail() {
        return currentMovieDetail;
    }

    public void setCurrentMovieDetail(Movie currentMovieDetail) {
        this.currentMovieDetail = currentMovieDetail;
    }

}