package com.example.khue.movieinfo;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.model.RelatedMovieList;
import com.example.khue.movieinfo.network.callbacks.DataOperationCallBack;
import com.example.khue.movieinfo.network.data_management.DataHolder;
import com.example.khue.movieinfo.network.data_management.DataManager;
import com.example.khue.movieinfo.utils.Const;
import com.example.khue.movieinfo.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class RelatedMovieFragment extends Fragment {
    private int visibleThreshold = 0;
    private int currentPage = 1;
    private int previousTotal = 0;
    private boolean loading = true;

    private String movieId;

    private GridView gridView;


    private MovieListAdapter relatedMovieAdapter;
    private static final String ARG_PARAM1 = "MOVIE_ID";


    public static RelatedMovieFragment newInstance(String param1) {
        RelatedMovieFragment fragment = new RelatedMovieFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_PARAM1, param1);
        fragment.setArguments(bundle);
        return fragment;
    }

    public RelatedMovieFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(ARG_PARAM1);
        }
        getRelatedMovie();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            gridView = (GridView)  inflater.inflate(R.layout.fragment_related_movie, container, false);

            if( DataHolder.getInstance().getRelatedMovieMap().get(movieId) == null ){
                RelatedMovieList relatedMovieList = new RelatedMovieList();
                List<Movie> moviesList = new ArrayList<Movie>();
                relatedMovieList.setMovies(moviesList);
                DataHolder.getInstance().getRelatedMovieMap().put(movieId, relatedMovieList);
            }
            relatedMovieAdapter = new MovieListAdapter(getActivity(),
                    DataHolder.getInstance().getRelatedMovieMap().get(movieId).getMovies());
            gridView.setAdapter(relatedMovieAdapter);

            gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int i) {

                }

                @Override
                public void onScroll(AbsListView absListView, int firstVisibleItem,
                                     int visibleItemCount, int totalItemCount) {
                    if (loading) {
                        if (totalItemCount > previousTotal) {
                            loading = false;
                            previousTotal = totalItemCount;
                            currentPage++;
                        }
                    }
                    if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
                        loading = true;
                        DataManager.getInstance(getActivity().getApplicationContext()).getRelatedMovie(
                                getActivity(), movieId, currentPage, new DataOperationCallBack() {
                                    @Override
                                    public void failure() {
                                        Log.i(Const.TAG_APP, "Load more fail: ");
                                    }

                                    @Override
                                    public void success() {
                                        Log.i(Const.TAG_APP, "Load more success: ");
                                        updateRelatedMovieGridView();
                                    }
                                });
                    }
                }
            });

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Utils.showToast(getActivity(), "pos: " + position + " clicked!");
                    Movie movie = DataHolder.getInstance().getRelatedMovieMap()
                            .get(movieId).getMovies().get(position);
                    Log.d(Const.TAG_APP, "Start movie detail with movie at pos: " + position
                            + " title: " + movie.getTitle());

                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    intent.putExtra(Const.FROM_DATABASE, false);
                    de.greenrobot.event.EventBus.getDefault().postSticky(movie);
                    startActivity(intent);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridView;
    }

    private void updateRelatedMovieGridView() {
        try {
            if( relatedMovieAdapter != null)
                relatedMovieAdapter.updateAdapter(DataHolder.getInstance().getRelatedMovieMap()
                        .get(movieId).getMovies());
            Log.d(Const.TAG_APP, "Notify Data set change on Related movie; ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getRelatedMovie() {
        DataManager.getInstance(getActivity().getApplicationContext())
                .getRelatedMovie(getActivity(), movieId,
                        currentPage, new DataOperationCallBack() {
                            @Override
                            public void failure() {
                                Log.d(Const.TAG_APP, "DataCall Load movie list error");
                            }
                            @Override
                            public void success() {
                                Log.d(Const.TAG_APP, "DataCall Load movie list success");
                                updateRelatedMovieGridView();
                            }
                        });
    }

}
