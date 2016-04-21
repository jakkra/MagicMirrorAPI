import reddit.Post;
import reddit.RedditFlow;
import smhi.Forecasts;
import smhi.SMHIWeatherAPI;

import java.util.ArrayList;


public class Main {

    public static void main(String[] args) {
        RedditFlow redditFlow = new RedditFlow("programming", "hot");
        ArrayList<Post> posts = redditFlow.getFlow();
        System.out.println(posts);

        SMHIWeatherAPI weatherAPI = new SMHIWeatherAPI("13.191", "55.704");
        Forecasts forecasts = weatherAPI.getForecasts();
        System.out.println(forecasts.toString());

    }
}
