import java.io.Serializable;

public class NLPToken implements Serializable {
    protected String word = "";
    protected String lemma = "";
    protected Integer characters = 0;
    protected Integer syllables = 0;
    protected String POS = "";

    public NLPToken(){}

    public NLPToken(String word, String lemma, Integer characters, Integer syllables, String POS){
        this.word = word;
        this.lemma = lemma;
        this.characters = characters;
        this.syllables = syllables;
        this.POS = POS;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getLemma() {
        return lemma;
    }

    public void setLemma(String lemma) {
        this.lemma = lemma;
    }

    public Integer getCharacters() {
        return characters;
    }

    public void setCharacters(Integer characters) {
        this.characters = characters;
    }

    public Integer getSyllables() {
        return syllables;
    }

    public void setSyllables(Integer syllables) {
        this.syllables = syllables;
    }

    public String getPOS() {
        return POS;
    }

    public void setPOS(String POS) {
        this.POS = POS;
    }

    public String toString() {
        return "{'ADWord': {" +
                "'word': '" + word + "'," +
                "'lemma':" + lemma + "'," +
                "'characters': " + characters.toString() + "," +
                "'syllables': " + syllables.toString() + "," +
                "'POS': '" + POS + "'" +
                "}}";
    }
}
