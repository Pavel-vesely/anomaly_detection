import eu.crydee.syllablecounter.SyllableCounter;

public class ADSentence {
    private Integer id;
    private Integer wordCount = 0;
    private Integer charCount = 0;
    private Integer syllCount = 0;
    private Integer longWordsCount = 0; //3+ syllabels
    private Integer shortWordsCount = 0; //1 syllabel
    private Integer punctCharCount = 0;

    public ADSentence(InputSentence inSentence, SyllableCounter counter) {
        this.id = inSentence.getId();
        String word;
        Integer syllabels;
        for (InputToken token : inSentence.getTokens()) {
            wordCount++;
            charCount += token.getCharacters();
            syllabels =  token.getSyllables();
            syllCount += syllabels;
            if (syllabels > 2) { longWordsCount++; }
            if (syllabels == 1) { shortWordsCount++; }
        }
    }

    @Override
    public String toString() {
        return "{\"ADSentence\": {" +
                "\"id\": " + id.toString() + "," +
                "\"wordCount\": " + wordCount.toString() + "," +
                "\"charCount\": " + charCount.toString() + "," +
                "\"syllCount\": " + syllCount.toString() + "," +
                "\"longWordsCount\": " + longWordsCount.toString() + "," +
                "\"shortWordsCount\": " + shortWordsCount.toString() + "" +
                "}}";
    }
}
