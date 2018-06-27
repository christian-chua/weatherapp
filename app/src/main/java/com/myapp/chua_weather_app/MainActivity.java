package com.myapp.chua_weather_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity implements LocationListener {

    static int x = 1;

    private static final String TAG = "MainActivity";
    private android.app.FragmentManager fragmentManager;
    RecyclerView recyclerView;
    private ArrayList<String> mCity = new ArrayList<>();
    private ArrayList<String> mIcon = new ArrayList<>();
    public ArrayList<String> mTemp = new ArrayList<>();

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private static final String TIME_PATTERN = "HH:mm";

    Intent myIntent;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    static TextView textDate, textDay;
    static ImageButton buttonRefresh;

    static TextView textMain, textWind, textLocation, textWeather, textTemperature, textLatitude, textLongitude, textPressure, textHumidity, textMinTemp, textMaxTemp, textSpeed, textDegree, textIconUri;
    static ImageView icon;

    LinearLayout llCurrentLocation;
    AdapterRecyclerView adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(TAG, "onCreate: start");

        try {
            getSupportActionBar().setTitle("Weather App");


        } catch (Exception e) {

        }

        calendar = Calendar.getInstance();
        dateFormat = DateFormat.getDateInstance(DateFormat.LONG, Locale.getDefault());
        timeFormat = new SimpleDateFormat(TIME_PATTERN, Locale.getDefault());

        /*==FOR DATE and TIME*/

        Date dt = new Date();
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        dt = calendar.getTime();

        textDate = (TextView) findViewById(R.id.txtDate);
        textDay = (TextView) findViewById(R.id.txtDay);
        buttonRefresh = (ImageButton) findViewById(R.id.BtnRefresh);

        String currentDateTimeString = dateFormat.getDateInstance().format(new Date());
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()); //Gets the next day (should get the day today)
        textDate.setText(currentDateTimeString);
        textDay.setText(dayLongName);

        /*FOR DATE and TIME==*/

        llCurrentLocation = (LinearLayout) findViewById(R.id.LLCurrentLocation);


        textMain = (TextView) findViewById(R.id.txtMain);
        textWind = (TextView) findViewById(R.id.txtWind);
        textLocation = (TextView) findViewById(R.id.txtLocation);
        textWeather = (TextView) findViewById(R.id.txtWeather);
        textTemperature = (TextView) findViewById(R.id.txtTemperature);
        textLatitude = (TextView) findViewById(R.id.txtLatitude);
        textLongitude = (TextView) findViewById(R.id.txtLongitude);
        textPressure = (TextView) findViewById(R.id.txtPressure);
        textHumidity = (TextView) findViewById(R.id.txtHumidity);
        textMinTemp = (TextView) findViewById(R.id.txtMinTemp);
        textMaxTemp = (TextView) findViewById(R.id.txtMaxTemp);
        textSpeed = (TextView) findViewById(R.id.txtSpeed);
        textDegree = (TextView) findViewById(R.id.txtDegree);
        textIconUri = (TextView) findViewById(R.id.txtIconUri);
        icon = (ImageView) findViewById(R.id.weatherIcon);

        myIntent = getIntent();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locate();
            refresh();

        } else {

            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {


            } else {
                // Request the permission
                int requestLocation1 = 0;
                int requestLocation2 = 0;

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        requestLocation1);

                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        requestLocation2);

                try {
                    if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        buttonRefresh.setEnabled(true);
                        locate();
                        refresh();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            //return;
        }
        //Toast.makeText(this, "TEST "+latitude+" "+longitude, Toast.LENGTH_LONG).show();

        llCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentWeather fw = new FragmentWeather();

                fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame
                                , new FragmentWeather())
                        .addToBackStack("Stack")
                        .commit();

                FragmentWeather.location = textLocation.getText().toString();
                FragmentWeather.weather = textWeather.getText().toString();
                FragmentWeather.temperature = textTemperature.getText().toString();
                FragmentWeather.pressure = textPressure.getText().toString();
                FragmentWeather.humidity = textHumidity.getText().toString();
                FragmentWeather.mintemp = textMinTemp.getText().toString();
                FragmentWeather.maxtemp = textMaxTemp.getText().toString();
                FragmentWeather.speed = textSpeed.getText().toString();
                FragmentWeather.degree = textDegree.getText().toString();
                FragmentWeather.icon = textIconUri.getText().toString();


            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    locate();
                    recyclerView.getRecycledViewPool().clear();
                    mCity.clear();
                    refresh();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        initImage();

    }//oncreate

    @SuppressLint("MissingPermission")
    private void locate() {
        if (isLocationEnabled(MainActivity.this)) {
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
            criteria = new Criteria();
            bestProvider = String.valueOf(locationManager.getBestProvider(criteria, true)).toString();

            @SuppressLint("MissingPermission") Location location = locationManager.getLastKnownLocation(bestProvider);
            if (location != null) {
                Log.e("TAG", "GPS is on");
                latitude = location.getLatitude();
                longitude = location.getLongitude();

                // Toast.makeText(Home.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
            } else {

                locationManager.requestLocationUpdates(bestProvider, 1000, 0, this);
            }
        } else {


        }
    }

    private void initImage() {
        Log.d(TAG, "initImage: getting images");

        mCity.add("Cebu,PH");
        mCity.add("Teresa,PH");
        mCity.add("London,uk");
        mCity.add("Seoul,kr");
        mCity.add("New York,us");
        mCity.add("Tokyo,jp");

        initRecyclerView();

    }

    private void initRecyclerView() {
        Log.d(TAG, "initRecyclerView: recyclerview");

        recyclerView = findViewById(R.id.recyclerView);
        adapter = new AdapterRecyclerView(this, mCity);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.notifyDataSetChanged();

    }

    @Override
    public void onBackPressed() {


        int count = getFragmentManager().getBackStackEntryCount();

        if (count == 0) {
            super.onBackPressed();
        } else {
            getFragmentManager().popBackStack();
        }

    }

    private void refresh() {

        //Toast.makeText(this, "Lat: " + latitude + " Long" + longitude, Toast.LENGTH_SHORT).show();

        String getGeoWeatherData = "http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) + "&APPID=1bafc94ff11a6fff9c79cbcc7d1a9955\n";
        DownloadWeatherTask weatherTask = new DownloadWeatherTask(this);
        weatherTask.execute(getGeoWeatherData);

        initImage();


    }

    public static boolean isLocationEnabled(Context context) {

        return true;
    }


    @Override
    public void onLocationChanged(Location location) {
        locationManager.removeUpdates(this);
        latitude = location.getLatitude();
        longitude = location.getLongitude();
        // Toast.makeText(Home.this, "latitude:" + latitude + " longitude:" + longitude, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }


}
