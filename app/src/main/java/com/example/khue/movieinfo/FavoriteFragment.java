package com.example.khue.movieinfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.khue.movieinfo.network.data_management.DataHolder;
import com.example.khue.movieinfo.utils.Utils;


public class FavoriteFragment extends Fragment {

    private GridView gridView;
    private MovieListAdapter adapter;

    public static FavoriteFragment newInstance(String param1, String param2) {
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

        gridView = (GridView) inflater.inflate(R.layout.fragment_favorite, container, false);
        adapter = new MovieListAdapter(getActivity(), DataHolder.getInstance().getMovieListFromAPI());
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id){
                Utils.showToast(getActivity(), "pos: " + position + " clicked!");
            }
        });
        return gridView;
    }

}
