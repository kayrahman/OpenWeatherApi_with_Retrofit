package neel.com.justoryweather;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.HttpURLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import neel.com.justoryweather.api.Service;
import neel.com.justoryweather.model.Weather;
import neel.com.justoryweather.model.WeatherMain;
import neel.com.justoryweather.model.WeatherResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static neel.com.justoryweather.Utils.WeatherUtils.TEMPERATURE_UNIT;
import static neel.com.justoryweather.Utils.WeatherUtils.formatTemperature;


public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();
    public static final String API_KEY = "c8a26005e0fab454ab1a312787ae2043";
    public static final int ERROR_LOCATION_NOT_FOUND = 404;

    private TextView mTempTv,mHumidityTv,mPressureTv,mWindTv,mLocationTv,mWeatherStatusTv,mDateTv,mLocationNotFoundTv;
    private ImageView mWeatherIconIv;
    private LinearLayout mMainLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*initializing views*/
        initViews();


        /*set default location Malaysia*/
        fetchWeatherInfoData("Malaysia");

    }

    private void initViews() {

        mTempTv=findViewById(R.id.tv_ac_main_temp);
        mHumidityTv=findViewById(R.id.tv_ac_main_humidity);
        mPressureTv=findViewById(R.id.tv_ac_main_pressure);
        mWindTv=findViewById(R.id.tv_ac_main_wind);
        mLocationTv=findViewById(R.id.tv_ac_main_location);
        mWeatherIconIv = findViewById(R.id.iv_ac_main_weather_icon);
        mWeatherStatusTv = findViewById(R.id.tv_weather_status);
        mDateTv = findViewById(R.id.tv_ac_main_date);

        mMainLinearLayout = findViewById(R.id.ll_ac_main);
        mLocationNotFoundTv = findViewById(R.id.tv_ac_main_location_not_found);
        mLocationNotFoundTv.setVisibility(View.GONE);

    }


    private void fetchWeatherInfoData(String query){


        Service.getApi().getWeatherInfoList(query,API_KEY,TEMPERATURE_UNIT)
                .enqueue(new Callback<WeatherResponse>() {
                    @Override
                    public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {


                        try {

                            if(response.code() == HttpURLConnection.HTTP_OK) {

                                Log.i(TAG,"ON_RESPONSE"+ response.body().toString());
                                Log.i(TAG,"ON_RESPONSE"+ "HTTP_OK");

                                WeatherResponse weatherResponse = response.body();

                                /*populate the views when data is fetched*/
                                populateViewsWithWeatherData(weatherResponse);


                            }else if(response.code() == ERROR_LOCATION_NOT_FOUND){
                                updateUiIfLocationNotFound();
                                Log.i(TAG,"ON_RESPONSE "+ "location not found");

                            }

                        }catch (NullPointerException e){

                            Log.i(TAG,"ON_RESPONSE: NullpointerException"+ e.getMessage());

                        }catch (IndexOutOfBoundsException e){

                            Log.i(TAG,"ON_RESPONSE: IndexOutOfBoundException"+ e.getMessage());

                        }


                    }

                    @Override
                    public void onFailure(Call<WeatherResponse> call, Throwable t) {

                        Log.i("ON_RESPONSE","Failed");

                    }
                });


    }

    private void updateUiIfLocationNotFound() {
        mMainLinearLayout.setVisibility(View.GONE);
        mLocationNotFoundTv.setVisibility(View.VISIBLE);


    }

    private void populateViewsWithWeatherData(WeatherResponse weatherResponse) {

        if(mLocationNotFoundTv.getVisibility() == View.VISIBLE){
            mLocationNotFoundTv.setVisibility(View.GONE);

        }
        if(mMainLinearLayout.getVisibility()==View.GONE){
            mMainLinearLayout.setVisibility(View.VISIBLE);
        }


        WeatherMain main =weatherResponse.getWeatherMain();
        ArrayList<Weather> weatherArrayList = weatherResponse.getWeatherList();

        String location_name =weatherResponse.getLocationName();
        String temp = formatTemperature(main.getTemp());
        String pressure = String.valueOf(main.getPressure());
        String humidity = String.valueOf(main.getHumidity());
        String temp_min = String.valueOf(main.getTemp_min());

        mTempTv.setText(temp+"\u00b0"+"C");
        mPressureTv.setText(pressure+" hPa");
        mHumidityTv.setText(humidity+"%");
        mWindTv.setText(String.valueOf(weatherResponse.getWind().speed)+" km/h");
        mLocationTv.setText(location_name);
        mDateTv.setText(getCurrentDate());

        if(weatherArrayList.size()>0) {
            mWeatherStatusTv.setText(weatherArrayList.get(0).getMain());

            String icon_id = weatherArrayList.get(0).getIcon();

            Picasso.with(this)
                    .load("http://openweathermap.org/img/w/"+icon_id+".png")
                    .into(mWeatherIconIv);

        }





    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu,menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);
        SearchView.SearchAutoComplete searchAutoComplete = (SearchView.SearchAutoComplete)searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
        searchAutoComplete.setHint(R.string.hint);



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String location) {

                fetchWeatherInfoData(location);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });



        return true;

    }


    public String getCurrentDate(){

        Date today = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateToStr = format.format(today);

        return dateToStr;

    }



}
