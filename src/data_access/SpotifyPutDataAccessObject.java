package data_access;

import data_access.APIs.YoutubeAPIAdapter;
import entity.CompletePlaylist;
import entity.CompleteSong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.spotify_put.SpotifyPutDataAccessInterface;

import java.io.IOException;

public class SpotifyPutDataAccessObject implements SpotifyPutDataAccessInterface {
    @Override
    public String getUserAuthorization() {
        YoutubeAPIAdapter api = new YoutubeAPIAdapter();
        return api.getUserAuthAccessToken();
    }

    @Override
    public String initializeYoutubePlaylist(String playlistName, String spotifyUrl, String token) throws IOException, InterruptedException {
        YoutubeAPIAdapter api = new YoutubeAPIAdapter();
        String response = api.createPlaylist(playlistName, spotifyUrl, token);
        if (response != null) {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("id")) {  // we want to also return the youtube playlist id
                return jsonObject.getString("id");
            }
        }
        return null;
    }

    @Override
    public void uploadSongs(String youtubePlaylistID, CompletePlaylist playlist, String token, int offset) {
        YoutubeAPIAdapter api = new YoutubeAPIAdapter();
        for (int i = offset; i < playlist.getTotal(); i++) {  // because request can only handle up to 100 songs at a time
            CompleteSong song = playlist.getCompleteSongs().get(i);
            String songID = song.getYoutubeId();
            api.addSongToPlaylist(youtubePlaylistID, songID, token);
        }
    }

    @Override
    public int getExistingPlaylistOffset(String youtubePlaylistID) {
        YoutubeAPIAdapter api = new YoutubeAPIAdapter();
        String data = api.getPlaylist(youtubePlaylistID, null);
        JSONObject response = new JSONObject(data);
        if (response.has("items")) {
            JSONArray list = response.getJSONArray("items");
            return list.length();
        }
        System.out.println("couldn't find an existing playlist to work off");
        return 0;
    }
}
