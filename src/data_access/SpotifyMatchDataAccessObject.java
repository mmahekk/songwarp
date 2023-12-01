package data_access;

import data_access.APIs.InputAPI;
import data_access.APIs.YoutubeAPI;
import entity.SpotifySong;
import entity.YoutubeSong;
import org.json.JSONObject;

import java.io.IOException;

import static data_access.APIs.YoutubeAPI.youtubeAPIRequest;
import static utilities.SearchQueryEncoder.encodeSearchQuery;

public class SpotifyMatchDataAccessObject {

    public YoutubeSong findYouTubeSongMatch(SpotifySong song) {
        String searchQuery = song.getAuthor() + " - " + song.getName();


        try {
            InputAPI info = new InputAPI();
            info.setApiCall("searchSong");
            info.setItemInfo(new String[]{encodeSearchQuery(searchQuery)});

            String data = youtubeAPIRequest(info);
            System.out.println(data);
            if (data != null && !data.isEmpty()) {
                YoutubeSong newSong = buildYouTubeSong(new JSONObject(data));

                return newSong;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public YoutubeSong buildYouTubeSong(JSONObject data) {
        if (data.has("tracks")) {
            JSONObject topSearchResults = data.getJSONArray("items").getJSONObject(0);
            String id = topSearchResults.getJSONObject("id").getString("videoID");
            String name = topSearchResults.getString("name");
            JSONObject album = topSearchResults.getJSONObject("album");
            String date = album.getString("release_date");
            String author = album.getJSONArray("artists").getJSONObject(0).getString("name");

            YoutubeSong song = new YoutubeSong(name, author, id, date);
            System.out.println(song.convertToJSON());
            return song;
        }
        return null;
    }



}

