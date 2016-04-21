package reddit;

/**
 * Created by Kevin on 2016-04-19.
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

//@JsonIgnoreProperties(ignoreUnknown=true)
public class RedditApi {
    Data1 data;
    String kind;

    public Data1 getData() {
        return data;
    }

    public void setData(Data1 data) {
        this.data = data;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public ArrayList<Post> getPosts(){
        ArrayList<Post> posts = new ArrayList<>();
        for (Children c : data.getChildren()){
            posts.add(c.getData());
        }
        return posts;
    }


    @Override
    public String toString() {
        return data.toString();
    }


    @JsonIgnoreProperties(ignoreUnknown = true)
    static class Data1 {
        String modhash;
        ArrayList<Children> children;
        String after;

        public String getAfter() {
            return after;
        }

        public void setAfter(String after) {
            this.after = after;
        }

        public String getModhash() {
            return modhash;
        }

        public void setModhash(String modhash) {
            modhash = modhash;
        }

        public ArrayList<Children> getChildren() {
            return children;
        }

        public void setChildren(ArrayList<Children> children) {
            this.children = children;
        }

        @Override
        public String toString() {
            StringBuilder s = new StringBuilder();
            s.append(getModhash());
            s.append(after);
            for (Children c : children) {
                s.append(c.toString() + "\n");
            }

            return s.toString();
        }


    }

    @JsonIgnoreProperties(ignoreUnknown = true)

    static class Children {
        Post data;

        public Post getData() {
            return data;
        }

        public void setData(Post data) {
            this.data = data;
        }

        @Override
        public String toString() {
            return data.toString();
        }
    }

}

