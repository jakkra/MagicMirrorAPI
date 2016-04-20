package reddit;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Kevin on 2016-04-20.
 */
public class RedditFlow {
    private String subReddit;
    private String sortAfter;
    private RedditApi rApi;
    public static final String USER_AGENT = "Chrome";

    public RedditFlow(String subReddit, String sortAfter) {
        this.subReddit = subReddit;
        this.sortAfter = sortAfter;
    }

    public RedditApi getFlow() {
        ObjectMapper mapper = new ObjectMapper();
        URLConnection connection = null;
        try {
            if (subReddit != "" ) {
                connection = (new URL("https://www.reddit.com/r/" + subReddit + "/" +sortAfter + ".json")).openConnection();
            }else {
                connection = (new URL("https://www.reddit.com/hot.json")).openConnection();
            }

            connection.setRequestProperty("User-Agent", USER_AGENT);

            InputStream in = connection.getInputStream();

            rApi = mapper.readValue(in, RedditApi.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return rApi;
    }
}
