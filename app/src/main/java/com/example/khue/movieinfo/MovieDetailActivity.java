package com.example.khue.movieinfo;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.khue.movieinfo.database.DB;
import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.utils.Const;
import com.example.khue.movieinfo.utils.Utils;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MovieDetailActivity extends AppCompatActivity {

    @Bind(R.id.text_title)  TextView title;
    @Bind(R.id.text_date_release)  TextView releaseDate;
    @Bind(R.id.text_language)  TextView language;
    @Bind(R.id.text_overview)  TextView overview;
    @Bind(R.id.text_favorite)  TextView favoriteText;
    @Bind(R.id.image_backdrop) ImageView backdropImage;
    @Bind(R.id.image_poster) ImageView posterImage;
    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.fab)  FloatingActionButton fab;
    @Bind(R.id.toggle_favorite)  ToggleButton favoriteButton;

    private Movie movie;
    private Boolean fromDatabase;
    private Boolean initialCheckFavorite;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        movie = (Movie) EventBus.getDefault().removeStickyEvent(Movie.class);
        initView();
        setMovieDetail();
        checkFavoriteMovie();
        addTrailerFragment();
        addRelatedMovieFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        de.greenrobot.event.EventBus.getDefault().postSticky(movie);
        Log.d(Const.TAG_APP, "Send movie to current activity");
    }

    private void addTrailerFragment() {
        try {
            android.app.FragmentManager manager = getFragmentManager();
            TrailerFragment fragment = TrailerFragment.newInstance(movie.getStringId());
            manager.beginTransaction()
                    .add(R.id.grid_trailer_video, fragment, Const.VIDEO_FRAGMENT)
                    .addToBackStack(Const.VIDEO_FRAGMENT)
                    .commit();
            manager.executePendingTransactions();
            Log.d(Const.TAG_APP, "added trailer fragment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addRelatedMovieFragment() {
        try {
            android.app.FragmentManager manager = getFragmentManager();
            RelatedMovieFragment fragment = RelatedMovieFragment.newInstance(movie.getStringId());
            manager.beginTransaction()
                    .add(R.id.grid_related_movie, fragment, Const.RELATED_MOVIE_FRAGMENT)
                    .addToBackStack(Const.RELATED_MOVIE_FRAGMENT)
                    .commit();
            manager.executePendingTransactions();
            Log.d(Const.TAG_APP, "added related movie fragment");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkFavoriteMovie() {
        try {
            initialCheckFavorite = true;
            if( DB.getMovieById(MovieDetailActivity.this.getApplicationContext(), movie.getId()) != null){
                Log.d(Const.TAG_APP, "Current movie is favorite !");
                favoriteButton.setChecked(true);
            }else {
                Log.d(Const.TAG_APP, "Current movie is NOT favorite !");
            }
            initialCheckFavorite = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMovieDetail() {
        try {
            if(null == movie){
                return;
            }
            String titleStr = movie.getTitle() + " (" + movie.getReleaseDate().substring(0,4) + ")";
            title.setText(titleStr);
            releaseDate.setText(movie.getReleaseDate());

            Locale locale = new Locale(movie.getOriginalLanguage());
            language.setText(locale.getDisplayLanguage());

            overview.setText(movie.getOverview());

            Intent intent = getIntent();
            fromDatabase = intent.getBooleanExtra(Const.FROM_DATABASE, false);
            if( fromDatabase){
                Log.d(Const.TAG_APP, "Load from Database"
                        + "\n" + " poster: " + movie.getLocalImagePosterURL()
                        + "\n" + " backdrop: " + movie.getLocalBackDropURL());
                if(movie.getLocalImagePosterURL() != null){
                    Picasso.with(MovieDetailActivity.this)
                            .load(new File(movie.getLocalImagePosterURL()))
                            .fit().centerCrop()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.ic_movie_icon_2)
                            .into(posterImage);
                }

                if(movie.getLocalBackDropURL() != null){
                    Picasso.with(MovieDetailActivity.this)
                            .load(new File(movie.getLocalBackDropURL()))
                            .fit().centerCrop()
                            .placeholder(R.drawable.progress_animation)
                            .error(R.drawable.ic_movie_icon_2)
                            .into(backdropImage);
                }
            }else {
                Log.d(Const.TAG_APP, "Not in database: load from API + " +
                        " poster path: " + movie.getPosterPath()
                        + " backdrop path: " + movie.getBackdropPath());
                Picasso.with(MovieDetailActivity.this)
                        .load(Utils.getCompleteImageURL(movie.getPosterPath()))
                        .fit().centerCrop()
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.ic_movie_icon_2)
                        .into(posterImage);

                Picasso.with(MovieDetailActivity.this)
                        .load(Utils.getCompleteImageURL(movie.getBackdropPath()))
                        .fit().centerCrop()
                        .placeholder(R.drawable.progress_animation)
                        .error(R.drawable.ic_movie_icon_2)
                        .into(backdropImage);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            MovieDetailActivity.this.setTitle(movie.getTitle());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void initView() {
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteButton.setChecked(!favoriteButton.isChecked());
            }
        });

        favoriteButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                handleFavoriteCheck(isChecked);
            }
        });
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


    private void handleFavoriteCheck(boolean isChecked) {
        try {
            Log.d(Const.TAG_APP, "init check : " + initialCheckFavorite);
            if (isChecked) {
                // The toggle is enabled
                favoriteButton.setBackgroundResource(android.R.drawable.btn_star_big_on);
                favoriteText.setText(R.string.in_favorite);
                fab.setImageResource(android.R.drawable.btn_star_big_on);
                if(!initialCheckFavorite){
                    Utils.showToast(MovieDetailActivity.this, getResources().getString(R.string.added_to_favorite));
                    addFavoriteMovieToDatabase();
                }
            } else {
                // The toggle is disabled
                favoriteButton.setBackgroundResource(android.R.drawable.btn_star_big_off);
                favoriteText.setText(R.string.not_in_favorite);
                fab.setImageResource(android.R.drawable.btn_star_big_off);
                if(!initialCheckFavorite){
                    Utils.showToast(MovieDetailActivity.this, getResources().getString(R.string.remove_favorite));
                    removeMovieFromFavorite();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeMovieFromFavorite() {
        try {
            DB.deleteMovie(MovieDetailActivity.this.getApplicationContext(), movie);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFavoriteMovieToDatabase() {
        try {
            if( movie.getPosterPath() == null){
                movie.setLocalImagePosterURL(null);
            }else {
                posterImage.buildDrawingCache();
                Bitmap bitmap = posterImage.getDrawingCache();
                String localPoster = Utils.saveBitmapToFile( "poster_" + String.valueOf(movie.getId()) , bitmap);
                Log.d(Const.TAG_APP, " local poster: " + localPoster);
                movie.setLocalImagePosterURL(localPoster);
            }
            if( movie.getBackdropPath() == null){
                movie.setLocalBackDropURL(null);
            }else {
                backdropImage.buildDrawingCache();
                Bitmap bitmap = backdropImage.getDrawingCache();
                String localBackDrop = Utils.saveBitmapToFile("backdrop_" + String.valueOf(movie.getId()), bitmap);
                Log.d(Const.TAG_APP, " local backdrop: " + localBackDrop);
                movie.setLocalBackDropURL(localBackDrop);
            }

            DB.addMovie(MovieDetailActivity.this.getApplicationContext(), movie);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
