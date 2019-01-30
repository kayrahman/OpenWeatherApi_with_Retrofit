package neel.com.justoryweather.Utils;

public class WeatherUtils {

    public static final String TEMPERATURE_UNIT = "imperial";

    private static double FahrenheitTocelsius(float temperatureInFarenheit) {
        double temperatureInCelcius = (temperatureInFarenheit - 32) /1.8;
        return temperatureInCelcius;
    }


    public static String formatTemperature(float temp_farenheit){

        double temp_celcius = FahrenheitTocelsius(temp_farenheit);
        temp_celcius = Math.round(temp_celcius);
        return String.valueOf(temp_celcius);

    }

}
