import reddit.Post;
import reddit.RedditFlow;
import skanetrafikenAPI.Journey;
import skanetrafikenAPI.SkanetrafikenAPI;
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

        SkanetrafikenAPI skanetrafikenAPI = new SkanetrafikenAPI();
        ArrayList<Journey> journeys = skanetrafikenAPI.getJourneys("Värnhem", "Lund LTH", 5);
        System.out.println(journeys.toString());

        NewsMapper newsMapper = new NewsMapper();
        NewsAPI nAPI = newsMapper.getNews();
        System.out.println(nAPI.toString());
    }
}
