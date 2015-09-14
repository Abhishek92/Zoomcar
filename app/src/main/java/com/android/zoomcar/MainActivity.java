package com.android.zoomcar;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.zoomcar.api.CarApiHitModel;
import com.android.zoomcar.api.CarDetailsModel;
import com.android.zoomcar.api.ZoomCarApiClient;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity implements CarListAdapter.OnItemClickListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private CarListAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private TextView mApiCount;
    private TextView mCarCount;
    private List<CarDetailsModel.Car> carList = new ArrayList<>();
    private int TOTAL_CAR_COUNT = 0;
    private ProgressBar mProgressBar;

    private Comparator<CarDetailsModel.Car> ratingComparator = new Comparator<CarDetailsModel.Car>() {
        @Override
        public int compare(CarDetailsModel.Car lhs, CarDetailsModel.Car rhs) {
            return Float.valueOf(rhs.getRating()).compareTo(Float.valueOf(lhs.getRating()));
        }
    };

    private Comparator<CarDetailsModel.Car> priceComparator = new Comparator<CarDetailsModel.Car>() {
        @Override
        public int compare(CarDetailsModel.Car lhs, CarDetailsModel.Car rhs) {
            return Float.valueOf(rhs.getHourlyRate()).compareTo(Float.valueOf(lhs.getHourlyRate()));
        }
    };

    private Comparator<CarDetailsModel.Car> nameComparator = new Comparator<CarDetailsModel.Car>() {
        @Override
        public int compare(CarDetailsModel.Car lhs, CarDetailsModel.Car rhs) {
            return lhs.getName().compareTo(rhs.getName());
        }
    };
    private SwipeRefreshLayout refreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mApiCount = (TextView) findViewById(R.id.apiCount);
        mCarCount = (TextView) findViewById(R.id.carCount);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);

        setRecyclerView();
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        refreshLayout.setColorSchemeResources(R.color.color_primary);

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getCarsList(false);
                getNoOfApiHits();
            }
        });

        mToolbar.setTitle(getString(R.string.app_title));
        mToolbar.inflateMenu(R.menu.menu_main);
        setSupportActionBar(mToolbar);
        getCarsList(true);
        getNoOfApiHits();

    }

    private void setRecyclerView()
    {
        mRecyclerView = (RecyclerView) findViewById(R.id.carList);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        // Inflate the menu; this adds items to the action bar if it is present.
        SearchManager searchManager = (SearchManager)this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(this.getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setQueryHint(getString(R.string.search_query_hint));

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextChange(String newText)
            {
                if(mAdapter != null && !TextUtils.isEmpty(newText))
                    mAdapter.setFilter(newText);
                else if(mAdapter != null && TextUtils.isEmpty(newText))
                    mAdapter.flushFilter();
                return true;
            }
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                if(mAdapter != null && !TextUtils.isEmpty(query))
                    mAdapter.setFilter(query);
                else if(mAdapter != null && TextUtils.isEmpty(query))
                    mAdapter.flushFilter();
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_price) {
            sortList(priceComparator);
            return true;
        }

        if (id == R.id.action_sort_rating) {
            sortList(ratingComparator);
            return true;
        }

        if (id == R.id.action_sort_name) {
            sortList(nameComparator);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void getCarsList(boolean showProgress)
    {
        if(Util.isNetworkConnected(this)) {
            if(showProgress)
                mProgressBar.setVisibility(View.VISIBLE);
          ZoomCarApiClient.getCarsApi().getCarsDetail(new Callback<CarDetailsModel>() {
              @Override
              public void success(CarDetailsModel cars, Response response) {

                  if (response.getStatus() == 200) {
                      mProgressBar.setVisibility(View.GONE);
                      if(refreshLayout.isRefreshing())
                          refreshLayout.setRefreshing(false);
                      carList = new ArrayList<CarDetailsModel.Car>(cars.getCars());
                      mAdapter = new CarListAdapter(MainActivity.this, carList);
                      mAdapter.setOnItemClickListener(MainActivity.this);
                      mRecyclerView.setAdapter(mAdapter);
                      TOTAL_CAR_COUNT = cars.getCars().size();
                      mCarCount.setText(getString(R.string.car_count) + TOTAL_CAR_COUNT);
                  }
              }

              @Override
              public void failure(RetrofitError error) {
                  mProgressBar.setVisibility(View.GONE);
                  if(refreshLayout.isRefreshing())
                      refreshLayout.setRefreshing(false);
                  Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
              }
          });
        }
        else
            Toast.makeText(this, R.string.internet_error,Toast.LENGTH_LONG).show();
    }

    private void getNoOfApiHits()
    {
        if(Util.isNetworkConnected(this)) {
            ZoomCarApiClient.getCarsApi().getApiCounter(new Callback<CarApiHitModel>() {
                @Override
                public void success(CarApiHitModel carStoreApiHitModel, Response response) {
                    if (response.getStatus() == 200)
                        mApiCount.setText(getString(R.string.api_count) + carStoreApiHitModel.getApiHits());
                }

                @Override
                public void failure(RetrofitError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onItemClick(View v, int position) {
        CarDetailsModel.Car model =  carList.get(position);
        EventBus bus = EventBus.getDefault();
        bus.postSticky(model);
        startActivity(new Intent(MainActivity.this, CarDetailActivity.class));
    }


    private void sortList(Comparator<CarDetailsModel.Car> comparator)
    {
        Collections.sort(carList, comparator);
        mAdapter = new CarListAdapter(this, carList);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }
}
