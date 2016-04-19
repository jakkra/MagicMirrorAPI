import smhi.Forecasts;
import smhi.SMHIWeatherAPI;

public class Main {

    public static void main(String[] args) {
        SMHIWeatherAPI weatherAPI = new SMHIWeatherAPI("13.191", "55.704");
        Forecasts forecasts = weatherAPI.getForecasts();
        System.out.println(forecasts.toString());
    }
}
