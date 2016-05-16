package Gui;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.util.Duration;
import newyorktimes.News;
import newyorktimes.NewsAPI;
import smhi.Forecasts;
import smhi.HourlyForecast;
import smhi.SMHIWeatherAPI;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by kasper on 2016-05-12.
 */


public class Controller {
    private SMHIWeatherAPI weatherApi;
    private NewsAPI newsAPI;
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
    ScrollPane redditScrollPane;
    @FXML
    ScrollPane newsScrollPane;

    @FXML
    VBox newsBox;

    @FXML
    VBox redditBox;


    public Controller() {
        this.weatherApi = new SMHIWeatherAPI("13.191", "55.704");
        this.newsAPI = new NewsAPI();

    }


    @FXML
    public void initialize() {
        startWeatherUpdater();
        startNewsUpdater();
        startRedditUpdater();
        startTimeAndDateUpdater();

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

    private void startRedditUpdater() {

    }

    private void startNewsUpdater() {
        Runnable updateNews = () -> {
            News news = newsAPI.getNews();
            Platform.runLater(() -> {
                for (int i = 0; i < news.getResults().size(); i++) {
                    Label l = new Label(news.getResults().get(i).getTitle());
                    l.setFont(new Font(20));
                    newsBox.getChildren().add(l);
                    newsScrollPane.setVvalue(1.0);
                    redditScrollPane.setVvalue(1.0);
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
            System.out.println(forecast.toString());

            Platform.runLater(() -> {
                temperatureLabel.setText(forecast.getTemp() + " °C");
                windLabel.setText(forecast.getWindVelocity() + " m/s " + "max " + forecast.getWindGust() + " m/s");
                rainForecastLabel.setText("Rain: " + forecast.getRainfallMeanAmount() + " mm");

            });
        };

        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(updateWeather, 0, 10, TimeUnit.SECONDS);

    }

    private String getDatePrint() {
        Calendar now = Calendar.getInstance();
        String monthOfYear = new SimpleDateFormat("EEEE d MMMM, y", Locale.ENGLISH).format(now.getTime());
        return monthOfYear;
    }


}
