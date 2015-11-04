
package com.example.khue.movieinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class VideoList {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("results")
    @Expose
    private List<MovieVideo> movieVideos = new ArrayList<MovieVideo>();

    /**
     * No args constructor for use in serialization
     * 
     */
    public VideoList() {
    }

    /**
     * 
     * @param id
     * @param movieVideos
     */
    public VideoList(int id, List<MovieVideo> movieVideos) {
        this.id = id;
        this.movieVideos = movieVideos;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The results
     */
    public List<MovieVideo> getMovieVideos() {
        return movieVideos;
    }

    /**
     * 
     * @param movieVideos
     *     The results
     */
    public void setMovieVideos(List<MovieVideo> movieVideos) {
        this.movieVideos = movieVideos;
    }

}
