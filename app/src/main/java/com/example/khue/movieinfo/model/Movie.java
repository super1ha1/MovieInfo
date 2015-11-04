
package com.example.khue.movieinfo.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;
import java.util.List;

@DatabaseTable(tableName = "movies")
public class Movie {
    public Movie(String localImagePosterURL, String localBackDropURL, Boolean favorite, boolean adult, String backdropPath, List<Integer> genreIds, int id, String originalLanguage, String originalTitle, String overview, String releaseDate, String posterPath, double popularity, String title, boolean video, double voteAverage, int voteCount) {
        this.localImagePosterURL = localImagePosterURL;
        this.localBackDropURL = localBackDropURL;
        this.favorite = favorite;
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.genreIds = genreIds;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    public Movie(String localImagePosterURL, String localBackDropURL, boolean adult, String backdropPath, int id, String originalLanguage, String originalTitle, String overview, String releaseDate, String posterPath, double popularity, String title, boolean video, double voteAverage, int voteCount) {
        this.localImagePosterURL = localImagePosterURL;
        this.localBackDropURL = localBackDropURL;
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    private List<Movie> relatedMovieList;


    public List<Movie> getRelatedMovieList() {
        return relatedMovieList;
    }

    public void setRelatedMovieList(List<Movie> relatedMovieList) {
        this.relatedMovieList = relatedMovieList;
    }

    @DatabaseField
    private String localImagePosterURL;

    @DatabaseField
    private String localBackDropURL;

    public String getLocalBackDropURL() {
        return localBackDropURL;
    }

    public void setLocalBackDropURL(String localBackDropURL) {
        this.localBackDropURL = localBackDropURL;
    }

    private Boolean favorite = false;
    public String getLocalImagePosterURL() {
        return localImagePosterURL;
    }

    public void setLocalImagePosterURL(String localImagePosterURL) {
        this.localImagePosterURL = localImagePosterURL;
    }

    public Boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(Boolean favorite) {
        this.favorite = favorite;
    }


    @DatabaseField
    @SerializedName("adult")
    @Expose
    private boolean adult;

    @DatabaseField
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;

    @SerializedName("genre_ids")
    @Expose
    private List<Integer> genreIds = new ArrayList<Integer>();

    @DatabaseField(generatedId=true, allowGeneratedIdInsert=true)
    @SerializedName("id")
    @Expose
    private int id;

    @DatabaseField
    @SerializedName("original_language")
    @Expose
    private String originalLanguage;

    @DatabaseField
    @SerializedName("original_title")
    @Expose
    private String originalTitle;

    @DatabaseField
    @SerializedName("overview")
    @Expose
    private String overview;

    @DatabaseField
    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @DatabaseField
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @DatabaseField
    @SerializedName("popularity")
    @Expose
    private double popularity;

    @DatabaseField
    @SerializedName("title")
    @Expose
    private String title;

    @DatabaseField
    @SerializedName("video")
    @Expose
    private boolean video;

    @DatabaseField
    @SerializedName("vote_average")
    @Expose
    private double voteAverage;

    @DatabaseField
    @SerializedName("vote_count")
    @Expose
    private int voteCount;

    /**
     * No args constructor for use in serialization
     * 
     */
    public Movie() {
    }

    /**
     * 
     * @param id
     * @param genreIds
     * @param title
     * @param releaseDate
     * @param overview
     * @param posterPath
     * @param originalTitle
     * @param voteAverage
     * @param originalLanguage
     * @param adult
     * @param backdropPath
     * @param voteCount
     * @param video
     * @param popularity
     */
    public Movie(boolean adult, String backdropPath, List<Integer> genreIds, int id, String originalLanguage, String originalTitle, String overview, String releaseDate, String posterPath, double popularity, String title, boolean video, double voteAverage, int voteCount) {
        this.adult = adult;
        this.backdropPath = backdropPath;
        this.genreIds = genreIds;
        this.id = id;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.posterPath = posterPath;
        this.popularity = popularity;
        this.title = title;
        this.video = video;
        this.voteAverage = voteAverage;
        this.voteCount = voteCount;
    }

    /**
     * 
     * @return
     *     The adult
     */
    public boolean isAdult() {
        return adult;
    }

    /**
     * 
     * @param adult
     *     The adult
     */
    public void setAdult(boolean adult) {
        this.adult = adult;
    }

    /**
     * 
     * @return
     *     The backdropPath
     */
    public String getBackdropPath() {
        return backdropPath;
    }

    /**
     * 
     * @param backdropPath
     *     The backdrop_path
     */
    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    /**
     * 
     * @return
     *     The genreIds
     */
    public List<Integer> getGenreIds() {
        return genreIds;
    }

    /**
     * 
     * @param genreIds
     *     The genre_ids
     */
    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }


    public String getStringId(){return String.valueOf(id); }

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
     *     The originalLanguage
     */
    public String getOriginalLanguage() {
        return originalLanguage;
    }

    /**
     * 
     * @param originalLanguage
     *     The original_language
     */
    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    /**
     * 
     * @return
     *     The originalTitle
     */
    public String getOriginalTitle() {
        return originalTitle;
    }

    /**
     * 
     * @param originalTitle
     *     The original_title
     */
    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    /**
     * 
     * @return
     *     The overview
     */
    public String getOverview() {
        return overview;
    }

    /**
     * 
     * @param overview
     *     The overview
     */
    public void setOverview(String overview) {
        this.overview = overview;
    }

    /**
     * 
     * @return
     *     The releaseDate
     */
    public String getReleaseDate() {
        return releaseDate;
    }

    /**
     * 
     * @param releaseDate
     *     The release_date
     */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    /**
     * 
     * @return
     *     The posterPath
     */
    public String getPosterPath() {
        return posterPath;
    }

    /**
     * 
     * @param posterPath
     *     The poster_path
     */
    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    /**
     * 
     * @return
     *     The popularity
     */
    public double getPopularity() {
        return popularity;
    }

    /**
     * 
     * @param popularity
     *     The popularity
     */
    public void setPopularity(double popularity) {
        this.popularity = popularity;
    }

    /**
     * 
     * @return
     *     The title
     */
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The video
     */
    public boolean isVideo() {
        return video;
    }

    /**
     * 
     * @param video
     *     The video
     */
    public void setVideo(boolean video) {
        this.video = video;
    }

    /**
     * 
     * @return
     *     The voteAverage
     */
    public double getVoteAverage() {
        return voteAverage;
    }

    /**
     * 
     * @param voteAverage
     *     The vote_average
     */
    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

    /**
     * 
     * @return
     *     The voteCount
     */
    public int getVoteCount() {
        return voteCount;
    }

    /**
     * 
     * @param voteCount
     *     The vote_count
     */
    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

}
