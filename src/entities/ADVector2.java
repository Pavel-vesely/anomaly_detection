package entities;

public class ADVector2 {
    private double[] surfaceVector = new double[21];
    private double[] sentimentVector = new double[5];
    private double[] wordClassVector = new double[12];
    private long startOffset;
    private long endOffset;
    private String header;
    private int id;
    private String text = "";

    public void loadSurfaceFeatures(ADSentenceBlock sBlock) {
        double sentences = (double) sBlock.getSentences();
        double words = (double) sBlock.getWords();
        double chars = (double) sBlock.getChars();
        double syllables = (double) sBlock.getSyllables();

        int[] posArray = sBlock.getPosArray();
        //Simple surface metrics
        surfaceVector[0] = words / sentences; //Avg sentence length
        surfaceVector[1] = chars / words; //Avg word length
        surfaceVector[2] = syllables / words; //Avg syllables/word
        surfaceVector[3] = (double) sBlock.getShortSentences() / sentences; //Percentage of short sentences (<8 words)
        surfaceVector[4] = (double) sBlock.getLongSentences() / sentences; //Percentage of long sentences (16+ words)
        surfaceVector[5] = (double) sBlock.getShortWords() / words; //Percentage of short words (1 syllable)
        surfaceVector[6] = (double) sBlock.getLongWords() / words; //Percentage of long words (3+ syllables)
        surfaceVector[7] = (double) sBlock.getSixCharWords() / words; //Percentage of words with 6+ chars
        surfaceVector[8] = (double) sBlock.getPassive() / words; //Percentage of passive sentences
        surfaceVector[9] = (double) sBlock.getQuestions() / sentences; //Percentage of sentences that are questions
        surfaceVector[10] = (double) sBlock.getStartsWithCCorIN() / sentences; //Sentences starting with CC or IN
        double puncChars = 0.0;
        for (int i = 0; i <= 8; i++) {
            puncChars += (double) posArray[i];
        }
        surfaceVector[11] = puncChars / chars; //Percentage of punctuation characters
        surfaceVector[12] = (double) posArray[8] / chars; //Percentage of chars that are ':', ';', '...'
        surfaceVector[13] = (double) posArray[5] / chars; //Percentage of chars that are ','
        //Not using word tokens/words

        //Readability metrics
        surfaceVector[14] = 206.835 - 1.015 * (words / sentences) - 84.6 * (syllables / words); //Flesch-Kincaid Reading Ease
        surfaceVector[15] = 11.8 * (syllables / words) + 0.39 * (words / sentences) - 15.59; //Flesch-Kincaid Grade Level
        surfaceVector[16] = (words / sentences) + (double) sBlock.getLongWords() / words; //Gunning-Fog Index
        surfaceVector[17] = 5.89 * (chars / words) - 0.3 * (sentences / words) / 100.0 - 15.8; //Coleman-Liau Formula
        surfaceVector[18] = 4.71 * (chars / words) + 0.5 * (words / sentences) - 21.43; //Automated Readability Index
        surfaceVector[19] = (words / sentences) + 100.0 * ((double) sBlock.getSixCharWords() / words); // Lix Formula
        surfaceVector[20] = 3 + Math.sqrt((double) sBlock.getLongWords() * 30 / sentences); //SMOG Index
    }

    public void loadSentimentFeatures(ADSentenceBlock sBlock) {
        double sentences = (double) sBlock.getSentences();
        int[] sentimentArray = sBlock.getSentimentArray();
        for (int i = 0; i < 5; i++) {
            sentimentVector[i] = (double) sentimentArray[i] / sentences;
        }
    }

    public void loadwordClasssFeatures(ADSentenceBlock sBlock) {
        int[] posArray = sBlock.getPosArray();
        double words = (double) sBlock.getWords();
        wordClassVector[0] = (posArray[20] + posArray[21] + posArray[22] + posArray[23]) / words; //Nouns
        wordClassVector[1] = (posArray[35] + posArray[36] + posArray[37] + posArray[38] + posArray[39] + posArray[40]) / words; //Verbs
        wordClassVector[2] = (posArray[15] + posArray[16] + posArray[17]) / words; //Adjectives
        wordClassVector[3] = (posArray[28] + posArray[29] + posArray[30]) / words; //Adverbs
        wordClassVector[4] = (posArray[26] + posArray[27] + posArray[42] + posArray[43]) / words; //Pronouns
        wordClassVector[5] = (posArray[14]) / words; //Prepositions
        wordClassVector[6] = (posArray[9]) / words; //Conjunctions
        wordClassVector[7] = (posArray[11]) / words; //Determiner
        wordClassVector[8] = (posArray[41] + posArray[42] + posArray[43] + posArray[44]) / words; //WH-words
        wordClassVector[1] = (posArray[35] + posArray[36] + posArray[37] + posArray[38] + posArray[39] + posArray[40]) / words; //Verbs
    }

}
