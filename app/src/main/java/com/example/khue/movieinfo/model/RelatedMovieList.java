package com.example.khue.movieinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to parse related movie list
 */
public class RelatedMovieList {


    @SerializedName("page")
    @Expose
    private int page;

    @SerializedName("results")
    @Expose
    private List<Movie> movies = new ArrayList<Movie>();

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    @SerializedName("total_results")
    @Expose
    private int totalResults;

    /**
     * No args constructor for use in serialization
     *
     */
    public RelatedMovieList() {
    }

    /**
     *
     * @param movies
     * @param totalResults
     * @param page
     * @param totalPages
     */
    public RelatedMovieList( int page, List<Movie> movies, int totalPages, int totalResults) {
        this.page = page;
        this.movies = movies;
        this.totalPages = totalPages;
        this.totalResults = totalResults;
    }

    /**
     *
     * @return
     *     The page
     */
    public int getPage() {
        return page;
    }

    /**
     *
     * @param page
     *     The page
     */
    public void setPage(int page) {
        this.page = page;
    }

    /**
     *
     * @return
     *     The results
     */
    public List<Movie> getMovies() {
        return movies;
    }

    /**
     *
     * @param movies
     *     The results
     */
    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }

    /**
     *
     * @return
     *     The totalPages
     */
    public int getTotalPages() {
        return totalPages;
    }

    /**
     *
     * @param totalPages
     *     The total_pages
     */
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    /**
     *
     * @return
     *     The totalResults
     */
    public int getTotalResults() {
        return totalResults;
    }

    /**
     *
     * @param totalResults
     *     The total_results
     */
    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

}
