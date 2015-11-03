package com.example.khue.movieinfo;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.RadioGroup;

import com.example.khue.movieinfo.network.callbacks.DataOperationCallBack;
import com.example.khue.movieinfo.network.data_management.DataHolder;
import com.example.khue.movieinfo.network.data_management.DataManager;
import com.example.khue.movieinfo.utils.Const;
import com.example.khue.movieinfo.utils.PreferenceManager;

public class AllMovieActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private ViewPagerAdapter viewPagerAdapter;
    private RadioGroup mTabs;
    PreferenceManager pm = null;
    private int page = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_movie);

//        DBSetupManager.setup(getApplicationContext());
        pm = new PreferenceManager(AllMovieActivity.this.getApplicationContext());
        initView();
    }

    private void initView() {
        try {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            viewPagerAdapter = new ViewPagerAdapter( getSupportFragmentManager(), AllMovieActivity.this.getApplicationContext());
            viewPager = (ViewPager) findViewById(R.id.pager);
            if( viewPager == null){
                Log.d(Const.TAG_APP, "View pager is null");
            }
            viewPager.setAdapter(viewPagerAdapter);

            mTabs = (RadioGroup) findViewById(R.id.radio_tabs);
            mTabs.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    switch (checkedId) {
                        case R.id.tab_now_showing:
                            viewPager.setCurrentItem(0);
                            break;
                        case R.id.tab_favorite:
                            viewPager.setCurrentItem(1);
                            break;
                    }
                }
            });

            viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

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



    public void getAllMovieTest() {
        Log.d(Const.TAG_APP, "Start Load movie list here");
        DataManager dataManager = DataManager.getInstance(AllMovieActivity.this.getApplicationContext());
        dataManager.getMovieList(AllMovieActivity.this, page, new DataOperationCallBack() {
            @Override
            public void failure() {
                Log.d(Const.TAG_APP, "DataCall Load movie list error");
            }

            @Override
            public void success() {
                Log.d(Const.TAG_APP, "DataCall Load movie list success");
                //TODO do something the UI for the wine deleted
                updateMovieGridView();
            }
        });
    }

    private void updateMovieGridView() {
        GridView gridView = (GridView) findViewById(R.id.nowShowingGridview);
        if( gridView != null ){
            BaseAdapter adapter  = (BaseAdapter)gridView.getAdapter();
            ((MovieListAdapter)adapter).updateAdapter(DataHolder.getInstance().getMovieListFromAPI());
            Log.d(Const.TAG_APP, "Notify Data set change on Now showing movie; ");
        }
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
                    return new NowShowingFragment();
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
