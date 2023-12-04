package use_case.spotify_match;

import data_access.SpotifyMatchDataAccessObject;
import entity.*;
import entity.SpotifySong;
import org.json.JSONObject;

import java.io.IOException;

public interface SpotifyMatchDataAccessInterface {
    YoutubeSong findYouTubeSongMatch(SpotifySong song) throws IOException, InterruptedException;

    YoutubeSong buildYouTubeSong(JSONObject data);

    SpotifyMatchDataAccessObject.Pair<CompletePlaylist, Boolean> buildCompletePlaylist(SpotifyPlaylist playlist, CompletePlaylist incompletePlaylist, int songLimit);


//    public void addProgressListener(ProgressListener listener);
//
//    public void removeProgressListener(ProgressListener listener);
}

