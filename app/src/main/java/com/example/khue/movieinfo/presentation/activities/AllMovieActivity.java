package com.example.khue.movieinfo.presentation.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.khue.movieinfo.R;
import com.example.khue.movieinfo.presentation.fragment.FavoriteFragment;
import com.example.khue.movieinfo.presentation.fragment.NowShowingMovieFragment;
import com.ogaclejapan.smarttablayout.SmartTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class AllMovieActivity extends AppCompatActivity {

     @Bind(R.id.pager) ViewPager viewPager;
     @Bind(R.id.viewpagertab) SmartTabLayout viewPagerTab;
     @Bind(R.id.toolbar) Toolbar toolbar;

    private  ViewPagerAdapter viewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movie);

        initView();
    }

    private void initView() {
        try {
            ButterKnife.bind(this);
            setSupportActionBar(toolbar);

            viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager(), AllMovieActivity.this.getApplicationContext());
            viewPager.setAdapter(viewPagerAdapter);

            viewPagerTab.setViewPager(viewPager);
            ((TextView)viewPagerTab.getTabAt(0)).setTextColor(ContextCompat.getColor(AllMovieActivity.this, R.color.white));
            viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    viewPager.setCurrentItem(position);
                    for( int i = 0 ; i < viewPagerAdapter.getCount(); i++){
                        TextView view = (TextView) viewPagerTab.getTabAt(i);
                        if( i == position){
//                            view.setTextColor(ContextCompat.getColor(AllMovieActivity.this, R.color.view_pager_color));
                            view.setTextColor(ContextCompat.getColor(AllMovieActivity.this, R.color.white));
                        }else {
                            view.setTextColor(ContextCompat.getColor(AllMovieActivity.this, R.color.movie_background_color));
                        }
                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @Override
    protected void onResume() {
        super.onResume();
    }

    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        public static final int NUMBER_OF_FRAGMENT = 2;
        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }
        private Context context;

        public ViewPagerAdapter(FragmentManager fm, Context c){
            super(fm);
            context = c;
        }
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NowShowingMovieFragment();
                case  1:
                    return  new FavoriteFragment();
            }
            return null;
        }

        @Override
        public CharSequence getPageTitle(int position){
            switch (position){
                case 0:
                    return this.context.getResources().getString(R.string.now_showing);
                case 1 :
                    return this.context.getResources().getString(R.string.favorites);
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUMBER_OF_FRAGMENT;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBackPressed() {
        if (viewPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            viewPager.setCurrentItem(viewPager.getCurrentItem() - 1);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_all_movie, menu);
        return true;
    }

}
