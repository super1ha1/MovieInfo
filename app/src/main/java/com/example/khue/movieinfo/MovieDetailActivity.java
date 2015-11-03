package com.example.khue.movieinfo;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

public class MovieDetailActivity extends AppCompatActivity {

    @Bind(R.id.gridview_related_movie) GridView gridView;
    @Bind(R.id.text_title)  TextView title;
    @Bind(R.id.text_date_release)  TextView releaseDate;
    @Bind(R.id.text_language)  TextView language;
    @Bind(R.id.text_overview)  TextView overview;
    @Bind(R.id.image_backdrop) ImageView backdropImage;
    @Bind(R.id.image_poster) ImageView posterImage;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        movie = (Movie) EventBus.getDefault().removeStickyEvent(Movie.class);

        initView();
        setMovieDetail();
    }

    private void setMovieDetail() {
        if(null == movie){
            return;
        }
        String titleStr = movie.getTitle() + " (" + movie.getReleaseDate().substring(0,4) + ")";
        title.setText(titleStr);
        releaseDate.setText(movie.getReleaseDate());

        Locale locale = new Locale(movie.getOriginalLanguage());
        language.setText(locale.getDisplayLanguage());

        overview.setText(movie.getOverview());

        Picasso.with(MovieDetailActivity.this)
                .load(Utils.getCompleteImageURL(movie.getPosterPath()))
                .error(R.drawable.ic_movie_icon_2)
                .into(posterImage);

        Picasso.with(MovieDetailActivity.this)
                .load(Utils.getCompleteImageURL(movie.getBackdropPath()))
                .error(R.drawable.ic_movie_icon_2)
                .into(backdropImage);

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);
    }

}
