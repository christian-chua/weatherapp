package com.myapp.chua_weather_app;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.content.ContentValues.TAG;

/**
 * Created by Chua on 25/06/2018.
 */

public class AsyncWeatherTask extends AsyncTask<String, Void, String> {

    String weather_result;
    private Context mContext;

    public AsyncWeatherTask(Context context) {
        mContext = context;
    }

    @Override
    protected String doInBackground(String... weatherURL) {

        weather_result = "";
        HttpURLConnection urlConnection = null;
        URL url;


        try {
            url = new URL(weatherURL[0]);
            urlConnection = (HttpURLConnection) url.openConnection();

            InputStream inputStream = urlConnection.getInputStream();
            InputStreamReader inputReader = new InputStreamReader(inputStream);

            int weatherData = inputReader.read();

            while (weatherData != -1) {
                char currentWeather = (char) weatherData;
                weather_result += currentWeather;
                weatherData = inputReader.read();


            }

            return weather_result;

        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);



    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(weather_result);
            JSONObject curentWeatherData = new JSONObject(jsonObject.getString("main"));
            JSONObject curentWindData = new JSONObject(jsonObject.getString("wind"));
            String currentWeatherInfo = jsonObject.getString("weather");

            String place = jsonObject.getString("name");

            JSONArray arr = new JSONArray(currentWeatherInfo);
            for (int i = 0; i < arr.length(); i++) {

                JSONObject jsonPart = arr.getJSONObject(i);


                String description = "";
                String icon = "";

                description = jsonPart.getString("description");
                icon = jsonPart.getString("icon");
                String iconUrl = "http://openweathermap.org/img/w/" + icon + ".png";

                Glide.with(mContext)
                        .asBitmap()
                        .load(iconUrl)
                        .into(AdapterRecyclerView.weatherIcon);

                AdapterRecyclerView.textIconUri.setText(iconUrl);
                AdapterRecyclerView.textWeather.setText(description);

            }

            Double temperature = Double.parseDouble(curentWeatherData.getString("temp"));
            Double pressure = Double.parseDouble(curentWeatherData.getString("pressure"));
            Double humidity = Double.parseDouble(curentWeatherData.getString("humidity"));
            Double mintemp = Double.parseDouble(curentWeatherData.getString("temp_min"));
            Double maxtemp = Double.parseDouble(curentWeatherData.getString("temp_max"));
            Double speed = Double.parseDouble(curentWindData.getString("speed"));
            Double degree = Double.parseDouble(curentWindData.getString("deg"));


            int temperatureInt = (int) (temperature - 273.15); //convert double temp to int then convert Kelvin to Celsius
            int mintempInt = (int) (mintemp - 273.15); //convert double temp to int then convert Kelvin to Celsius
            int maxtempInt = (int) (maxtemp - 273.15); //convert double temp to int then convert Kelvin to Celsius

            AdapterRecyclerView.textTemperature.setText(String.valueOf(temperatureInt) + " °C");

            AdapterRecyclerView.textLocation.setText(place);

            AdapterRecyclerView.textPressure.setText("Pressure: " + String.valueOf(pressure));
            AdapterRecyclerView.textHumidity.setText("Humidity: " + String.valueOf(humidity));
            AdapterRecyclerView.textMinTemp.setText("Min Temperature: " + String.valueOf(mintempInt) + "°C");
            AdapterRecyclerView.textMaxTemp.setText("Max Temperature: " + String.valueOf(maxtempInt) + "°C");
            AdapterRecyclerView.textSpeed.setText("Speed: " + String.valueOf(speed));
            AdapterRecyclerView.textDegree.setText("Degree: " + String.valueOf(degree));

            Log.d(TAG, "onPostExecute: check "+place);


        } catch (Exception e) {
            e.printStackTrace();
        }

        this.cancel(true);
    }

}
