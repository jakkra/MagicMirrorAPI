package newyorktimes;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsAPI {
    private ArrayList<Results> results;

    public ArrayList<Results> getResults() {
        return results;
    }

    public void setResults(ArrayList<Results> results) {
        this.results = results;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (Results r: results) {
            sb.append(r.toString() + "\n");
        }
        return sb.toString();

    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Results{
        public String title;

        @JsonProperty(value = "abstract")
        public String shortDescription;
        public String byline;
        public String published_date;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShortDescription() {
            return shortDescription;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public String getByline() {
            return byline;
        }

        public void setByline(String byline) {
            this.byline = byline;
        }

        public String getPublished_date() {
            return published_date;
        }

        public void setPublished_date(String published_date) {
            this.published_date = published_date;
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append(getTitle() + "\n" + getShortDescription()+"\n");
            return sb.toString();
        }
    }

}



