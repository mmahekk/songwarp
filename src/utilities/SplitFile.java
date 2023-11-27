package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SplitFile {

        // Specify the path to the input file
        String inputFile;

        // Specify the paths for the two output JSON files
        String outputFile1 = "CompletePlaylist.json";
        String outputFile2 = "IncompletePlaylist.json";

        public SplitFile(String inputFile) {this.inputFile = inputFile;}
        public void splitFile() {

            try (BufferedReader reader = new BufferedReader(new FileReader(inputFile));
                 BufferedWriter writer1 = new BufferedWriter(new FileWriter(outputFile1));
                 BufferedWriter writer2 = new BufferedWriter(new FileWriter(outputFile2))) {

                StringBuilder currentBlock = new StringBuilder();
                int curlyBraceCount = 0;
                boolean writeToOutput1 = true;

                int character;
                while ((character = reader.read()) != -1) {
                    char currentChar = (char) character;

                    currentBlock.append(currentChar);

                    if (currentChar == '{') {
                        curlyBraceCount++;
                    } else if (currentChar == '}') {
                        curlyBraceCount--;

                        // If the curly brace count is zero, it means we've reached the end of a top-level block
                        if (curlyBraceCount == 0) {
                            // Decide which output file to write to based on the current block
                            if (writeToOutput1) {
                                writeToJsonFile(currentBlock.toString(), writer1);
                            } else {
                                writeToJsonFile(currentBlock.toString(), writer2);
                            }

                            // Reset variables for the next block
                            currentBlock.setLength(0);
                            writeToOutput1 = !writeToOutput1;
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    private static void writeToJsonFile(String jsonContent, BufferedWriter writer) throws IOException {
        // If the content is not empty, write it to the JSON file
        if (!jsonContent.trim().isEmpty()) {
            writer.write(jsonContent);
            writer.newLine();
        }
    }
}
