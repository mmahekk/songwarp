package data_access;

import data_access.APIs.InputSpotifyAPI;
import entity.*;
import org.json.JSONObject;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static data_access.APIs.SpotifyAPI.spotifyAPIRequest;
import static utilities.SearchQueryEncoder.encodeSearchQuery;
import static utilities.YoutubeTitleInfoExtract.youtubeTitleInfoExtract;

public class YoutubeMatchDataAccessObject implements YoutubeMatchDataAccessInterface {
    @Override
    public SpotifySong findSpotifySongMatch(YoutubeSong song) {
        String[] nameAndAuthor = youtubeTitleInfoExtract(song.getName(), song.getAuthor());
        String firstTryQuery = nameAndAuthor[0] + " " + nameAndAuthor[1];
        String secondTryQuery = (nameAndAuthor[2] + " " + nameAndAuthor[3]).replaceAll("null", "");
        System.out.println(firstTryQuery);
        try {
            InputSpotifyAPI info = new InputSpotifyAPI();
            info.setApiCall("searchSong");
            info.setItemInfo(new String[]{encodeSearchQuery(firstTryQuery)});

            String data = spotifyAPIRequest(info);
            if (data != null && !data.isEmpty()) {
                SpotifySong newSong = buildSpotifySong(new JSONObject(data));
                if (!firstTryQuery.contains(newSong.getAuthor().toLowerCase())) {
                    info.setItemInfo(new String[]{encodeSearchQuery(firstTryQuery + " " + secondTryQuery)});
                    String secondData = spotifyAPIRequest(info);
                    if (secondData != null) {
                        newSong = buildSpotifySong(new JSONObject(secondData));
                    }
                }

                return newSong;
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public SpotifySong buildSpotifySong(JSONObject data) {
        if (data.has("tracks")) {
            JSONObject topSearchResults = data.getJSONObject("tracks").getJSONArray("items").getJSONObject(0);
            String id = topSearchResults.getString("id");
            String name = topSearchResults.getString("name");
            int duration = topSearchResults.getInt("duration_ms");
            JSONObject album = topSearchResults.getJSONObject("album");
            String date = album.getString("release_date");
            String author = album.getJSONArray("artists").getJSONObject(0).getString("name");

            SpotifySong song = new SpotifySong(name, author, duration, id, date);
            System.out.println(song.convertToJSON());
            return song;
        }
        return null;
    }


    @Override
    public Pair<CompletePlaylist, Boolean> buildCompletePlaylist(YoutubePlaylist playlist, CompletePlaylist incompletePlaylist, int songLimit) {
        ArrayList<YoutubeSong> songList = playlist.getYoutubeSongs();
        CompletePlaylist matchedPlaylist = Objects.requireNonNullElseGet(incompletePlaylist, () ->
                new CompletePlaylist("unknown name", null, playlist.getYoutubeID(), "unknown"));

        int offset;
        if (matchedPlaylist.getTotal() < songList.size()) {
            offset = matchedPlaylist.getTotal();
        } else {
            offset = 0;
        }

        for (int i = offset; i < songList.size(); i++) {
            YoutubeSong song = songList.get(i);
            if (song != null) {
                if (songLimit != -1 && matchedPlaylist.getTotal() >= songLimit) {
                    return new Pair<>(matchedPlaylist, false);
                }
                SpotifySong matchedSong = findSpotifySongMatch(song);
                if (matchedSong != null) {
                    CompleteSong completeSong = new CompleteSong(
                            matchedSong.getName(), matchedSong.getAuthor(), matchedSong.getSpotifyID(),
                            song.getYoutubeID(), matchedSong.getDate(),
                            song.getName(), song.getAuthor(), matchedSong.getDuration());

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


