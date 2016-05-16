package newyorktimes;

/**
 * Created by philip on 2016-05-11.
 */
public class Article {
    public String Title;
    public String shortDescription;
    public String Author;
    public String publishedDate;

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String toString(){
        return getTitle() +"\n" +
                getShortDescription();
    }
}
