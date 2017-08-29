import entities.ADSentenceBlock;

import java.io.File;

public class SlidingWindow {
    public static void slidingWindow(File srcFile, int windowSize, ADSentenceBlock sum) {
        if (sum == null) {
            sum = getSum(srcFile);
        }
    }

    public static ADSentenceBlock getSum(File srcFile) {
        return null;
    }
}
