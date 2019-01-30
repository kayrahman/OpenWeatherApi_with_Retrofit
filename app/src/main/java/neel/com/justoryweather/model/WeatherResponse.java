package neel.com.justoryweather.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class WeatherResponse {

   @SerializedName("name")
   @Expose
   private String mLocationName;

   @SerializedName("main")
   @Expose
   private WeatherMain mWeatherMain;

   @SerializedName("weather")
   @Expose
   private ArrayList<Weather> mWeatherList=new ArrayList<Weather>();

   @SerializedName("wind")
   @Expose
   private Wind mWind;

   public WeatherResponse(String locationName, WeatherMain weatherMain, ArrayList<Weather> weatherList, Wind wind) {
      mLocationName = locationName;
      mWeatherMain = weatherMain;
      mWeatherList = weatherList;
      mWind = wind;
   }

   public WeatherMain getWeatherMain() {
      return mWeatherMain;
   }

   public String getLocationName() {
      return mLocationName;
   }

   public ArrayList<Weather> getWeatherList() {
      return mWeatherList;
   }

   public Wind getWind() {
      return mWind;
   }

  public class Wind {
      @SerializedName("speed")
      public float speed;

   }
}
