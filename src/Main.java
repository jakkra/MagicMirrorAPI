
import com.fasterxml.jackson.databind.ObjectMapper;
import reddit.RedditApi;
import smhi.Forecasts;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static final String USER_AGENT = "Chrome";


    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();

        try {
            URLConnection connection = (new URL("https://www.reddit.com/hot.json")).openConnection();

            Thread.sleep(2000); //Delay to comply with rate limiting
            connection.setRequestProperty("User-Agent", USER_AGENT);

            InputStream in = connection.getInputStream();
            Forecasts forecast = mapper.readValue(new URL("http://opendata-download-metfcst.smhi.se/api/category/pmp2g/version/2/geotype/point/lon/13.191/lat/55.704/data.json"), Forecasts.class);

            RedditApi reddit = mapper.readValue(in, RedditApi.class);
            System.out.println(reddit.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
