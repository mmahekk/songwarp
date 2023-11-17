import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import java.io.IOException;

import static extra_functions.YoutubeTitleInfoExtract.youtubeTitleInfoExtract;
import static data_access.TempFileWriterDataAccessObject.readTempJSON;

public class TestYoutubeTitleInfoExtract {
    @Test
    public void testYoutubeTitleInfoExtract() throws IOException {
        JSONObject jsonObject = readTempJSON(null, false);
        JSONArray list = jsonObject.getJSONArray("items").getJSONArray(0);
        for (int i = 0; i < list.length(); i++) {
            String title = list.getJSONObject(i).getJSONArray("name").getString(0);
            String channel = list.getJSONObject(i).getJSONArray("author").getString(0);
            String[] info = youtubeTitleInfoExtract(title, channel);
            System.out.println("SONG " + (i + 1) + ": ");
            System.out.println("name: " + info[0]);
            System.out.println("author: " + info[1]);
            System.out.println("OR");
            System.out.println("name: " + info[2]);
            System.out.println("author: " + info[3]);
            System.out.println();
        }
    }
}
