package com.test.simpleweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import com.test.simpleweather.injection.DaggerViewModelComponent;
import com.test.simpleweather.injection.ViewModelComponent;
import com.test.simpleweather.injection.ViewModelModule;
import com.test.simpleweather.ui.CityParcel;
import com.test.simpleweather.ui.WeatherFragment;
import com.test.simpleweatherapi.model.City;
import com.test.simpleweatherlogic.CityViewModel;
import com.test.simpleweatherlogic.ResultObservable;
import com.test.simpleweatherlogic.ResultObserver;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {

    private static boolean KEYWORD_SEARCH = true;

    private static final String KEYWORD = "auckland";

    @Inject
    CityViewModel cityViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        ViewModelComponent viewModelComponent = DaggerViewModelComponent.builder()
                .viewModelModule(new ViewModelModule(this)).build();
        viewModelComponent.inject(this);

        if (KEYWORD_SEARCH) {
            ResultObservable<List<City>> cityList = cityViewModel.fetchCity(KEYWORD);
            cityList.setResultObserver(new ResultObserver<List<City>>() {
                @Override
                public void notifyUpdate(List<City> cities) {
                    SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                            cities);
                    ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                    viewPager.setAdapter(sectionsPagerAdapter);
                }
            });
        } else {
            if (locationPermissionGranted()) {
                Location location = getCurrentLocation();
                if (location != null) {
                    ResultObservable<List<City>> cityList = cityViewModel.fetchCity(location.getLatitude(), location.getLongitude());
                    cityList.setResultObserver(new ResultObserver<List<City>>() {
                        @Override
                        public void notifyUpdate(List<City> cities) {
                            SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),
                                    cities);
                            ViewPager viewPager = (ViewPager) findViewById(R.id.container);
                            viewPager.setAdapter(sectionsPagerAdapter);
                        }
                    });
                }
            } else {
                Toast.makeText(this, R.string.access_gps_permission_denied, Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private List<WeatherFragment> weatherFragmentList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm, List<City> cityList) {
            super(fm);
            for (City city : cityList) {
                WeatherFragment weatherFragment = new WeatherFragment();
                Bundle bundle = new Bundle();
                CityParcel cityParcel = new CityParcel(city.getId(), city.getName(), city.getLat(), city.getLon(), city.getCounty());
                bundle.putParcelable(WeatherFragment.CITY, cityParcel);
                weatherFragment.setArguments(bundle);
                weatherFragmentList.add(weatherFragment);
            }
        }

        @Override
        public Fragment getItem(int position) {
            return weatherFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return weatherFragmentList.size();
        }
    }

    private Location getCurrentLocation() {
        LocationManager locationManager = (LocationManager) this.getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return null;
        }
        Location gps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location network = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (gps != null && network != null) {
            if (gps.getTime() > network.getTime()) {
                return gps;
            } else {
                return network;
            }
        } else {
            if (gps != null) {
                return gps;
            }
            if (network != null) {
                return network;
            }
        }
        return null;
    }

    private boolean locationPermissionGranted() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        boolean granted = false;
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                granted = true;
            } else {
                Toast.makeText(this, R.string.access_gps_permission_denied, Toast.LENGTH_LONG).show();
            }
        } else {
            granted = true;
        }
        return granted;
    }
}
