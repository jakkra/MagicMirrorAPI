

import com.fasterxml.jackson.databind.ObjectMapper;
import reddit.RedditApi;
import reddit.RedditFlow;
import smhi.Forecasts;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


import smhi.SMHIWeatherAPI;


public class Main {

    public static void main(String[] args) {
        RedditFlow redditFlow = new RedditFlow("programming","hot");
        RedditApi rApi = redditFlow.getFlow();

        System.out.println(rApi.toString());

        SMHIWeatherAPI weatherAPI = new SMHIWeatherAPI("13.191", "55.704");
        Forecasts forecasts = weatherAPI.getForecasts();
        System.out.println(forecasts.toString());

    }
}
