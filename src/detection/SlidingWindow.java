package detection;

import com.sun.xml.internal.ws.api.message.ExceptionHasMessage;
import entities.ADSentenceBlock;
import entities.ADVector;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Stream;

public class SlidingWindow {

    public static void slidingWindow(String srcFilePath, String outVectorsFilePath, String outDistancesFilePath, int windowSize, ADSentenceBlock sumBlock, double[][] normVector) throws IOException {
//        System.out.println(sumBlock.toCSVLine());
        ADSentenceBlock windowBlock = new ADSentenceBlock(-1, "");
        ADSentenceBlock[] window = new ADSentenceBlock[windowSize];
        int windowPointer;

        //System.out.println(ADVector.getCSVHeader());

        try (
                BufferedReader br = new BufferedReader(new FileReader(srcFilePath));
                BufferedWriter vectorBw = new BufferedWriter(new FileWriter(outVectorsFilePath));
                BufferedWriter distanceBw = new BufferedWriter(new FileWriter(outDistancesFilePath))
        ) {
            String line;
            for (windowPointer = 0; windowPointer < windowSize; windowPointer++) {
                if ((line = br.readLine()) != null) {
                    if (line.equals(ADSentenceBlock.getCSVHeader())) {
                        windowPointer--;
                        continue;
                    }
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

            vectorBw.write(ADVector.getCSVHeader());
            vectorBw.newLine();
            //distanceBw.write(ADVector.getCSVHeader() + ", distance");
            distanceBw.write(ADVector.getShortHeader());
            distanceBw.newLine();

            String text = "";
            for (int i = 0; i < windowSize; i++) {
                text += window[(windowPointer + i) % windowSize].getFirstSentence();
            }
            windowBlock.setFirstSentence(text);

            ADVector windowVector = new ADVector(windowBlock);
            ADVector sumVector = new ADVector(sumBlock);
            windowVector.normalize(normVector); //normalize
            sumVector.normalize(normVector);

            vectorBw.write(windowVector.toCSVLine());
            vectorBw.newLine();
            //distanceBw.write(windowVector.differenceToCSVLine(sumVector));
            distanceBw.write(windowVector.cosineDistanceToCSVLine(sumVector));
            distanceBw.newLine();


            while ((line = br.readLine()) != null) {
                windowBlock.decrease(window[windowPointer]);
                sumBlock.increase(window[windowPointer]);

                window[windowPointer].loadCSVLine(line);
                windowBlock.setId(window[(windowPointer + 1) % windowSize].getId());
                windowBlock.setHeader(window[(windowPointer + 1) % windowSize].getHeader());

                windowBlock.increase(window[windowPointer]);
                sumBlock.decrease(window[windowPointer]);

                windowPointer = (windowPointer + 1) % windowSize;
                text = "";
                for (int i = 0; i < windowSize; i++) {
                    text += window[(windowPointer + i) % windowSize].getFirstSentence();
                }
                windowBlock.setFirstSentence(text);

                windowVector.loadSentenceBlock(windowBlock);
                sumVector.loadSentenceBlock(sumBlock);
                windowVector.normalize(normVector); //normalize
                sumVector.normalize(normVector);

                vectorBw.write(windowVector.toCSVLine());
                vectorBw.newLine();
                //distanceBw.write(windowVector.differenceToCSVLine(sumVector));
                distanceBw.write(windowVector.cosineDistanceToCSVLine(sumVector));
                distanceBw.newLine();
            }

            for (windowPointer = 0; windowPointer < windowSize; windowPointer++) {
                sumBlock.increase(window[windowPointer]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public static void slidingWindow(String srcFilePath, String outVectorsFilePath, int windowSize, ADSentenceBlock sumBlock, double[][] normVector) throws IOException {
        //System.out.println(sumBlock.toCSVLine());
        ADSentenceBlock windowBlock = new ADSentenceBlock(-1, "");
        ADSentenceBlock[] window = new ADSentenceBlock[windowSize];
        int windowPointer;

        //System.out.println(ADVector.getCSVHeader());

        try (
                BufferedReader br = new BufferedReader(new FileReader(srcFilePath));
                BufferedWriter vectorBw = new BufferedWriter(new FileWriter(outVectorsFilePath))
        ) {
            String line;
            for (windowPointer = 0; windowPointer < windowSize; windowPointer++) {
                if ((line = br.readLine()) != null) {
                    if (line.equals(ADSentenceBlock.getCSVHeader())) {
                        windowPointer--;
                        continue;
                    }
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

            vectorBw.write(ADVector.getCSVHeader());
            vectorBw.newLine();

            String text = "";
            for (int i = 0; i < windowSize; i++) {
                text += window[(windowPointer + i) % windowSize].getFirstSentence();
            }
            windowBlock.setFirstSentence(text);

            ADVector windowVector = new ADVector(windowBlock);
            ADVector sumVector = new ADVector(sumBlock);
            windowVector.normalize(normVector); //normalize
            sumVector.normalize(normVector);

            vectorBw.write(windowVector.toCSVLine());
            vectorBw.newLine();


            while ((line = br.readLine()) != null) {
                windowBlock.decrease(window[windowPointer]);
                sumBlock.increase(window[windowPointer]);

                window[windowPointer].loadCSVLine(line);
                windowBlock.setId(window[(windowPointer + 1) % windowSize].getId());
                windowBlock.setHeader(window[(windowPointer + 1) % windowSize].getHeader());

                windowBlock.increase(window[windowPointer]);
                sumBlock.decrease(window[windowPointer]);

                windowPointer = (windowPointer + 1) % windowSize;
                text = "";
                for (int i = 0; i < windowSize; i++) {
                    text += window[(windowPointer + i) % windowSize].getFirstSentence();
                }
                windowBlock.setFirstSentence(text);

                windowVector.loadSentenceBlock(windowBlock);
                sumVector.loadSentenceBlock(sumBlock);
                windowVector.normalize(normVector); //normalize
                sumVector.normalize(normVector);

                vectorBw.write(windowVector.toCSVLine());
                vectorBw.newLine();
            }

            for (windowPointer = 0; windowPointer < windowSize; windowPointer++) {
                sumBlock.increase(window[windowPointer]);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException();
        }
    }

    public static ADSentenceBlock getSum(String srcFilePath) {
        ADSentenceBlock sum = new ADSentenceBlock(0, "sum");
        ADSentenceBlock lineBlock = new ADSentenceBlock(-1, "");
        try (BufferedReader br = new BufferedReader(new FileReader(srcFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(ADSentenceBlock.getCSVHeader())) {
                    continue;
                }
                lineBlock.loadCSVLine(line);
                sum.increase(lineBlock);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sum;
    }

    public static double[][] getNormVectors(String srcFilePath) {
        ADSentenceBlock lineBlock = new ADSentenceBlock(-1, "");
        ADVector adVector = new ADVector();
        double[] mins = new double[ADVector.VECTOR_LEN];
        double[] maxes = new double[ADVector.VECTOR_LEN];
        Arrays.fill(mins, Double.MAX_VALUE);
        Arrays.fill(maxes, Double.MIN_VALUE);
        double[] vector;
        try (BufferedReader br = new BufferedReader(new FileReader(srcFilePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.equals(ADSentenceBlock.getCSVHeader())) {
                    continue;
                }
                lineBlock.loadCSVLine(line);
                adVector.loadSentenceBlock(lineBlock);
                vector = adVector.getComputeVector();
                for (int i = 0; i < ADVector.VECTOR_LEN; i++) {
                    mins[i] = vector[i] < mins[i] ? vector[i] : mins[i];
                    maxes[i] = vector[i] > maxes[i] ? vector[i] : maxes[i];
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        double[] dispersions = new double[ADVector.VECTOR_LEN];
        for (int i = 0; i < ADVector.VECTOR_LEN; i++) {
            dispersions[i] = maxes[i] - mins[i];
        }
        return new double[][]{mins, dispersions};
    }
}
