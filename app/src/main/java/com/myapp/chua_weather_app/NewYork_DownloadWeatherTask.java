package com.myapp.chua_weather_app;

import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Chua on 25/06/2018.
 */
public class NewYork_DownloadWeatherTask extends AsyncTask<String, Void, String> {

    String weather_result;

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
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(weather_result);
            JSONObject curentWeatherData = new JSONObject(jsonObject.getString("main"));
            JSONObject curentWindData = new JSONObject(jsonObject.getString("wind"));
            String currentWeatherInfo = jsonObject.getString("weather");

            JSONArray arr = new JSONArray(currentWeatherInfo);
            for (int i = 0; i < arr.length(); i++) {

                JSONObject jsonPart = arr.getJSONObject(i);

                String main = "";
                String description = "";

                main = jsonPart.getString("main");
                description = jsonPart.getString("description");


                Home.textWeatherNY.setText(description);

            }


            Double temperature = Double.parseDouble(curentWeatherData.getString("temp"));
            Double pressure = Double.parseDouble(curentWeatherData.getString("pressure"));
            Double humidity = Double.parseDouble(curentWeatherData.getString("humidity"));
            Double mintemp =  Double.parseDouble(curentWeatherData.getString("temp_min"));
            Double maxtemp =  Double.parseDouble(curentWeatherData.getString("temp_max"));
            Double speed =  Double.parseDouble(curentWindData.getString("speed"));
            Double degree =  Double.parseDouble(curentWindData.getString("deg"));


            int temperatureInt = (int) (temperature - 273.15); //convert double temp to int then convert Kelvin to Celsius
            int mintempInt = (int) (mintemp - 273.15); //convert double temp to int then convert Kelvin to Celsius
            int maxtempInt = (int) (maxtemp - 273.15); //convert double temp to int then convert Kelvin to Celsius


            String place = jsonObject.getString("name");

            Home.textTemperatureNY.setText(String.valueOf(temperatureInt) + " °C");
            Home.textLocationNY.setText(place);
            Home.textPressureNY.setText("Pressure: "+String.valueOf(pressure));
            Home.textHumidityNY.setText("Humidity: "+String.valueOf(humidity));
            Home.textMinTempNY.setText("Min Temperature: "+String.valueOf(mintempInt)+"°C");
            Home.textMaxTempNY.setText("Max Temperature: "+String.valueOf(maxtempInt)+"°C");
            Home.textSpeedNY.setText("Speed: "+String.valueOf(speed));
            Home.textDegreeNY.setText("Degree: "+String.valueOf(degree));


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
