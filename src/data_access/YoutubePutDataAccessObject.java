package data_access;

import data_access.APIs.InputAPI;
import entity.CompletePlaylist;
import entity.CompleteSong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.youtube_put.YoutubePutDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static data_access.APIs.SpotifyAPI.getUserAuthAccessToken;
import static data_access.APIs.SpotifyAPI.spotifyAPIRequest;
import static java.lang.Math.min;

public class YoutubePutDataAccessObject implements YoutubePutDataAccessInterface {

    @Override
    public String getUserAuthorization() {
        return getUserAuthAccessToken();
    }

    @Override
    public String getUserID(String token) throws IOException, InterruptedException {
        InputAPI info = new InputAPI();
        info.setPremadeToken(token);
        info.setApiCall("getUser");
        String response = spotifyAPIRequest(info);
        assert response != null;
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("id");
    }

    @Override
    public String initializeSpotifyPlaylist(String userID, String playlistName, String youtubeUrl, String token) throws IOException, InterruptedException {
        InputAPI info = new InputAPI();
        info.setItemInfo(new String[]{userID, playlistName, youtubeUrl});  // youtubeUrl here is for the playlist description
        info.setApiCall("createPlaylist");
        info.setPremadeToken(token);
        String response = spotifyAPIRequest(info);
        if (response != null) {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("uri")) {  // we want to also return the spotify playlist id
                String[] parts = jsonObject.getString("uri").split(":");
                return parts[parts.length - 1];
            }
        }
        return null;
    }

    @Override
    public void uploadSongs(String spotifyPlaylistID, CompletePlaylist playlist, String token) throws IOException, InterruptedException {
        for (int i = 0; i < Math.ceil((double) playlist.getTotal() / 100); i++) {  // because request can only handle up to 100 songs at a time
            List<CompleteSong> songs = playlist.getCompleteSongs().subList(i * 100, min(playlist.getTotal(), (i + 1) * 100));
            ArrayList<String> batch = new ArrayList<>();
            for (CompleteSong song : songs) {
                batch.add("spotify:track:" + song.getSpotifyId());
            }

            InputAPI info = new InputAPI();
            info.setItemInfo(new String[]{spotifyPlaylistID});
            info.setListInfo(new JSONArray(batch));
            info.setPremadeToken(token);
            info.setApiCall("addSongsToPlaylist");
            spotifyAPIRequest(info);
        }
    }
}
