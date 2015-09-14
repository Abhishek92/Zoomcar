package com.android.zoomcar;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import com.android.zoomcar.api.CarDetailsModel;
import com.android.zoomcar.fragment.BookingDetailFragment;
import com.android.zoomcar.fragment.CarDetailFragment;
import com.android.zoomcar.fragment.CarLocationFragment;

import de.greenrobot.event.EventBus;

public class CarDetailActivity extends AppCompatActivity implements
        ShareActionProvider.OnShareTargetSelectedListener {

    private TabLayout mTablayout;
    private Toolbar mToolbar;
    private CarDetailsModel.Car carDetailModel;
    private ShareActionProvider mShareActionProvider;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        carDetailModel = EventBus.getDefault().removeStickyEvent(CarDetailsModel.Car.class);
        initToolBar();
        initTabLayout();

    }

    private void initToolBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getString(R.string.car_detail_title));
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    private void initTabLayout() {
        mTablayout = (TabLayout) findViewById(R.id.tabLayout);
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.car_detail_tab)));
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.location_tab)));
        mTablayout.addTab(mTablayout.newTab().setText(getString(R.string.booking_detail_tab)));

       setCarDetailFragment();

        mTablayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0) {
                    setCarDetailFragment();
                } else if (tab.getPosition() == 1) {
                    setLocationFragment();
                }else if (tab.getPosition() == 2) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, new BookingDetailFragment()).commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    getSupportFragmentManager().beginTransaction().remove(new CarDetailFragment()).commit();
                else if (tab.getPosition() == 1)
                    getSupportFragmentManager().beginTransaction().remove(new CarLocationFragment()).commit();
                else if (tab.getPosition() == 2)
                    getSupportFragmentManager().beginTransaction().remove(new BookingDetailFragment()).commit();

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition() == 0)
                    getSupportFragmentManager().beginTransaction().remove(new CarDetailFragment()).commit();
                else if (tab.getPosition() == 1)
                    getSupportFragmentManager().beginTransaction().remove(new CarLocationFragment()).commit();
                else if (tab.getPosition() == 2)
                    getSupportFragmentManager().beginTransaction().remove(new BookingDetailFragment()).commit();
            }
        });
    }

    private void setCarDetailFragment()
    {
        CarDetailFragment fragment = new CarDetailFragment();
        EventBus.getDefault().postSticky(carDetailModel);
        getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, fragment).commit();
    }

    private void setLocationFragment()
    {
        CarLocationFragment carLocationFragment = new CarLocationFragment();
        Bundle bundle = new Bundle();
        bundle.putDouble("latitude", Double.parseDouble(carDetailModel.getLocation().getLatitude()));
        bundle.putDouble("longitude", Double.parseDouble(carDetailModel.getLocation().getLongitude()));
        bundle.putString("name", carDetailModel.getName());
        carLocationFragment.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.viewPager, carLocationFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_car_detail, menu);
        // Locate MenuItem with ShareActionProvider
        MenuItem item = (MenuItem) menu.findItem(R.id.menu_item_share);

        // Fetch and store ShareActionProvider
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(item);
        mShareActionProvider.setShareIntent(getDefaultShareIntent());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_send_sms) {
            sendDetailsThroughSms();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private Intent getDefaultShareIntent(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,getSharingText());
        return intent;
    }

    private void sendDetailsThroughSms()
    {
        Uri uri = Uri.parse("smsto:" + "");
        Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
        intent.putExtra("sms_body", getSharingText());
        startActivity(intent);
    }

    private String getSharingText()
    {
        String ac = carDetailModel.getAc().equals("1") ? "Yes" : "No";
        String text = "Hey Checkout this car details from ZoomCar"+"\n"+"Car Name: "+carDetailModel.getName()+"\n"
                +"Car Rating: "+carDetailModel.getRating()+"\n"
                +"Car Type: "+carDetailModel.getType()+"\n"
                +"Car hourly rate: "+carDetailModel.getHourlyRate()+"\n"
                +"Total Seats: "+carDetailModel.getSeater()+"\n"
                +"AC: "+ac+"\n"
                +"Car's location Longitude: "+carDetailModel.getLocation().getLongitude()+" "+"Latitude: "+carDetailModel
                .getLocation().getLatitude();

        return text;
    }

    @Override
    public boolean onShareTargetSelected(ShareActionProvider source, Intent intent) {
        return false;
    }
}
