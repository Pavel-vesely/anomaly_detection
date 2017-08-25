package entities;

import java.util.Arrays;

public class ADSentenceBlock {
    private String header;
    private Integer id;
    private Integer sentences = 0;
    private Integer words = 0;
    private Integer chars = 0;
    private Integer syllables = 0;
    private Integer shortSentences = 0;
    private Integer longSentences = 0;
    private Integer shortWords = 0; //1 syllable
    private Integer longWords = 0; //3+ syllables
    private Integer sixCharWords = 0; //6+ chars
    private Integer passive = 0;
    private Integer questions = 0;
    private Integer[] sentimentArray = new Integer[5];
    private Integer[] posArray = new Integer[PosTags.getPosTagsLenght()];


    public ADSentenceBlock(Integer id, String header) {
        Arrays.fill(sentimentArray, 0);
        Arrays.fill(posArray, 0);
        this.header = header;
        this.id = id;
    }

    public ADSentenceBlock(InputSentence inSentence, String header) {
        Arrays.fill(sentimentArray, 0);
        Arrays.fill(posArray, 0);
        this.header = header;
        this.id = inSentence.getId();
        sentences = 1;
        if (inSentence.getPassive()) {
            this.passive = 1;
        }
        this.sentimentArray[inSentence.getSentiment() - 1]++;
        Integer syllables;
        for (InputToken token : inSentence.getTokens()) {
            words++;
            chars += token.getCharacters();
            syllables = token.getSyllables();
            this.syllables += syllables;
            if (syllables > 2) {
                longWords++;
            }
            if (syllables == 1) {
                shortWords++;
            }
            if (token.getCharacters() > 5) {
                sixCharWords++;
            }
            if (token.getPOS().equals(".") && token.getLemma().equals("?")) {
                questions = 1;
            }
            posArray[PosTags.getPosIndex(token.getPOS())]++;
        }
        shortSentences = (words < 8) ? 1 : 0;
        longSentences = (words > 15) ? 1 : 0;
    }

    public ADSentenceBlock(String sourceCSVLine) {
        String[] brokenLine = sourceCSVLine.replace(" ", "").split(",");
        header = brokenLine[0].replaceAll("\"", "");
        id = Integer.valueOf(brokenLine[1]);
        sentences = Integer.valueOf(brokenLine[2]);
        words = Integer.valueOf(brokenLine[3]);
        chars = Integer.valueOf(brokenLine[4]);
        syllables = Integer.valueOf(brokenLine[5]);
        shortSentences = Integer.valueOf(brokenLine[6]);
        longSentences = Integer.valueOf(brokenLine[7]);
        shortWords = Integer.valueOf(brokenLine[8]); //1 syllable
        longWords = Integer.valueOf(brokenLine[9]); //3+ syllables
        sixCharWords = Integer.valueOf(brokenLine[10]); //6+ chars
        passive = Integer.valueOf(brokenLine[11]);
        questions = Integer.valueOf(brokenLine[12]);
        for (int i = 0; i < sentimentArray.length; i++) {
            sentimentArray[i] = Integer.valueOf(brokenLine[13 + i]);
        }
        for (int i = 0; i < posArray.length; i++) {
            posArray[i] = Integer.valueOf(brokenLine[18 + i]);
        }
    }


    public void increase(ADSentenceBlock otherADSB) {
        sentences += otherADSB.sentences;
        words += otherADSB.words;
        chars += otherADSB.chars;
        syllables += otherADSB.syllables;
        shortSentences += otherADSB.shortSentences;
        longSentences += otherADSB.longSentences;
        shortWords += otherADSB.shortWords;
        longWords += otherADSB.longWords;
        sixCharWords += otherADSB.sixCharWords;
        passive += otherADSB.passive;
        questions += otherADSB.questions;
        for (int i = 0; i < sentimentArray.length; i++) {
            sentimentArray[i] += otherADSB.sentimentArray[i];
        }
        for (int i = 0; i < posArray.length; i++) {
            posArray[i] += otherADSB.posArray[i];
        }
    }

    public void decrease(ADSentenceBlock otherADSB) {
        sentences -= otherADSB.sentences;
        words -= otherADSB.words;
        chars -= otherADSB.chars;
        syllables -= otherADSB.syllables;
        shortSentences -= otherADSB.shortSentences;
        longSentences -= otherADSB.longSentences;
        shortWords -= otherADSB.shortWords;
        longWords -= otherADSB.longWords;
        sixCharWords -= otherADSB.sixCharWords;
        passive -= otherADSB.passive;
        questions -= otherADSB.questions;
        for (int i = 0; i < sentimentArray.length; i++) {
            sentimentArray[i] -= otherADSB.sentimentArray[i];
        }
        for (int i = 0; i < posArray.length; i++) {
            posArray[i] -= otherADSB.posArray[i];
        }
    }


    @Override
    public String toString() {
        return "{\"ADSentenceBlock\": {" +
                "\"header\": " + header + "," +
                "\"id\": " + id.toString() + "," +
                "\"sentences\": " + sentences.toString() + "," +
                "\"words\": " + words.toString() + "," +
                "\"chars\": " + chars.toString() + "," +
                "\"syllables\": " + syllables.toString() + "," +
                "\"shortSentences\": " + shortSentences.toString() + "," +
                "\"longSentences\": " + longSentences.toString() + "," +
                "\"shortWords\": " + shortWords.toString() + "," +
                "\"longWords\": " + longWords.toString() + "," +
                "\"sixCharWords\": " + sixCharWords.toString() + "," +
                "\"passive\": " + passive.toString() + "," +
                "\"questions\": " + questions.toString() + "," +
                "\"sentimentArray\": " + Arrays.toString(sentimentArray) + "," +
                "\"posArray\": " + Arrays.toString(posArray) +
                "}}";
    }

    public String toJSON() {
        return this.toString();
    }

    public String toCSVLine() {
        return "\"" + header + "\"," +
                id.toString() + "," +
                sentences.toString() + "," +
                words.toString() + "," +
                chars.toString() + "," +
                syllables.toString() + "," +
                shortSentences.toString() + "," +
                longSentences.toString() + "," +
                shortWords.toString() + "," +
                longWords.toString() + "," +
                sixCharWords.toString() + "," +
                passive.toString() + "," +
                questions.toString() + "," +
                Arrays.toString(sentimentArray).replace("[", "").replace("]", "") + "," +
                Arrays.toString(posArray).replace("[", "").replace("]", "");
    }


    public static String getCSVHeader() {
        String posTagString = "";
        for (String tag : PosTags.getPosTags()) {
            posTagString += "\"" + tag + "\", ";
        }
        posTagString = posTagString.substring(0, posTagString.length() - 1);
        return "header, id, sentences, words, chars, syllables, shortSentences, longSentences, shortWords, longWords," +
                " sixCharWords, passive, questions, sentiment1, sentiment2, sentiment3, sentiment4, sentiment5, " + posTagString;
    }
}
