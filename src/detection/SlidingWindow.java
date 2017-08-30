package detection;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import entities.ADSentenceBlock;
import entities.ADVector;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class SlidingWindow {

    public static void slidingWindow(String srcFilePath, int windowSize, ADSentenceBlock sumBlock) {
        System.out.println(sumBlock.toCSVLine());
        ADSentenceBlock windowBlock = new ADSentenceBlock(-1, "");
        ADSentenceBlock lineBlock = new ADSentenceBlock(-1, "");
        ADSentenceBlock[] window = new ADSentenceBlock[windowSize];
        int windowPointer = 0;

        System.out.println(ADVector.getCSVHeader());

        try (BufferedReader br = new BufferedReader(new FileReader(srcFilePath))) {
            String line;
            for (windowPointer = 0; windowPointer < windowSize; windowPointer++) {
                if ((line = br.readLine()) != null) {
                    window[windowPointer] = new ADSentenceBlock(-1, "");
                    window[windowPointer].loadCSVLine(line);
                    windowBlock.increase(window[windowPointer]);
                    sumBlock.decrease(window[windowPointer]);
                } else {
                    throw new Exception();
                }
            }
            windowPointer = 0;
            windowBlock.setHeader(window[0].getHeader());
            windowBlock.setId(window[0].getId());

            //measureAndWrite(windowBlock, sumBlock);
            System.out.println(windowBlock.toCSVLine());

            while ((line = br.readLine()) != null) {
                windowBlock.decrease(window[windowPointer]);
                sumBlock.increase(window[windowPointer]);

                window[windowPointer].loadCSVLine(line);
                windowBlock.setId(window[(windowPointer + 1) % windowSize].getId());
                windowBlock.setHeader(window[(windowPointer + 1) % windowSize].getHeader());

                windowBlock.increase(window[windowPointer]);
                sumBlock.decrease(window[windowPointer]);

                windowPointer = (windowPointer + 1) % windowSize;
                //measureAndWrite(windowBlock, sumBlock);
                System.out.println(windowBlock.toCSVLine());
            }

            for (windowPointer = 0; windowPointer < windowSize; windowPointer++) {
                sumBlock.increase(window[windowPointer]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void measureAndWrite(ADSentenceBlock windowBlock, ADSentenceBlock sumBlock) {
        ADVector windowVector = new ADVector(windowBlock, false);
        ADVector sumVector = new ADVector(sumBlock, true);
//        double distance = windowVector.manhattanDistanceTo(sumVector);
        System.out.println(windowVector.differenceToCSVLine(sumVector));
//        System.out.println(sumVector.toCSVLine() + ", 0");
    }

    public static ADSentenceBlock getSum(String srcFilePath) {
        ADSentenceBlock sum = new ADSentenceBlock(0, "sum");
        ADSentenceBlock lineBlock = new ADSentenceBlock(-1, "");
        try (BufferedReader br = new BufferedReader(new FileReader(srcFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                lineBlock.loadCSVLine(line);
                sum.increase(lineBlock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }
}
