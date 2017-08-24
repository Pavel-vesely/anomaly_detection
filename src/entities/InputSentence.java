package entities;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class InputSentence {
    private Integer id = 0;
    private Boolean passive = false;
    private Integer sentiment = 3;
    private ArrayList<InputToken> tokens = new ArrayList<InputToken>();

    public InputSentence() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSentiment() {
        return sentiment;
    }
    public void setSentiment(Integer sentiment) {
        this.sentiment = sentiment;
    }

    public Boolean getPassive() {
        return passive;
    }

    public void setPassive(Boolean passive) {
        this.passive = passive;
    }

    public ArrayList<InputToken> getTokens() {
        return tokens;
    }
    public void setTokens(ArrayList<InputToken> tokens) {
        this.tokens = tokens;
    }

    public String toString() {
        String tokenString = tokens.stream().map(Object::toString).collect(Collectors.joining(", "));
        return "{\"InputSentence\": {" +
                "\"id\": " + id.toString() + "," +
                "\"passive\": " + passive.toString() + "," +
                "\"sentiment\": " + sentiment.toString() + "," +
                "\"tokens\": [" + tokenString + "]" +
                "}}";
    }

    public String toJSON() {
        return this.toString();
    }
}
