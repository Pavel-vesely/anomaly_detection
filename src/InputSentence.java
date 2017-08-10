import java.util.ArrayList;
import java.util.stream.Collectors;

public class InputSentence {
    private Integer id = 0;
    private ArrayList<InputToken> tokens = new ArrayList<InputToken>();

    public InputSentence() {}

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
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
                "\"tokens\": [" + tokenString + "]" +
                "}}";
    }
}
