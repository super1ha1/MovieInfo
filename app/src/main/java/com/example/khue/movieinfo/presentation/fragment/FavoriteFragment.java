package com.example.khue.movieinfo.presentation.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.khue.movieinfo.R;
import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.network.callbacks.DataOperationCallBack;
import com.example.khue.movieinfo.network.data_management.DataHolder;
import com.example.khue.movieinfo.network.data_management.DataManager;
import com.example.khue.movieinfo.presentation.activities.MovieDetailActivity;
import com.example.khue.movieinfo.presentation.adapter.MovieListAdapter;
import com.example.khue.movieinfo.utils.Const;

import butterknife.Bind;
import butterknife.ButterKnife;


public class FavoriteFragment extends Fragment {

    private View rootView;
    @Bind(R.id.grid_view_favorite) GridView  gridView;
    @Bind(R.id.empty_view)  TextView emptyView;
    private MovieListAdapter adapter;

    public static FavoriteFragment newInstance() {
        FavoriteFragment fragment = new FavoriteFragment();
        return fragment;
    }

    public FavoriteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d(Const.TAG_APP, "Oncreate View Favorite here");

        rootView = inflater.inflate(R.layout.fragment_favorite, container, false);
        ButterKnife.bind(this, rootView);

        adapter = new MovieListAdapter(getActivity(), DataHolder.getInstance().getMovieListFromDatabase());
        gridView.setAdapter(adapter);

        gridView.setEmptyView(emptyView);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Movie movie = DataHolder.getInstance().getMovieListFromDatabase().get(position);
                Log.d(Const.TAG_APP, "Start movie detail with movie at pos: " + position
                        + " title: " + movie.getTitle());

                Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                intent.putExtra(Const.FROM_DATABASE, true);
                de.greenrobot.event.EventBus.getDefault().postSticky(movie);
                startActivity(intent);
            }
        });
        return rootView;
    }
    public void getAllFavoriteMovie() {
        DataManager.getInstance(getActivity().getApplicationContext())
                .getFavoriteMovie(getActivity(), new DataOperationCallBack() {
                    @Override
                    public void failure() {
                        Log.d(Const.TAG_APP, "DataCall Load movie list error");
                    }

                    @Override
                    public void success() {
                        Log.d(Const.TAG_APP, "DataCall Load movie list success");
                        updateMovieGridView();
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
        getAllFavoriteMovie();
        Log.d(Const.TAG_APP, "On resume Favorite : load movie");

    }

    private void updateMovieGridView() {
        try {
            if( adapter != null){
                adapter.updateAdapter(DataHolder.getInstance().getMovieListFromDatabase());
            }
            Log.d(Const.TAG_APP, "Notify Data set change on Favorite movie; ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
