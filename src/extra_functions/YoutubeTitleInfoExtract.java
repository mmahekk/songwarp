package extra_functions;

import java.util.ArrayList;
import java.util.List;

public class YoutubeTitleInfoExtract {
    public static String[] youtubeTitleInfoExtract(String title, String channel) {
        // set up
        title = title.toLowerCase();
        channel = extractAuthorFromChannel(channel.toLowerCase());;
        String name;
        String author = null;
        if (title.contains(channel)) {
            author = channel;
        }
        String name_alt = null;
        String author_alt = null;

        // remove weird characters
        String[] strangeChars = {"@", "#"};
        for (String character : strangeChars) {
            title = title.replaceAll(character, "");
        }

        // split up title
        String[] dividers = {" - ", "- ", " by ", "\\/ ", "\\| ", ": ", " ~ ", "~ "};
        String[] splitTitle = {};
        for (String divider : dividers) {
            if (title.contains(divider)) {
                splitTitle = title.split(divider);
            }
        }

        // extract name and author
        if (splitTitle.length > 2) {
            List<String> filteredList = new ArrayList<>();

            // Iterate through the array and filter strings not completely in brackets
            for (String str : splitTitle) {
                String potential = str.trim();
                if (!potential.matches("\\[.*\\]") && !potential.matches("\\(.*\\)")) {
                    filteredList.add(str);
                }
            }
            // Convert the list back to an array
            splitTitle = filteredList.toArray(new String[0]);
        }

        if (splitTitle.length == 0) {
            name = title;
            author = channel;
        } else {
            name = extractNameFromTitle(splitTitle[1]);
            name_alt = extractNameFromTitle(splitTitle[0]);
            if (name.contains("#@#")) {
                String holdVal = name;
                name = holdVal.split("#@#")[0];
                if (author == null) {
                    author = holdVal.split("#@#")[1];
                }
            }
            if (name_alt.contains("#@#")) {
                String holdVal = name_alt;
                name_alt = holdVal.split("#@#")[0];
                author_alt = holdVal.split("#@#")[1];
            }
            if (author == null) {
                String possible_author = extractAuthorFromTitle(splitTitle[0]);
                String possible_author_alt = extractAuthorFromTitle(splitTitle[1]);
                if (channel.equals(possible_author)) {
                    author = possible_author;
                    author_alt = possible_author;
                } else if (channel.equals(possible_author_alt)) {
                    author = possible_author_alt;
                    author_alt = possible_author_alt;
                } else {
                    author = possible_author;
                    author_alt = possible_author_alt;
                }
            }
        }

        String[] return_val = new String[4];
        String[] bracketsExcess = {"[", "(", ")", "]"};
        for (String str : bracketsExcess) {
            name = name.replace(str, "");
        }
        return_val[0] = name;
        return_val[1] = author;
        return_val[2] = name_alt;
        return_val[3] = author_alt;
        return return_val;
    }

    private static String extractAuthorFromChannel(String channel) {
        String[] excessStrings = {"vevo", "official", " - topic"};
        for (String excessString : excessStrings) {
            if (channel.endsWith(excessString)) {
                channel = channel.replace(excessString, "");
            }
        }
        return channel;
    }

    private static String extractAuthorFromTitle(String title) {
        String[] collabIndicators = {" & ", " x ", " + ", ", "};
        for (String collabIndicator : collabIndicators) {
            if (title.contains(collabIndicator)) {
                title = title.split(collabIndicator)[0];
            }
        }
        return title;
    }

    private static String extractNameFromTitle(String title) {
        String remixAuthor = null;
        String[] excessStrings = {
                "lyrics", " 4k ", "hd", "lyrical", "audio", "version", "animated", "edit", "release",
                "lyric", "ost", "feat. ", "ft.", "music video", "official", "video", "soundtrack"};
        for (String str: excessStrings) {
            title = title.replace(str, "");
        }
        String[] splitTitle = splitStringWithBrackets(title);
        String[] newAuthorIndicators = {"remix", "remastered", "relift", "tribute", "cover", "bootleg", "remake"};

        for (String s : splitTitle) {
            for (String str : newAuthorIndicators) {
                if (s.contains(str)) {
                    remixAuthor = s.replace(str, " ");
                }
            }
        }
        if (remixAuthor != null && !remixAuthor.isEmpty()) {
            return splitTitle[0] + "#@# " + remixAuthor;
        } else {
            return splitTitle[0];
        }
    }

    private static String[] splitStringWithBrackets(String str) {
        // Find the index of the opening and closing parentheses
        int openIndex = str.indexOf('(');
        int closeIndex = str.indexOf(')');

        if (openIndex != -1 && closeIndex != -1) {
            // Extract the parts based on the indexes
            String notInBrackets = str.substring(0, openIndex).trim() + str.substring(closeIndex).trim();
            String inBrackets = str.substring(openIndex + 1, closeIndex).trim();

            return new String[]{notInBrackets, inBrackets};
        } else {
            return new String[]{str};
        }
    }
}
