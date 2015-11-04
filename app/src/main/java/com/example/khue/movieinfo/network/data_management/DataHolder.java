package com.example.khue.movieinfo.network.data_management;


import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.model.MovieList;
import com.example.khue.movieinfo.model.RelatedMovieList;
import com.example.khue.movieinfo.model.VideoList;

import java.util.ArrayList;
import java.util.HashMap;
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
    private HashMap<String, RelatedMovieList> relatedMovieMap = new HashMap<String, RelatedMovieList>();

    public HashMap<String, VideoList> getMovieVideoMap() {
        return movieVideoMap;
    }

    public void setMovieVideoMap(HashMap<String, VideoList> movieVideoMap) {
        this.movieVideoMap = movieVideoMap;
    }

    private HashMap<String, VideoList> movieVideoMap = new HashMap<String, VideoList>();

    public HashMap<String, RelatedMovieList> getRelatedMovieMap() {
        return relatedMovieMap;
    }

    public void setRelatedMovieMap(HashMap<String, RelatedMovieList> relatedMovieMap) {
        this.relatedMovieMap = relatedMovieMap;
    }

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
