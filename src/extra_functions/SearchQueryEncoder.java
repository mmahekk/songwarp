package extra_functions;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class SearchQueryEncoder {
    public static String encodeSearchQuery(String query) {
        // Encode the query string using URLEncoder
        return URLEncoder.encode(query, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20") // Replace '+' with '%20'
                .replaceAll("%21", "!")   // Replace '%21' with '!'
                // Add more replacements for other characters if needed
                .replaceAll("%2A", "*")
                .replaceAll("%27", "'")
                .replaceAll("%28", "(")
                .replaceAll("%29", ")")
                .replaceAll("%3B", ";")
                .replaceAll("%3A", ":")
                .replaceAll("%40", "@")
                .replaceAll("%26", "&")
                .replaceAll("%3D", "=")
                .replaceAll("%2B", "+")
                .replaceAll("S", "$")
                .replaceAll("%2C", ",")
                .replaceAll("%3F", "?")
                .replaceAll("%23", "#")
                .replaceAll("%5B", "[")
                .replaceAll("%5D", "]")
                .replaceAll("\\|", "");
    }
}
