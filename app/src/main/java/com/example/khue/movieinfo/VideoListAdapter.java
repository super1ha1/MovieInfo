package com.example.khue.movieinfo;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.khue.movieinfo.model.MovieVideo;
import com.example.khue.movieinfo.utils.Const;
import com.example.khue.movieinfo.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Adapter for video grid view
 *
 */
public class VideoListAdapter extends BaseAdapter {
    private Context mContext;
    private List<MovieVideo> videoList;
    public VideoListAdapter(Context c, List<MovieVideo> videoList) {
        mContext = c;
        this.videoList = videoList;
        Log.d(Const.TAG_APP, "Current list size: " + videoList.size());
    }

    public int getCount() {
        return videoList.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.image_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        MovieVideo movieVideo = videoList.get(position);
        String url = Utils.getYoutubeVideoURL(movieVideo.getKey());
        if( url != null && !TextUtils.isEmpty(url)){
            Picasso.with(mContext)
                    .load(url)
                    .fit()
                    .centerCrop()
                    .placeholder(R.drawable.progress_animation)
                    .error(R.drawable.ic_movie_icon_2)
                    .into(holder.imageView);
        }else {
            holder.imageView.setImageResource(R.drawable.ic_movie_icon_2);
        }
        return convertView;
    }

    public void updateAdapter(List<MovieVideo> movieList) {
        this.videoList = movieList;
        notifyDataSetChanged();
    }

    public static class ViewHolder  {
        @Bind(R.id.image_movie_poster)
        ImageView imageView;
        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
