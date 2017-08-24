package entities;

import eu.crydee.syllablecounter.SyllableCounter;

import java.util.Arrays;

public class ADSentence {
    private String header;
    private Integer id;
    private Integer wordCount = 0;
    private Integer charCount = 0;
    private Integer syllCount = 0;
    private Integer longWordsCount = 0; //3+ syllabels
    private Integer shortWordsCount = 0; //1 syllabel
    private Integer[] posArray = new Integer[PosTags.getPosTagsLenght()];
    private Integer punctCharCount = 0;
    private Integer passive = 0;
    private Integer sentiment;

    public ADSentence(InputSentence inSentence, SyllableCounter counter, String header) {
        Arrays.fill(posArray, 0);
        this.header = header;
        this.id = inSentence.getId();
        if (inSentence.getPassive()) {
            this.passive = 1;
        }
        this.sentiment = inSentence.getSentiment();
        String word;
        Integer syllables;
        for (InputToken token : inSentence.getTokens()) {
            wordCount++;
            charCount += token.getCharacters();
            syllables =  token.getSyllables();
            syllCount += syllables;
            if (syllables > 2) { longWordsCount++; }
            if (syllables == 1) { shortWordsCount++; }
//            System.out.println(token.getPOS() + ": " + PosTags.getPosIndex(token.getPOS()));
            posArray[PosTags.getPosIndex(token.getPOS())]++;
        }
    }

    @Override
    public String toString() {
        return "{\"ADSentence\": {" +
                "\"header\": " + header + "," +
                "\"id\": " + id.toString() + "," +
                "\"wordCount\": " + wordCount.toString() + "," +
                "\"charCount\": " + charCount.toString() + "," +
                "\"syllCount\": " + syllCount.toString() + "," +
                "\"longWordsCount\": " + longWordsCount.toString() + "," +
                "\"shortWordsCount\": " + shortWordsCount.toString() + "," +
                "\"sentiment\": " + passive.toString() + "," +
                "\"sentiment\": " + sentiment.toString() + "," +
                "\"posArray\": " + Arrays.toString(posArray) +
                "}}";
    }

    public String toJSON() {
        return this.toString();
    }

    public String toCSVLine() {
        return "\"" + header + "\"," +
                id.toString() + "," +
                wordCount.toString() + "," +
                charCount.toString() + "," +
                syllCount.toString() + "," +
                longWordsCount.toString() + "," +
                shortWordsCount.toString() + "," +
                passive.toString() + "," +
                sentiment.toString() + "," +
                Arrays.toString(posArray).replace("[", "").replace("]", "");
    }


    public static String getCSVHeader() {
        String posTagString = "";
        for (String tag : PosTags.getPosTags()) {
            posTagString += "\"" + tag + "\", ";
        }
        posTagString = posTagString.substring(0, posTagString.length() - 1);
        return "header, id, wordCount, charCount, syllCount, longWordsCount, shortWordsCount, passive, sentiment, " + posTagString;
    }
}
