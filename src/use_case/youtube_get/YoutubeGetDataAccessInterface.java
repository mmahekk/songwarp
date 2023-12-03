package use_case.youtube_get;

import org.json.JSONArray;
import org.json.JSONObject;

public interface YoutubeGetDataAccessInterface {
    JSONObject getPlaylistJSON(String youtubePlaylistID);  // makes request to youtube to get a playlist json

    JSONArray getAllPlaylist(JSONObject jsonObject, String playlistID);
}
