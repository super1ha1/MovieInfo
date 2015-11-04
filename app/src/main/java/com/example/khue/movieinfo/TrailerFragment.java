package com.example.khue.movieinfo;

import android.app.Fragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.khue.movieinfo.model.MovieVideo;
import com.example.khue.movieinfo.model.VideoList;
import com.example.khue.movieinfo.network.callbacks.DataOperationCallBack;
import com.example.khue.movieinfo.network.data_management.DataHolder;
import com.example.khue.movieinfo.network.data_management.DataManager;
import com.example.khue.movieinfo.utils.Const;

import java.util.ArrayList;
import java.util.List;


public class TrailerFragment extends Fragment {

    private String movieId;

    private GridView gridView;
    private VideoListAdapter videoListAdapter;
    private static final String ARG_PARAM1 = "MOVIE_ID";



    public static TrailerFragment newInstance(String param1) {
        TrailerFragment fragment = new TrailerFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    public TrailerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieId = getArguments().getString(ARG_PARAM1);
        }
        getMovieVideo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        try {
            gridView =  (GridView) inflater.inflate(R.layout.fragment_trailer, container, false);
            if( DataHolder.getInstance().getMovieVideoMap().get(movieId) == null ){
                VideoList list = new VideoList();
                List<MovieVideo> videoList = new ArrayList<MovieVideo>();
                list.setMovieVideos(videoList);
                DataHolder.getInstance().getMovieVideoMap().put(movieId, list);
            }

            videoListAdapter = new VideoListAdapter(getActivity(),
                    DataHolder.getInstance().getMovieVideoMap().get(movieId).getMovieVideos());
            gridView.setAdapter(videoListAdapter);


            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View v,
                                        int position, long id) {
                    MovieVideo video = DataHolder.getInstance().getMovieVideoMap().get(movieId).getMovieVideos().get(position);
                    String youtubeId = video.getKey();
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://" + youtubeId));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        Log.d(Const.TAG_APP, "Open using youtube app");
                    }catch(ActivityNotFoundException e) {
                        // youtube is not installed.Will be opened in other available apps
                        String content = "http://www.youtube.com/watch?v=" + youtubeId;
                        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(content));
                        startActivity(i);
                        Log.d(Const.TAG_APP, "Open using other  app");
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        return gridView;
    }

    private void updateRelatedMovieGridView() {
        try {
            if( videoListAdapter != null)
                videoListAdapter.updateAdapter(DataHolder.getInstance().getMovieVideoMap()
                        .get(movieId).getMovieVideos());
            Log.d(Const.TAG_APP, "Notify Data set change on Movie movie; ");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void getMovieVideo() {
        DataManager.getInstance(getActivity().getApplicationContext())
                .getMovieVideo(getActivity(), movieId,
                        new DataOperationCallBack() {
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
