package com.example.khue.movieinfo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.network.callbacks.DataOperationCallBack;
import com.example.khue.movieinfo.network.data_management.DataHolder;
import com.example.khue.movieinfo.network.data_management.DataManager;
import com.example.khue.movieinfo.utils.Const;
import com.example.khue.movieinfo.utils.Utils;


public class NowShowingFragment extends Fragment {

    private GridView  gridView;
    private MovieListAdapter adapter;

    private int visibleThreshold = 0;
    private int currentPage = 1;
    private int previousTotal = 0;
    private boolean loading = true;

    public static NowShowingFragment newInstance() {
        NowShowingFragment fragment = new NowShowingFragment();
        return fragment;
    }

    public NowShowingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllMovieShowing();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        try {
            gridView =  (GridView) inflater.inflate(R.layout.fragment_now_showing, container, false);
            adapter = new MovieListAdapter(getActivity(), DataHolder.getInstance().getMovieListFromAPI());
            gridView.setAdapter(adapter);

            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    Utils.showToast(getActivity(), "pos: " + position + " clicked!");
                    Movie movie = DataHolder.getInstance().getMovieListFromAPI().get(position);
                    Log.d(Const.TAG_APP, "Start movie detail with movie at pos: " + position
                            + " title: " + movie.getTitle());

                    Intent intent = new Intent(getActivity(), MovieDetailActivity.class);
                    de.greenrobot.event.EventBus.getDefault().postSticky(movie);
                    startActivity(intent);
                }
            });


            gridView.setOnScrollListener(new AbsListView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(AbsListView absListView, int scrollState) {
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
                        DataManager.getInstance(getActivity().getApplicationContext()).getMovieList(
                                getActivity(), currentPage, new DataOperationCallBack() {
                                    @Override
                                    public void failure() {
                                        Log.i(Const.TAG_APP, "Load more fail: ");
                                    }

                                    @Override
                                    public void success() {
                                        Log.i(Const.TAG_APP, "Load more success: ");
                                        updateMovieGridView();
                                    }
                                });
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridView;
    }
    public void getAllMovieShowing() {
        DataManager.getInstance(getActivity().getApplicationContext())
                .getMovieList(getActivity(), currentPage, new DataOperationCallBack() {
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

    private void updateMovieGridView() {
//        if( gridView != null ){
//            BaseAdapter adapter  = (BaseAdapter) gridView.getAdapter();
//            ((MovieListAdapter)adapter).updateAdapter(DataHolder.getInstance().getMovieListFromAPI());
//            Log.d(Const.TAG_APP, "Notify Data set change on Now showing movie; ");
//        }
        adapter.updateAdapter(DataHolder.getInstance().getMovieListFromAPI());
        Log.d(Const.TAG_APP, "Notify Data set change on Now showing movie; ");
    }
}