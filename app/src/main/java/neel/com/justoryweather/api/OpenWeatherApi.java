package neel.com.justoryweather.api;

import neel.com.justoryweather.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OpenWeatherApi {

  public static final String OPEN_WEATHER_BASE_URL ="https://api.openweathermap.org";





    interface Auth{

        ///weather?q={city name}";
        @GET("/data/2.5/weather?")
        Call<WeatherResponse>getWeatherInfoList(@Query("q") String cityname,
                                                @Query("APPID")String api_key,
                                                @Query("units")String units);


    }

}
