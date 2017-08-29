package entities;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class InputSentence {
    private int id = 0;
    private Boolean passive = false;
    private int sentiment = 3;
    private ArrayList<InputToken> tokens = new ArrayList<InputToken>();

    public InputSentence() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSentiment() {
        return sentiment;
    }

    public void setSentiment(int sentiment) {
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
                "\"id\": " + Integer.toString(id) + "," +
                "\"passive\": " + passive.toString() + "," +
                "\"sentiment\": " + Integer.toString(sentiment) + "," +
                "\"tokens\": [" + tokenString + "]" +
                "}}";
    }

    public String toJSON() {
        return this.toString();
    }
}
