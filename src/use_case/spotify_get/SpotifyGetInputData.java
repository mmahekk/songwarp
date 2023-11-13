package use_case.spotify_get;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SpotifyGetInputData {

    final private String url;
    final private String id;


    public SpotifyGetInputData(String url){
        this.url = url;
        this.id = this.extractId(url);
    }


    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }


    public String extractId(String playlistUrl) {

        if (playlistUrl != null){
            int lastIndex = url.lastIndexOf("/");

            return url.substring(lastIndex + 1);
        } else {
            return "Error, enter a valid url.";
        }

    }
}
