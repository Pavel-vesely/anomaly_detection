package entities;

import java.util.Arrays;

public class ADVector {
    private static final int VECTOR_LEN = 23;
    private String header;
    private int id;
    private boolean complement;
    private double[] computeVector = new double[VECTOR_LEN];

    public ADVector(ADSentenceBlock sBlock, Boolean complement) {
        this.header = sBlock.getHeader();
        this.id = sBlock.getId();
        this.complement = complement;
        int[] posArray = sBlock.getPosArray();
        double sentences = (double)sBlock.getSentences();
        double words = (double)sBlock.getWords();
        double chars = (double)sBlock.getChars();
        double syllables = (double)sBlock.getSyllables();
        computeVector[0] = words / sentences; //Avg sentence length
        computeVector[1] = chars / words; //Avg word length
        computeVector[2] = syllables / words; //Avg syllables/word
        computeVector[3] = (double)sBlock.getLongWords() / words * 100.0; //Percentage of long words (3+ syllables)
        computeVector[4] = (double)sBlock.getShortWords() / words * 100.0; //Percentage of short words (1 syllable)
        computeVector[5] = (double)sBlock.getLongSentences() / sentences * 100.0; //Percentage of long sentences (16+ words)
        computeVector[6] = (double)sBlock.getShortSentences() / sentences * 100.0; //Percentage of short sentences (<8 words)
        computeVector[7] = (double)sBlock.getQuestions() / sentences * 100.0; //Percentage of sentences that are questions
        double puncChars = 0.0;
        for (int i = 0; i <= 8; i++) {
            puncChars += (double)posArray[i];
        }
        computeVector[8] = puncChars / chars * 100.0; //Percentage of punctuation characters
        computeVector[9] = (double)posArray[8] / chars * 100.0; //Percentage of chars that are ':', ';', '...'
        computeVector[10] = (double)posArray[5] / chars * 100.0; //Percentage of chars that are ','
        computeVector[11] = (double)sBlock.getSixCharWords() / words * 100.0; //Percentage of words with 6+ chars
        //Not using word tokens/words
        computeVector[12] = (double)posArray[14] / words * 100.0; //Percentage of words that are IN POS
        computeVector[13] = (double)posArray[9] / words * 100.0; //Percentage of words that are CC POS
        //Sentences starting with CC or IN
        computeVector[14] = (double)posArray[11] / words * 100.0; //Percentage of words that are DT POS
        //Prepositions
        computeVector[15] = ((double)posArray[26] + (double)posArray[27] + (double)posArray[42] + (double)posArray[43]) / words * 100.0; //Percentage of words that are pronouns

        computeVector[16] =  206.835 - 1.015 * (words / sentences) - 84.6 * (syllables / words); //Flesch-Kincaid Reading Ease
        computeVector[17] = 11.8 * (syllables / words) + 0.39 * (words / sentences) - 15.59; //Flesch-Kincaid Grade Level
        computeVector[18] = (words / sentences) + (double)sBlock.getLongWords() / words * 100.0; //Gunning-Fog Index
        computeVector[19] = 5.89 * (chars / words) - 0.3 * (sentences / words) / 100.0 - 15.8; //Coleman-Liau Formula
        computeVector[20] = 4.71 * (chars / words) + 0.5 * (words / sentences) - 21.43; //Automated Readability Index
        computeVector[21] = (words / sentences) + 100.0 * ((double)sBlock.getSixCharWords() / words); // Lix Formula
        computeVector[22] =  3 + Math.sqrt((double)sBlock.getLongWords() * 30 / sentences); //SMOG Index

    }

    public double manhattanDistanceTo(ADVector other) {
        double[] otherVector = other.getComputeVector();
        double distance = 0.0;
        for (int i = 0; i < VECTOR_LEN; i++) {
            distance += Math.abs(this.computeVector[i] - otherVector[i]);
        }
        return distance;
    }

    public String toCSVLine() {
        return "\"" + header + "\"," +
                Integer.toString(id) + "," +
                Boolean.toString(complement) + "," +
                Arrays.toString(computeVector).replace("[", "").replace("]", "");
    }

    public String differenceToCSVLine(ADVector other) {
        String resultString = "\"" + header + "\"," +
                Integer.toString(id) + "," +
                Boolean.toString(complement);
        double[] otherVector = other.getComputeVector();
        double distance = 0.0;
        for (int i = 0; i < VECTOR_LEN; i++) {
            distance += Math.abs(this.computeVector[i] - otherVector[i]);
            resultString += "," + Double.toString(Math.abs(this.computeVector[i] - otherVector[i]));
        }
        resultString += "," + Double.toString(distance);
        return resultString;
    }

    public double[] getComputeVector() {
        return computeVector;
    }

    public static String getCSVHeader() {
        //TODO
        return "";
    }
}

