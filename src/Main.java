import smhi.Forecasts;
import smhi.SMHIWeatherAPI;


public class Main {

    public static void main(String[] args) {
        /*RedditFlow redditFlow = new RedditFlow("programming", "hot");
        ArrayList<Post> posts = redditFlow.getFlow();
        System.out.println(posts);
*/
        SMHIWeatherAPI weatherAPI = new SMHIWeatherAPI("13.191", "55.704");
        Forecasts forecasts = weatherAPI.getForecasts();
        System.out.println(forecasts.toString());
/*
        SkanetrafikenAPI skanetrafikenAPI = new SkanetrafikenAPI();
        ArrayList<Journey> journeys = skanetrafikenAPI.getJourneys("Värnhem", "Lund LTH", 5);
        System.out.println(journeys.toString());

        NewsMapper newsMapper = new NewsMapper();
        NewsAPI news = newsMapper.getNews();
        System.out.println(news);*/
    }
}