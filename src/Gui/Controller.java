package Gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import newyorktimes.News;
import newyorktimes.NewsAPI;
import reddit.Post;
import reddit.RedditFlow;
import skanetrafikenAPI.Journey;
import skanetrafikenAPI.RouteLink;
import skanetrafikenAPI.SkanetrafikenAPI;
import skanetrafikenAPI.TimeAndDateConverter;
import smhi.Forecasts;
import smhi.HourlyForecast;
import smhi.SMHIWeatherAPI;
import smhi.WeatherConditionCodes;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Properties;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by kasper on 2016-05-12.
 */


public class Controller {
    private final SkanetrafikenAPI skanetrafikenAPI;
    private SMHIWeatherAPI weatherApi;
    private NewsAPI newsAPI;
    private RedditFlow redditFlow;
    private Properties prop;
    private InputStream is;

    @FXML
    Label weatherIconLabel;
    @FXML
    Label temperatureLabel;
    @FXML
    Label windLabel;
    @FXML
    Label rainForecastLabel;

    @FXML
    Label timeLabel;
    @FXML
    Label dateLabel;

    @FXML
    Label subredditLabel;
    @FXML
    Label newsLabel;

    @FXML
    Label busDepartureLabel;
    @FXML
    Label busDepartureTitle;

    @FXML
    ScrollPane redditScrollPane;
    @FXML
    ScrollPane newsScrollPane;

    @FXML
    VBox newsBox;

    @FXML
    VBox redditBox;


    public Controller() {
        try {
            prop = new Properties();
            String propFileName = "config.properties";
            is = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (is != null) {
                prop.load(is);
            } else {
                throw new FileNotFoundException("Filen kunde inte hittas");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.weatherApi = new SMHIWeatherAPI(prop.getProperty("lat"), prop.getProperty("longitude"));
        this.newsAPI = new NewsAPI(prop.getProperty("newsCat"));
        this.redditFlow = new RedditFlow(prop.getProperty("subreddit"), prop.getProperty("sort"));
        this.skanetrafikenAPI = new SkanetrafikenAPI();
    }


    @FXML
    public void initialize() {
        startWeatherUpdater();
        startNewsUpdater();
        startRedditAndBusUpdater();
        startTimeAndDateUpdater();
        startAutoScroll();
        setWeatherFont(weatherIconLabel);
        busDepartureTitle.setText("Upcoming departure to " + prop.getProperty("hplatsA"));
        subredditLabel.setText("Reddit feed /" + prop.getProperty("subreddit"));
        newsLabel.setText("News, " + prop.getProperty("newsCat"));
    }

    /**
     * Sets up how often the digital clock should update
     */
    private void startTimeAndDateUpdater() {
        Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            Calendar now = Calendar.getInstance();
            timeLabel.setText(new SimpleDateFormat("HH:mm").format(now.getTime()));
            dateLabel.setText(getDatePrint());

        }));
        fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
        fiveSecondsWonder.play();

    }

    private void startRedditAndBusUpdater() {
        Runnable updateNews = () -> {
            ArrayList<Post> posts = redditFlow.getFlow();
            ArrayList<Journey> journeys = skanetrafikenAPI.getJourneys(prop.getProperty("hplatsD"), prop.getProperty("hplatsA"), 3);
            Platform.runLater(() -> {
                redditBox.getChildren().clear();
                for (int i = posts.size() - 1; i >= 0; i--) {
                    Label l = new Label("+" + posts.get(i).getScore() + " " + posts.get(i).getTitle());
                    l.setTextFill(Color.WHITE);
                    l.setFont(new Font(20));
                    redditBox.getChildren().add(l);
                }
                StringBuilder sb = new StringBuilder();
                for (Journey j : journeys) {
                    String depTime = TimeAndDateConverter.timeToDeparture(j.getDepDateTime());
                    if (j.deviationType() == RouteLink.EARLY) {
                        sb.append(j.getFirstRouteLineNbr() + "" + depTime + " (" + j.getDeviationDepTimeToString() + " early)\n");
                    } else if (j.deviationType() == RouteLink.LATE) {
                        sb.append(j.getFirstRouteLineNbr() + " " + depTime + " (" + j.getDeviationDepTimeToString() + " late)\n");
                    } else if (j.deviationType() == RouteLink.IN_TIME) {
                        sb.append(j.getFirstRouteLineNbr() + " " + depTime + " (in time)\n");
                    } else {
                        sb.append(j.getFirstRouteLineNbr() + " in " + depTime + "\n");
                    }
                }
                busDepartureLabel.setText(sb.toString());
            });
        };
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(updateNews, 0, 1, TimeUnit.MINUTES);

    }

    private void startNewsUpdater() {
        Runnable updateNews = () -> {
            News news = newsAPI.getNews();
            Platform.runLater(() -> {
                newsBox.getChildren().clear();
                for (int i = 0; i < news.getResults().size(); i++) {
                    Label l = new Label(news.getResults().get(i).getTitle());
                    l.setTextFill(Color.WHITE);
                    l.setFont(new Font(20));
                    newsBox.getChildren().add(l);
                }
            });
        };
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(updateNews, 0, 5, TimeUnit.MINUTES);
    }

    private void startWeatherUpdater() {
        Runnable updateWeather = () -> {
            Forecasts f = weatherApi.getForecasts();
            HourlyForecast forecast = f.getCurrentWeather();

            Platform.runLater(() -> {
                weatherIconLabel.setText(WeatherConditionCodes.fromInt(forecast.getWeatherSymbol()).toString());
                temperatureLabel.setText(forecast.getTemp() + " °C");
                windLabel.setText(forecast.getWindVelocity() + " m/s " + "max " + forecast.getWindGust() + " m/s");
                rainForecastLabel.setText("Rain: " + forecast.getRainfallMeanAmount() + " mm");

            });
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(updateWeather, 0, 10, TimeUnit.MINUTES);
    }

    private void startAutoScroll() {
        Runnable updateWeather = () -> Platform.runLater(() -> {
            double amount = redditScrollPane.getVvalue();
            if (amount + 0.001 > redditScrollPane.getVmax()) {
                amount = 0;
            } else {
                amount += 0.001;
            }
            redditScrollPane.setVvalue(amount);
            amount = newsScrollPane.getVvalue();
            if (amount + 0.001 > newsScrollPane.getVmax()) {
                amount = 0;
            } else {
                amount += 0.001;
            }
            newsScrollPane.setVvalue(amount);

        });

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(updateWeather, 0, 100, TimeUnit.MILLISECONDS);
    }


    private String getDatePrint() {
        Calendar now = Calendar.getInstance();
        String monthOfYear = new SimpleDateFormat("EEEE d MMMM, y", Locale.ENGLISH).format(now.getTime());
        return monthOfYear;
    }

    /**
     * Sets the weather font from https://erikflowers.github.io/weather-icons/
     * to a Label
     *
     * @param weatherLabel
     */
    private void setWeatherFont(Label weatherLabel) {
        Font f = null;
        try {
            URL url = getClass().getResource("/fonts/weathericons-regular-webfont.ttf");
            f = Font.loadFont(url.openStream(), 120);
        } catch (IOException e) {
            e.printStackTrace();
        }
        weatherLabel.setFont(f);
    }


}
