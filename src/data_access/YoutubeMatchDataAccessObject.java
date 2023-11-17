package data_access;

import entity.*;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;

import java.util.ArrayList;

public class YoutubeMatchDataAccessObject implements YoutubeMatchDataAccessInterface {
    @Override
    public SpotifySong findSpotifySongMatch(YoutubeSong song) {
        // TODO: implement with spotify http requests.
        return null;
    }

    @Override
    public Pair<CompletePlaylist, Boolean> buildCompletePlaylist(YoutubePlaylist playlist) {
        ArrayList<Song> songList = playlist.getList();
        CompletePlaylist matchedPlaylist = new CompletePlaylist("unknown name", null);
        for (Song song : songList) {
            if (song instanceof YoutubeSong) {
                SpotifySong matchedSong = findSpotifySongMatch((YoutubeSong) song);

                if (matchedSong != null) {
                    CompleteSong completeSong = new CompleteSong(
                            matchedSong.getName(), matchedSong.getAuthor(), matchedSong.getSpotifyID(),
                            ((YoutubeSong) song).getYoutubeID(), matchedSong.getGenre(), matchedSong.getDate(),
                            song.getName(), song.getAuthor());

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


