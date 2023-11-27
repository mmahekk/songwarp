package utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CheckMultiplePlaylist {

    // Specify the path to the input file
    String inputFile;

    public CheckMultiplePlaylist(String file) {
        this.inputFile = file;
    }

    public boolean check() {
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            int curlyBraceCount = 0;
            int character;

            while ((character = reader.read()) != -1) {
                char currentChar = (char) character;

                if (currentChar == '{') {
                    curlyBraceCount++;
                } else if (currentChar == '}') {
                    curlyBraceCount--;

                    // If the curly brace count is zero, it means we've reached the end of a top-level block
                    if (curlyBraceCount == 0) {
                            return true;
                    }
                }
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
