package data_access;

import entity.*;
import org.json.JSONObject;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;

import static data_access.APIs.SpotifyAPI.spotifyAPIRequest;
import static extra_functions.YoutubeTitleInfoExtract.youtubeTitleInfoExtract;

public class YoutubeMatchDataAccessObject implements YoutubeMatchDataAccessInterface {
    @Override
    public SpotifySong findSpotifySongMatch(YoutubeSong song) {
        String[] nameAndAuthor = youtubeTitleInfoExtract(song.getName(), song.getAuthor());
        String firstTryQuery = nameAndAuthor[0] + " " + nameAndAuthor[1];
        String secondTryQuery = nameAndAuthor[2] + " " + nameAndAuthor[3];

        try {
            String data = spotifyAPIRequest("search", firstTryQuery);
            if (data != null) {
                return buildSpotifySong(new JSONObject(data));
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public SpotifySong buildSpotifySong(JSONObject data) {

        return null;
    }

    @Override
    public Pair<CompletePlaylist, Boolean> buildCompletePlaylist(YoutubePlaylist playlist) {
        ArrayList<YoutubeSong> songList = playlist.getYoutubeSongs();
        CompletePlaylist matchedPlaylist = new CompletePlaylist("unknown name", null, playlist.getYoutubeID(), "unknown");
        for (YoutubeSong song : songList) {
            if (song != null) {
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


