package entities;

import java.util.Arrays;

public class ADSentenceBlock {
    private String header;
    private int id;
    private int sentences = 0;
    private int words = 0;
    private int chars = 0;
    private int syllables = 0;
    private int shortSentences = 0;
    private int longSentences = 0;
    private int shortWords = 0; //1 syllable
    private int longWords = 0; //3+ syllables
    private int sixCharWords = 0; //6+ chars
    private int passive = 0;
    private int questions = 0;
    private int[] sentimentArray = new int[5];
    private int[] posArray = new int[PosTags.getPosTagsLenght()];
    private int startsWithCCorIN = 0;
    private String firstSentence = "";



    public ADSentenceBlock(int id, String header) {
        Arrays.fill(sentimentArray, 0);
        Arrays.fill(posArray, 0);
        this.header = header;
        this.id = id;
    }

    public ADSentenceBlock(InputSentence inSentence, String header) {
        Arrays.fill(sentimentArray, 0);
        Arrays.fill(posArray, 0);
        boolean starting = true;
        this.header = header;
        this.id = inSentence.getId();
        sentences = 1;
        if (inSentence.getPassive()) {
            this.passive = 1;
        }
        this.sentimentArray[inSentence.getSentiment()]++;
        int syllables;
        int pos;
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
            pos = PosTags.getPosIndex(token.getPOS());
            posArray[pos]++;
            if (starting && pos > 8) {
                if (pos == 9 || pos == 14) {
                    startsWithCCorIN = 1;
                }
                starting = false;
            }
            firstSentence += token.getWord() + " ";
        }
        shortSentences = (words < 8) ? 1 : 0;
        longSentences = (words > 15) ? 1 : 0;
        firstSentence = firstSentence.replaceAll("\"", "");
    }

    public void loadCSVLine(String sourceCSVLine) {
        String[] brokenLine = sourceCSVLine.replace(" ", "").split(",");
        header = brokenLine[0].replaceAll("\"", "");
        id = Integer.parseInt(brokenLine[1]);
        sentences = Integer.parseInt(brokenLine[2]);
        words = Integer.parseInt(brokenLine[3]);
        chars = Integer.parseInt(brokenLine[4]);
        syllables = Integer.parseInt(brokenLine[5]);
        shortSentences = Integer.parseInt(brokenLine[6]);
        longSentences = Integer.parseInt(brokenLine[7]);
        shortWords = Integer.parseInt(brokenLine[8]); //1 syllable
        longWords = Integer.parseInt(brokenLine[9]); //3+ syllables
        sixCharWords = Integer.parseInt(brokenLine[10]); //6+ chars
        passive = Integer.parseInt(brokenLine[11]);
        questions = Integer.parseInt(brokenLine[12]);
        startsWithCCorIN = Integer.parseInt(brokenLine[13]);
        for (int i = 0; i < sentimentArray.length; i++) {
            sentimentArray[i] = Integer.parseInt(brokenLine[14 + i]);
        }
        for (int i = 0; i < posArray.length; i++) {
            posArray[i] = Integer.parseInt(brokenLine[19 + i]);
        }
        firstSentence = brokenLine[19 + PosTags.getPosTagsLenght()].replaceAll("\"", "");
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
        startsWithCCorIN += otherADSB.startsWithCCorIN;
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
        startsWithCCorIN -= otherADSB.startsWithCCorIN;
        for (int i = 0; i < sentimentArray.length; i++) {
            sentimentArray[i] -= otherADSB.sentimentArray[i];
        }
        for (int i = 0; i < posArray.length; i++) {
            posArray[i] -= otherADSB.posArray[i];
        }
    }

    public void replace(ADSentenceBlock otherADSB) {
        sentences = otherADSB.sentences;
        words = otherADSB.words;
        chars = otherADSB.chars;
        syllables = otherADSB.syllables;
        shortSentences = otherADSB.shortSentences;
        longSentences = otherADSB.longSentences;
        shortWords = otherADSB.shortWords;
        longWords = otherADSB.longWords;
        sixCharWords = otherADSB.sixCharWords;
        passive = otherADSB.passive;
        questions = otherADSB.questions;
        startsWithCCorIN = otherADSB.startsWithCCorIN;
        System.arraycopy(otherADSB.sentimentArray, 0, sentimentArray, 0, sentimentArray.length);
        System.arraycopy(otherADSB.posArray, 0, posArray, 0, posArray.length);
        firstSentence = otherADSB.firstSentence;
    }


    @Override
    public String toString() {
        return "{\"ADSentenceBlock\": {" +
                "\"header\": " + header + "," +
                "\"id\": " + Integer.toString(id) + "," +
                "\"sentences\": " + Integer.toString(sentences) + "," +
                "\"words\": " + Integer.toString(words) + "," +
                "\"chars\": " + Integer.toString(chars) + "," +
                "\"syllables\": " + Integer.toString(syllables) + "," +
                "\"shortSentences\": " + Integer.toString(shortSentences) + "," +
                "\"longSentences\": " + Integer.toString(longSentences) + "," +
                "\"shortWords\": " + Integer.toString(shortWords) + "," +
                "\"longWords\": " + Integer.toString(longWords) + "," +
                "\"sixCharWords\": " + Integer.toString(sixCharWords) + "," +
                "\"passive\": " + Integer.toString(passive) + "," +
                "\"questions\": " + Integer.toString(questions) + "," +
                "\"startsWithCCorIN\": " + Integer.toString(questions) + "," +
                "\"sentimentArray\": " + Arrays.toString(sentimentArray) + "," +
                "\"posArray\": " + Arrays.toString(posArray) +
                "\"firstSentence\": " + firstSentence + "," +
                "}}";
    }

    public String toJSON() {
        return this.toString();
    }

    public String toCSVLine() {
        return "\"" + header + "\"," +
                Integer.toString(id) + "," +
                Integer.toString(sentences) + "," +
                Integer.toString(words) + "," +
                Integer.toString(chars) + "," +
                Integer.toString(syllables) + "," +
                Integer.toString(shortSentences) + "," +
                Integer.toString(longSentences) + "," +
                Integer.toString(shortWords) + "," +
                Integer.toString(longWords) + "," +
                Integer.toString(sixCharWords) + "," +
                Integer.toString(passive) + "," +
                Integer.toString(questions) + "," +
                Integer.toString(startsWithCCorIN) + "," +
                Arrays.toString(sentimentArray).replace("[", "").replace("]", "") + "," +
                Arrays.toString(posArray).replace("[", "").replace("]", "") +
                "\"" + firstSentence + "\"";

    }


    public static String getCSVHeader() {

        return "header, id, sentences, words, chars, syllables, shortSentences, longSentences, shortWords, longWords," +
                " sixCharWords, passive, questions, startsWithCCorIN, sentiment0, sentiment1, sentiment2, sentiment3, sentiment4, " +
                PosTags.getCSVHeaderString() + ", firstSentence";
    }

        public String getHeader() {
        return header;
    }
    public int getId() {
        return id;
    }

    public int getSentences() {
        return sentences;
    }

    public int getWords() {
        return words;
    }

    public int getChars() {
        return chars;
    }

    public int getSyllables() {
        return syllables;
    }

    public int getShortSentences() {
        return shortSentences;
    }

    public int getLongSentences() {
        return longSentences;
    }

    public int getShortWords() {
        return shortWords;
    }

    public int getLongWords() {
        return longWords;
    }

    public int getSixCharWords() {
        return sixCharWords;
    }

    public int getPassive() {
        return passive;
    }

    public int getQuestions() {
        return questions;
    }

    public int getStartsWithCCorIN() {
        return startsWithCCorIN;
    }

    public int[] getSentimentArray() {
        return sentimentArray;
    }

    public int[] getPosArray() {
        return posArray;
    }

    public String getFirstSentence() {
        return firstSentence;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setId(int id) {
        this.id = id;
    }
}
