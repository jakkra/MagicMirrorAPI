package smhi;/*
 * Created by krantz on 16-04-19.
 */

import java.util.ArrayList;

public class Forecasts {
    public ArrayList<HourlyForecast> hourlyForecasts;
    public String approvedTime;
    public String referenceTime;

    public Forecasts(){
        this.hourlyForecasts = new ArrayList<>();
    }

    public void addHourlyForecast(HourlyForecast hf) {
        hourlyForecasts.add(hf);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(HourlyForecast h : hourlyForecasts){
            sb.append("\n" + h.toString());
        }
        return "Forecast: \n" +
                "ApprovedTime: " + approvedTime + "\n" +
                "referenceTime: " + referenceTime + "\n" +
                sb.toString();
    }
}
