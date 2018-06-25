package com.myapp.chua_weather_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Home extends AppCompatActivity implements LocationListener {

    private Calendar calendar;
    private DateFormat dateFormat;
    private SimpleDateFormat timeFormat;
    private static final String TIME_PATTERN = "HH:mm";
    private String currentDateTimeString2;

    Intent myIntent;
    public double latitude;
    public double longitude;
    public LocationManager locationManager;
    public Criteria criteria;
    public String bestProvider;

    static TextView textDate, textDay;
    static ImageButton buttonRefresh;

    static TextView textMain, textWind, textLocation, textWeather, textTemperature, textLatitude, textLongitude, textPressure, textHumidity, textMinTemp, textMaxTemp, textSpeed, textDegree;

    static TextView textMainLondon, textWindLondon, textLocationLondon, textWeatherLondon, textTemperatureLondon, textPressureLondon, textHumidityLondon, textMinTempLondon, textMaxTempLondon, textSpeedLondon, textDegreeLondon;

    static TextView textMainSeoul, textWindSeoul, textLocationSeoul, textWeatherSeoul, textTemperatureSeoul, textPressureSeoul, textHumiditySeoul, textMinTempSeoul, textMaxTempSeoul, textSpeedSeoul, textDegreeSeoul;

    static TextView textMainNY, textWindNY, textLocationNY, textWeatherNY, textTemperatureNY, textPressureNY, textHumidityNY, textMinTempNY, textMaxTempNY, textSpeedNY, textDegreeNY;

    static TextView textMainTokyo, textWindTokyo, textLocationTokyo, textWeatherTokyo, textTemperatureTokyo, textPressureTokyo, textHumidityTokyo, textMinTempTokyo, textMaxTempTokyo, textSpeedTokyo, textDegreeTokyo;

    LinearLayout llCurrentLocation, llLondon, llSeoul, llNY, llTokyo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

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
        String dayLongName = calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault());
        textDate.setText(currentDateTimeString);
        textDay.setText(dayLongName);

        /*FOR DATE and TIME==*/

        llCurrentLocation = (LinearLayout) findViewById(R.id.LLCurrentLocation);
        llLondon = (LinearLayout) findViewById(R.id.LLLondon);
        llSeoul = (LinearLayout) findViewById(R.id.LLSeoul);
        llNY = (LinearLayout) findViewById(R.id.LLNY);
        llTokyo = (LinearLayout) findViewById(R.id.LLTokyo);

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

        textMainLondon = (TextView) findViewById(R.id.txtMainLondon);
        textWindLondon = (TextView) findViewById(R.id.txtWindLondon);
        textLocationLondon = (TextView) findViewById(R.id.txtLondon);
        textWeatherLondon = (TextView) findViewById(R.id.txtWeatherLondon);
        textTemperatureLondon = (TextView) findViewById(R.id.txtTemperatureLondon);
        textPressureLondon = (TextView) findViewById(R.id.txtPressureLondon);
        textHumidityLondon = (TextView) findViewById(R.id.txtHumidityLondon);
        textMinTempLondon = (TextView) findViewById(R.id.txtMinTempLondon);
        textMaxTempLondon = (TextView) findViewById(R.id.txtMaxTempLondon);
        textSpeedLondon = (TextView) findViewById(R.id.txtSpeedLondon);
        textDegreeLondon = (TextView) findViewById(R.id.txtDegreeLondon);


        textMainSeoul = (TextView) findViewById(R.id.txtMainSeoul);
        textWindSeoul = (TextView) findViewById(R.id.txtWindSeoul);
        textLocationSeoul = (TextView) findViewById(R.id.txtSeoul);
        textWeatherSeoul = (TextView) findViewById(R.id.txtWeatherSeoul);
        textTemperatureSeoul = (TextView) findViewById(R.id.txtTemperatureSeoul);
        textPressureSeoul = (TextView) findViewById(R.id.txtPressureSeoul);
        textHumiditySeoul = (TextView) findViewById(R.id.txtHumiditySeoul);
        textMinTempSeoul = (TextView) findViewById(R.id.txtMinTempSeoul);
        textMaxTempSeoul = (TextView) findViewById(R.id.txtMaxTempSeoul);
        textSpeedSeoul = (TextView) findViewById(R.id.txtSpeedSeoul);
        textDegreeSeoul = (TextView) findViewById(R.id.txtDegreeSeoul);

        textMainNY = (TextView) findViewById(R.id.txtMainNY);
        textWindNY = (TextView) findViewById(R.id.txtWindNY);
        textLocationNY = (TextView) findViewById(R.id.txtNY);
        textWeatherNY = (TextView) findViewById(R.id.txtWeatherNY);
        textTemperatureNY = (TextView) findViewById(R.id.txtTemperatureNY);
        textPressureNY = (TextView) findViewById(R.id.txtPressureNY);
        textHumidityNY = (TextView) findViewById(R.id.txtHumidityNY);
        textMinTempNY = (TextView) findViewById(R.id.txtMinTempNY);
        textMaxTempNY = (TextView) findViewById(R.id.txtMaxTempNY);
        textSpeedNY = (TextView) findViewById(R.id.txtSpeedNY);
        textDegreeNY = (TextView) findViewById(R.id.txtDegreeNY);

        textMainTokyo = (TextView) findViewById(R.id.txtMainTokyo);
        textWindTokyo = (TextView) findViewById(R.id.txtWindTokyo);
        textLocationTokyo = (TextView) findViewById(R.id.txtTokyo);
        textWeatherTokyo = (TextView) findViewById(R.id.txtWeatherTokyo);
        textTemperatureTokyo = (TextView) findViewById(R.id.txtTemperatureTokyo);
        textPressureTokyo = (TextView) findViewById(R.id.txtPressureTokyo);
        textHumidityTokyo = (TextView) findViewById(R.id.txtHumidityTokyo);
        textMinTempTokyo = (TextView) findViewById(R.id.txtMinTempTokyo);
        textMaxTempTokyo = (TextView) findViewById(R.id.txtMaxTempTokyo);
        textSpeedTokyo = (TextView) findViewById(R.id.txtSpeedTokyo);
        textDegreeTokyo = (TextView) findViewById(R.id.txtDegreeTokyo);

        myIntent = getIntent();


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locate();
            refresh();

        } else {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("Request for Location Settings");
            alertDialog.setCancelable(false);
            alertDialog.setMessage("Access to your device's location is required in order to use this app.\n");

            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(Home.this,
                            Manifest.permission.ACCESS_FINE_LOCATION) && ActivityCompat.shouldShowRequestPermissionRationale(Home.this,
                            Manifest.permission.ACCESS_COARSE_LOCATION)) {


                    } else {
                        // Request the permission
                        int requestLocation1 = 0;
                        int requestLocation2 = 0;

                        ActivityCompat.requestPermissions(Home.this,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                requestLocation1);

                        ActivityCompat.requestPermissions(Home.this,
                                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                requestLocation2);

                        try {
                            if (ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Home.this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                buttonRefresh.setEnabled(true);
                                locate();
                                refresh();

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                }
            });

            alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    buttonRefresh.setEnabled(false);
                    return;
                }
            });
            alertDialog.show();


            //return;
        }


        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               try{
                   locate();
                   refresh();
               }catch (Exception e){
                   e.printStackTrace();
               }

            }
        });

        //Toast.makeText(this, "TEST "+latitude+" "+longitude, Toast.LENGTH_LONG).show();

        llCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textPressure.getVisibility() == View.VISIBLE) {
                    textPressure.setVisibility(View.GONE);
                    textHumidity.setVisibility(View.GONE);
                    textMinTemp.setVisibility(View.GONE);
                    textMaxTemp.setVisibility(View.GONE);
                    textSpeed.setVisibility(View.GONE);
                    textDegree.setVisibility(View.GONE);
                } else {
                    textPressure.setVisibility(View.VISIBLE);
                    textHumidity.setVisibility(View.VISIBLE);
                    textMinTemp.setVisibility(View.VISIBLE);
                    textMaxTemp.setVisibility(View.VISIBLE);
                    textSpeed.setVisibility(View.VISIBLE);
                    textDegree.setVisibility(View.VISIBLE);
                }
            }
        });

        llLondon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textPressureLondon.getVisibility() == View.VISIBLE) {
                    textPressureLondon.setVisibility(View.GONE);
                    textHumidityLondon.setVisibility(View.GONE);
                    textMinTempLondon.setVisibility(View.GONE);
                    textMaxTempLondon.setVisibility(View.GONE);
                    textSpeedLondon.setVisibility(View.GONE);
                    textDegreeLondon.setVisibility(View.GONE);
                } else {
                    textPressureLondon.setVisibility(View.VISIBLE);
                    textHumidityLondon.setVisibility(View.VISIBLE);
                    textMinTempLondon.setVisibility(View.VISIBLE);
                    textMaxTempLondon.setVisibility(View.VISIBLE);
                    textSpeedLondon.setVisibility(View.VISIBLE);
                    textDegreeLondon.setVisibility(View.VISIBLE);
                }
            }
        });

        llSeoul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textPressureSeoul.getVisibility() == View.VISIBLE) {
                    textPressureSeoul.setVisibility(View.GONE);
                    textHumiditySeoul.setVisibility(View.GONE);
                    textMinTempSeoul.setVisibility(View.GONE);
                    textMaxTempSeoul.setVisibility(View.GONE);
                    textSpeedSeoul.setVisibility(View.GONE);
                    textDegreeSeoul.setVisibility(View.GONE);
                } else {
                    textPressureSeoul.setVisibility(View.VISIBLE);
                    textHumiditySeoul.setVisibility(View.VISIBLE);
                    textMinTempSeoul.setVisibility(View.VISIBLE);
                    textMaxTempSeoul.setVisibility(View.VISIBLE);
                    textSpeedSeoul.setVisibility(View.VISIBLE);
                    textDegreeSeoul.setVisibility(View.VISIBLE);
                }
            }
        });

        llNY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textPressureNY.getVisibility() == View.VISIBLE) {
                    textPressureNY.setVisibility(View.GONE);
                    textHumidityNY.setVisibility(View.GONE);
                    textMinTempNY.setVisibility(View.GONE);
                    textMaxTempNY.setVisibility(View.GONE);
                    textSpeedNY.setVisibility(View.GONE);
                    textDegreeNY.setVisibility(View.GONE);
                } else {
                    textPressureNY.setVisibility(View.VISIBLE);
                    textHumidityNY.setVisibility(View.VISIBLE);
                    textMinTempNY.setVisibility(View.VISIBLE);
                    textMaxTempNY.setVisibility(View.VISIBLE);
                    textSpeedNY.setVisibility(View.VISIBLE);
                    textDegreeNY.setVisibility(View.VISIBLE);
                }
            }
        });

        llTokyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (textPressureTokyo.getVisibility() == View.VISIBLE) {
                    textPressureTokyo.setVisibility(View.GONE);
                    textHumidityTokyo.setVisibility(View.GONE);
                    textMinTempTokyo.setVisibility(View.GONE);
                    textMaxTempTokyo.setVisibility(View.GONE);
                    textSpeedTokyo.setVisibility(View.GONE);
                    textDegreeTokyo.setVisibility(View.GONE);
                } else {
                    textPressureTokyo.setVisibility(View.VISIBLE);
                    textHumidityTokyo.setVisibility(View.VISIBLE);
                    textMinTempTokyo.setVisibility(View.VISIBLE);
                    textMaxTempTokyo.setVisibility(View.VISIBLE);
                    textSpeedTokyo.setVisibility(View.VISIBLE);
                    textDegreeTokyo.setVisibility(View.VISIBLE);
                }
            }
        });

    }//oncreate

    @SuppressLint("MissingPermission")
    private void locate() {
        if (isLocationEnabled(Home.this)) {
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

    private void refresh() {


        String getGeoWeatherData = "http://api.openweathermap.org/data/2.5/weather?lat=" + String.valueOf(latitude) + "&lon=" + String.valueOf(longitude) + "&APPID=1bafc94ff11a6fff9c79cbcc7d1a9955\n";
        DownloadWeatherTask weatherTask = new DownloadWeatherTask();
        weatherTask.execute(getGeoWeatherData);


        String getCityWeatherData1 = "http://api.openweathermap.org/data/2.5/weather?q=" + "London,uk" + "&APPID=1bafc94ff11a6fff9c79cbcc7d1a9955\n";
        London_DownloadWeatherTask major_cities_weatherTask = new London_DownloadWeatherTask();
        major_cities_weatherTask.execute(getCityWeatherData1);

        String getCityWeatherData2 = "http://api.openweathermap.org/data/2.5/weather?q=" + "Seoul,kr" + "&APPID=1bafc94ff11a6fff9c79cbcc7d1a9955\n";
        Seoul_DownloadWeatherTask seoul_weatherTask = new Seoul_DownloadWeatherTask();
        seoul_weatherTask.execute(getCityWeatherData2);

        String getCityWeatherData3 = "http://api.openweathermap.org/data/2.5/weather?q=" + "New York,us" + "&APPID=1bafc94ff11a6fff9c79cbcc7d1a9955\n";
        NewYork_DownloadWeatherTask ny_weatherTask = new NewYork_DownloadWeatherTask();
        ny_weatherTask.execute(getCityWeatherData3);

        String getCityWeatherData4 = "http://api.openweathermap.org/data/2.5/weather?q=" + "Tokyo,jp" + "&APPID=1bafc94ff11a6fff9c79cbcc7d1a9955\n";
        Tokyo_DownloadWeatherTask Tokyo_weatherTask = new Tokyo_DownloadWeatherTask();
        Tokyo_weatherTask.execute(getCityWeatherData4);
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
