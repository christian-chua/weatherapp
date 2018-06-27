package com.myapp.chua_weather_app;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by User on 27/06/2018.
 */

public class FragmentWeather extends Fragment {
    View myView;
    private Context context;

    public static TextView textMain, textWind, textLocation, textWeather, textTemperature,  textPressure, textHumidity, textMinTemp, textMaxTemp, textSpeed, textDegree;
    static ImageView weatherIcon;

    public static String location, weather, temperature, pressure, humidity, mintemp, maxtemp, speed, degree, icon;


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.layout_fragment, container, false);
        context = getActivity();

        textMain = (TextView) myView.findViewById(R.id.txtMain);
        textWind = (TextView) myView.findViewById(R.id.txtWind);

        textLocation = (TextView) myView.findViewById(R.id.txtCity);
        textWeather = (TextView) myView.findViewById(R.id.txtWeather);
        textTemperature = (TextView) myView.findViewById(R.id.txtTemperature);
        textPressure = (TextView) myView.findViewById(R.id.txtPressure);
        textHumidity = (TextView) myView.findViewById(R.id.txtHumidity);
        textMinTemp = (TextView) myView.findViewById(R.id.txtMinTemp);
        textMaxTemp = (TextView) myView.findViewById(R.id.txtMaxTemp);
        textSpeed = (TextView) myView.findViewById(R.id.txtSpeed);
        textDegree = (TextView) myView.findViewById(R.id.txtDegree);

        weatherIcon = (ImageView) myView.findViewById(R.id.weatherIcon);

        textLocation.setText(location);
        textWeather.setText(weather);
        textTemperature.setText(temperature);
        textPressure.setText(pressure);
        textHumidity.setText(humidity);
        textMinTemp.setText(mintemp);
        textMaxTemp.setText(maxtemp);
        textSpeed.setText(speed);
        textDegree.setText(degree);

        Glide.with(context)
                .asBitmap()
                .load(icon)
                .into(weatherIcon);

        return myView;


    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {


        location = null;
        weather = null;
        temperature = null;
        pressure = null;
        humidity = null;
        mintemp = null;
        maxtemp = null;
        speed = null;
        degree = null;


        super.onDestroy();
    }
}
