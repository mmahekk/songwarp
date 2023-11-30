package use_case.youtube_get;

import data_access.APIs.YoutubeAPI;
import entity.YoutubePlaylist;
import org.json.JSONArray;
import org.json.JSONObject;

public interface YoutubeGetDataAccessInterface {
    JSONObject getPlaylistJSON(YoutubeAPI api, String youtubePlaylistID);  // makes request to youtube to get a playlist json

    JSONArray getAllPlaylist(YoutubeAPI api, JSONObject jsonObject, String playlistID);

    YoutubePlaylist buildYoutubePlaylist(JSONArray songList, String playlistId);
}
