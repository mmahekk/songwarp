package data_access;

import data_access.APIs.APIcaller;
import data_access.APIs.InputAPI;
import data_access.APIs.YoutubeAPI;
import entity.CompletePlaylist;
import entity.CompleteSong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.spotify_put.SpotifyPutDataAccessInterface;

import java.io.IOException;

public class SpotifyPutDataAccessObject implements SpotifyPutDataAccessInterface {
    @Override
    public String getUserAuthorization(YoutubeAPI api) {
        return api.getUserAuthAccessToken();
    }

    @Override
    public String initializeYoutubePlaylist(YoutubeAPI api, String playlistName, String spotifyUrl, String token) throws IOException, InterruptedException {
        InputAPI info = new InputAPI();
        info.setItemInfo(new String[]{playlistName, spotifyUrl});
        info.setApiCall("createPlaylist");
        info.setPremadeToken(token);
        String response = api.request(info);
        if (response != null) {
            JSONObject jsonObject = new JSONObject(response);
            if (jsonObject.has("id")) {  // we want to also return the youtube playlist id
                return jsonObject.getString("id");
            }
        }
        return null;
    }

    @Override
    public void uploadSongs(YoutubeAPI api, String youtubePlaylistID, CompletePlaylist playlist, String token, int offset) throws IOException, InterruptedException {
        for (int i = offset; i < playlist.getTotal(); i++) {  // because request can only handle up to 100 songs at a time
            CompleteSong song = playlist.getCompleteSongs().get(i);

            InputAPI info = new InputAPI();
            info.setItemInfo(new String[]{youtubePlaylistID, song.getYoutubeId()});
            info.setPremadeToken(token);
            info.setApiCall("addSongToPlaylist");
            api.request(info);
        }
    }

    @Override
    public int getExistingPlaylistOffset(YoutubeAPI api, String youtubePlaylistID) {
        InputAPI input = new InputAPI();
        input.setApiCall("getPlaylist");
        input.setItemInfo(new String[]{youtubePlaylistID});
        try {
            String data = api.request(input);
            JSONObject response = new JSONObject(data);
            if (response.has("items")) {
                JSONArray list = response.getJSONArray("items");
                return list.length();
            }
        } catch (IOException e) {
            System.out.println("couldn't find an existing playlist to work off");
        }
        return 0;
    }
}
