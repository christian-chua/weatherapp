package com.myapp.chua_weather_app;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

/**
 * Created by Chua on 27/06/2018.
 */

public class AdapterRecyclerView extends RecyclerView.Adapter<AdapterRecyclerView.ViewHolder> implements View.OnClickListener {

    private static final String TAG = "RecyclerViewAdapter";

    public static TextView textMain, textWind, textLocation, textWeather, textTemperature, textPressure, textHumidity, textMinTemp, textMaxTemp, textSpeed, textDegree, textIconUri;

    static ImageView weatherIcon;
    public static String place;

    public static String temp;
    public String weather;

    public ArrayList<String> mLocation = new ArrayList<>();
    private ArrayList<String> mTemperature = new ArrayList<>();

    public AdapterRecyclerView(Context context, ArrayList<String> mLocation) {
        this.mLocation = mLocation;
        this.mTemperature = mTemperature;
  /*      this.mWeather = mWeather;

        this.mPressure = mPressure;
        this.mHumidity = mHumidity;
        this.mMinTemp = mMinTemp;
        this.mMaxTemp = mMaxTemp;
        this.mSpeed = mSpeed;
        this.mDegree = mDegree;
        this.mWeatherIcon = mWeatherIcon;*/
        this.context = context;
    }

    private Context context;

    @Override
    public void onClick(View view) {

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout scaffold;


        public ViewHolder(View itemView) {
            super(itemView);

            scaffold = (RelativeLayout) itemView.findViewById(R.id.scaffold);

            textMain = (TextView) itemView.findViewById(R.id.txtMain);
            textWind = (TextView) itemView.findViewById(R.id.txtWind);

            textLocation = (TextView) itemView.findViewById(R.id.txtCity);
            textWeather = (TextView) itemView.findViewById(R.id.txtWeather);
            textTemperature = (TextView) itemView.findViewById(R.id.txtTemperature);
            textPressure = (TextView) itemView.findViewById(R.id.txtPressure);
            textHumidity = (TextView) itemView.findViewById(R.id.txtHumidity);
            textMinTemp = (TextView) itemView.findViewById(R.id.txtMinTemp);
            textMaxTemp = (TextView) itemView.findViewById(R.id.txtMaxTemp);
            textSpeed = (TextView) itemView.findViewById(R.id.txtSpeed);
            textDegree = (TextView) itemView.findViewById(R.id.txtDegree);
            textIconUri = (TextView) itemView.findViewById(R.id.txtIconUri);
            weatherIcon = (ImageView) itemView.findViewById(R.id.weatherIcon);

            weatherIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                }
            });

        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listitem, parent, false);
        ViewHolder holder = new ViewHolder(view);

        holder.scaffold.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentWeather fw = new FragmentWeather();

                FragmentManager manager = ((Activity) context).getFragmentManager();
                manager.beginTransaction()
                        .replace(R.id.content_frame
                                , fw)
                        .addToBackStack("Stack")
                        .commit();


                fw.location = textLocation.getText().toString();
                fw.weather = textWeather.getText().toString();
                fw.temperature = textTemperature.getText().toString();
                fw.pressure = textPressure.getText().toString();
                fw.humidity = textHumidity.getText().toString();
                fw.mintemp = textMinTemp.getText().toString();
                fw.maxtemp = textMaxTemp.getText().toString();
                fw.speed = textSpeed.getText().toString();
                fw.degree = textDegree.getText().toString();
                fw.icon = textIconUri.getText().toString();


            }
        });

        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.d(TAG, "onBindViewHolder: called");

        String getCityWeatherData1 = "http://api.openweathermap.org/data/2.5/weather?q=" + mLocation.get(position) + "&APPID=1bafc94ff11a6fff9c79cbcc7d1a9955\n";
        AsyncWeatherTask major_cities_weatherTask = new AsyncWeatherTask(context);
        major_cities_weatherTask.execute(getCityWeatherData1);


        Log.d(TAG, "onBindViewHolder: " + mLocation.get(position));

    }

    @Override
    public int getItemCount() {
        Log.d(TAG, "getItemCount: " + mLocation.size());

        return mLocation.size();

    }


}
