package data_access.APIs;

import org.json.JSONArray;

import java.io.IOException;

public interface SpotifyAPIAdapterInterface {
    String request(APIRequestInfo info);
    String getPlaylist(String playlistID);
    String searchSong(String query);
    String createPlaylist(String userID, String name, String relatedUrl, String key);
    String addSongsToPlaylist(String playlistID, JSONArray songs, String key);
    String getUser(String key);
}
