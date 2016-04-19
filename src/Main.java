
import com.fasterxml.jackson.databind.ObjectMapper;
import smhi.Forecasts;

import java.io.IOException;
import java.net.URL;

public class Main {

    public static void main(String[] args) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Forecasts forecast = mapper.readValue(new URL("http://opendata-download-metfcst.smhi.se/api/category/pmp2g/version/2/geotype/point/lon/13.191/lat/55.704/data.json"), Forecasts.class);
            System.out.println(forecast.toString());
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
