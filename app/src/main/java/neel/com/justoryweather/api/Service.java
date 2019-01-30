package neel.com.justoryweather.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static neel.com.justoryweather.api.OpenWeatherApi.OPEN_WEATHER_BASE_URL;


public class Service {

    public static OpenWeatherApi.Auth getApi(){

        return new Retrofit.Builder()
                .baseUrl(OPEN_WEATHER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(OpenWeatherApi.Auth.class);

    }
}
