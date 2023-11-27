package data_access;

import data_access.APIs.InputSpotifyAPI;
import entity.CompletePlaylist;
import entity.CompleteSong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.spotify_put.SpotifyPutDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static data_access.APIs.YoutubeAPI.getUserAuthAccessToken;
import static data_access.APIs.SpotifyAPI.spotifyAPIRequest;
import static java.lang.Math.min;

public class SpotifyPutDataAccessObject implements SpotifyPutDataAccessInterface {
    @Override
    public String getUserAuthorization() {
        return getUserAuthAccessToken();
    }

    @Override
    public String getUserID(String token) throws IOException, InterruptedException {
        InputSpotifyAPI info = new InputSpotifyAPI();
        info.setPremadeToken(token);
        info.setApiCall("getUser");
        String response = spotifyAPIRequest(info);
        assert response != null;
        JSONObject jsonObject = new JSONObject(response);
        return jsonObject.getString("id");
    }

    @Override
    public String initializeYoutubePlaylist(String userID, String playlistName, String spotifyUrl, String token) throws IOException, InterruptedException {
        InputSpotifyAPI info = new InputSpotifyAPI();
        info.setItemInfo(new String[]{userID, playlistName, spotifyUrl});
        info.setApiCall("createPlaylist");
        info.setPremadeToken(token);
        String response = spotifyAPIRequest(info);
        assert response != null;
        JSONObject jsonObject = new JSONObject(response);
        if (jsonObject.has("uri")) {  // we want to also return the spotify playlist id
            String[] parts = jsonObject.getString("uri").split(":");
            return parts[parts.length - 1];
        }
        return null;
    }

    @Override
    public void uploadSongs(String youtubePlaylistID, CompletePlaylist playlist, String token, int offset) throws IOException, InterruptedException {
        for (int i = offset; i < playlist.getTotal(); i++) {  // because request can only handle up to 100 songs at a time
            CompleteSong song = playlist.getCompleteSongs().get(i);
            InputSpotifyAPI info = new InputSpotifyAPI();
            info.setItemInfo(new String[]{youtubePlaylistID, song.getYoutubeId()});
            info.setPremadeToken(token);
            info.setApiCall("addSongToPlaylist");
            spotifyAPIRequest(info);
        }
    }
}
