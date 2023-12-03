package data_access;

import entity.CompletePlaylist;
import entity.SpotifySong;
import entity.YoutubeSong;
import org.json.JSONObject;
import entity.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Objects;
import data_access.APIs.YoutubeAPIAdapter;
import use_case.spotify_match.SpotifyMatchDataAccessInterface;


public class SpotifyMatchDataAccessObject implements SpotifyMatchDataAccessInterface {

    public YoutubeSong findYouTubeSongMatch(YoutubeAPIAdapter api, SpotifySong song) {
        String searchQuery = song.getAuthor() + " - " + song.getName();


        String data = null;
        try {
            data = api.searchSong(URLEncoder.encode(searchQuery, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            data = null;
        }


        if (data != null && !data.isEmpty()) {
            YoutubeSong newSong = buildYouTubeSong(new JSONObject(data));

            return  newSong;
        }

        return null;
    }


    public YoutubeSong buildYouTubeSong(JSONObject data) {
        if (data.has("items")) {

            JSONObject topSearchResults = data.getJSONArray("items").getJSONObject(0);
            String id = topSearchResults.getJSONObject("id").getString("videoId");
            JSONObject snippet = topSearchResults.getJSONObject("snippet");
            String name = snippet.getString("title");
            String date = snippet.getString("publishedAt");
            String author = snippet.getString("channelTitle");

            YoutubeSong song = new YoutubeSong(name, author, id, date);
            return song;
        }
        return null;
    }


    public Pair<CompletePlaylist, Boolean> buildCompletePlaylist(YoutubeAPIAdapter api, SpotifyPlaylist playlist, CompletePlaylist incompletePlaylist, int songLimit){

        ArrayList<SpotifySong> songlist = playlist.getSpotifySongs();
        CompletePlaylist matchedPlaylist = Objects.requireNonNullElseGet(incompletePlaylist, () ->
                new CompletePlaylist("unknown name", null, "unknown", playlist.getSpotifyID()));

        int offset;

        if (matchedPlaylist.getTotal() < songlist.size()) {
            offset = matchedPlaylist.getTotal();
        } else {
            offset = 0;
        }

        for (int i = offset; i < songlist.size(); i++) {
            SpotifySong song = songlist.get(i);
            if (song != null) {
                if (songLimit != -1 && matchedPlaylist.getTotal() >= songLimit) {
                    return new Pair<>(matchedPlaylist, false);
                }
                YoutubeSong matchedSong = findYouTubeSongMatch(api, song);
                if (matchedSong != null) {
                    CompleteSong completeSong = new CompleteSong(song.getName(), song.getAuthor()
                            , song.getSpotifyID()
                            , matchedSong.getYoutubeID(), matchedSong.getDate()
                            ,matchedSong.getName(), matchedSong.getAuthor(), song.getDuration());

                    matchedPlaylist.addSong(completeSong);
                } else {  // we got an http error, i.e. ran out of tokens, therefore, it means we stop building and indicate that it wasn't a completed playlist
                    return new Pair<>(matchedPlaylist, false);
                }
            }

        }
        return new Pair<>(matchedPlaylist, true);




    }

    public record Pair<CompletePlaylist, Boolean>(CompletePlaylist p, Boolean completed) {}



}

