package com.example.khue.movieinfo;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.khue.movieinfo.model.Movie;
import com.example.khue.movieinfo.utils.Utils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * TODO: Add a class header comment!
 */
public class MovieListAdapter extends BaseAdapter {
    ImageLoader imageLoader ;
    private Context mContext;
    private List<Movie> movieList;
    public MovieListAdapter(Context c, List<Movie> movieList) {
        mContext = c;
        this.movieList = movieList;
         imageLoader = ImageLoader.getInstance();
        imageLoader.init(ImageLoaderConfiguration.createDefault(mContext));
    }

    public int getCount() {
        return movieList.size();
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

        Movie movie = movieList.get(position);
        String url = Utils.getCompleteImageURL(movie.getPosterPath());
        if( url != null && !TextUtils.isEmpty(url)){
            Picasso.with(mContext)
                    .load(url)
                    .fit()
                    .centerCrop()
                    .error(R.drawable.ic_movie_icon_2)
                    .into(holder.imageView);
//            imageLoader.displayImage(url, holder.imageView);
        }else {
            holder.imageView.setImageResource(R.drawable.ic_movie_icon_2);
        }
        return convertView;
    }

    public void updateAdapter(List<Movie> movieList) {
        this.movieList= movieList;
        notifyDataSetChanged();
    }

    public static class ViewHolder  {
        @Bind(R.id.image_movie_poster) ImageView imageView;
        public ViewHolder(View view){
            ButterKnife.bind(this, view);
        }
    }
}
