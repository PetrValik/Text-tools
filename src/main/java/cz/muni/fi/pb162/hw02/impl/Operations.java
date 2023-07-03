package cz.muni.fi.pb162.hw02.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Class contain all operations with lines
 *
 * @author Petr Valik
 */

public class Operations{
    public List<String> linesList;

    /**
     * Operations class constructor
     * @param input contain path to list of strings
     * @exception IOException when path is invalid
     * @author Petr Valik
     */
    public Operations(String input) throws IOException {
        this.linesList = new ArrayList<>(Files.readAllLines(Paths.get(input)));
    }

    /**
     * Remove duplicities from linesList
     * @author Petr Valik
     */
    void unique() {
        List<String> uniqueLinesList = new ArrayList<>();
        for (String line : this.linesList) {
            if (!(uniqueLinesList.contains(line))) {
                uniqueLinesList.add(line);
            }
        }
        this.linesList = uniqueLinesList;
    }

    /**
     * sort string(lines) in linesList
     * @author Petr Valik
     */
    public void sort() {
        Collections.sort(this.linesList);
    }

    /**
     * Remove non duplicities from linesList
     * @author Petr Valik
     */
    public void duplicates() {
        List<String> duplicateLines = new ArrayList<>();
        List<String> readLines = new ArrayList<>();

        for (String line : this.linesList) {
            if (readLines.contains(line)) {
                duplicateLines.add(line);
            }
            readLines.add(line);
        }
        this.linesList = duplicateLines;
    }

    /**
     * Print number of lines in linesList
     * @author Petr Valik
     */
    public void count() {
         System.out.println(this.linesList.size());
    }

    /**
     * Print length of every line in format = length: line
     * @author Petr Valik
     */
    public void sizes() {
        for (String line : this.linesList) {
            System.out.println(line.length() + ": " + line);
        }
    }

    /**
     * Search for most similar lines in linesList(according Levenshtein distance)
     * @author Petr Valik
     */
    public void similar() {
        List<String> similarLines = new ArrayList<>();
        this.unique();
        int levenshteinDistance = Integer.MAX_VALUE;
        int actualDistance;

        for (int j = 0; j < this.linesList.size(); j++) {
            for (int i = j + 1; i < this.linesList.size(); i++) {
                actualDistance = Levenshtein.compute_Levenshtein_distance(this.linesList.get(j),
                                this.linesList.get(i));

                if (levenshteinDistance > actualDistance) {
                    levenshteinDistance = actualDistance;
                    similarLines.clear();
                    similarLines.add(this.linesList.get(j));
                    similarLines.add(this.linesList.get(i));
                }

                if (levenshteinDistance == actualDistance) {
                    similarLines.add(this.linesList.get(j));
                    similarLines.add(this.linesList.get(i));
                }
            }
        }
        this.linesList = similarLines;
        this.similarPrint(levenshteinDistance);
    }

    /**
     * Sprint most similar lines in linesList(according Levenshtein distance)
     * @param levenshteinDistance length of found levenshteinDistance
     * @author Petr Valik
     */
        public void similarPrint(int levenshteinDistance) {
            System.out.println("Distance of " + levenshteinDistance);
            for (int i = 0; i < this.linesList.size(); i += 2) {
                System.out.println(this.linesList.get(i) + " ~= " + this.linesList.get(i + 1));
        }
    }

    /**
     * print linesList
     * @author Petr Valik
     */
    public void print() {
        for (String line : this.linesList) {
            System.out.println(line);
        }
    }
}
