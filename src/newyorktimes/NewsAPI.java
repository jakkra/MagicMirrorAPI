package newyorktimes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.net.URL;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsAPI {
	private News nAPI;
	private String sec;

	public NewsAPI(String sec){
		this.sec = sec;
	}

	public News getNews(){
		ObjectMapper mapper = new ObjectMapper();
		URLConnection connection = null;

		try{
			connection = (new URL("http://api.nytimes.com/svc/mostpopular/v2/mostviewed/" + sec + "/7.json?api-key=568c91f5cbf0e0d9f500275f7d869ac7:1:75121196")).openConnection();
			InputStream in = connection.getInputStream();

			nAPI = mapper.readValue(in, News.class);

		} catch (IOException e){
			e.printStackTrace();
		}

		return nAPI;
	}
}