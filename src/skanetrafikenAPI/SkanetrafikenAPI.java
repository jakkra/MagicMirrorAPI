package skanetrafikenAPI;
/*
 * Created by Jakkra
 */

import skanetrafikenAPI.downloaders.SearchJourneysTask;
import skanetrafikenAPI.downloaders.SearchStationsTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to retrieve journeys from Skånetrafiken
 */
public class SkanetrafikenAPI {
    Map<String, Integer> savedStationIds;

    public SkanetrafikenAPI() {
        savedStationIds = new HashMap<>();
    }

    /**
     * @param fromStation name of from station
     * @param toStation name of to station
     * @param nbrResults nbr journeys to get back
     * This method will lock run this in a separate Thread.
     * @return list of Journeys
     */
    public ArrayList<Journey> getJourneys(String fromStation, String toStation, int nbrResults) {
        //Save the ids of the two Stations, so we don't have to make the two extra calls every time.
        if(!savedStationIds.containsKey(fromStation) || !savedStationIds.containsKey(toStation)) {
            SearchStationsTask task = new SearchStationsTask();
            String url = Constants.getSearchStationURL(fromStation);
            ArrayList<Station> stationsResult = task.download(url);
            Station from = stationsResult.get(0);
            savedStationIds.put(fromStation, from.getStationId());

            task = new SearchStationsTask();
            url = Constants.getSearchStationURL(toStation);
            stationsResult = task.download(url);
            Station to = stationsResult.get(0);
            savedStationIds.put(toStation, to.getStationId());
        }

        String journey = Constants.getURL(savedStationIds.get(fromStation), savedStationIds.get(toStation), nbrResults);
        SearchJourneysTask jTask = new SearchJourneysTask();
        return jTask.download(journey);
    }



}
