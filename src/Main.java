

import com.fasterxml.jackson.databind.ObjectMapper;
import reddit.RedditApi;
import smhi.Forecasts;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


import smhi.SMHIWeatherAPI;


public class Main {
    public static final String USER_AGENT = "Chrome";


    public static void main(String[] args) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            URLConnection connection = (new URL("https://www.reddit.com/hot.json")).openConnection();

            Thread.sleep(2000); //Delay to comply with rate limiting
            connection.setRequestProperty("User-Agent", USER_AGENT);

            InputStream in = connection.getInputStream();

            RedditApi reddit = mapper.readValue(in, RedditApi.class);
            System.out.println(reddit.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SMHIWeatherAPI weatherAPI = new SMHIWeatherAPI("13.191", "55.704");
        Forecasts forecasts = weatherAPI.getForecasts();
        System.out.println(forecasts.toString());

    }
}
