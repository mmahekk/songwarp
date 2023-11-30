package use_case.youtube_match;

import data_access.APIs.SpotifyAPI;
import data_access.YoutubeMatchDataAccessObject;
import entity.CompletePlaylist;
import entity.SpotifySong;
import entity.YoutubePlaylist;
import entity.YoutubeSong;
import interface_adapter.ProgressListener;
import org.json.JSONObject;

import java.io.IOException;

public interface YoutubeMatchDataAccessInterface {
    SpotifySong findSpotifySongMatch(SpotifyAPI api, YoutubeSong song) throws IOException;

    SpotifySong buildSpotifySong(JSONObject data);

    YoutubeMatchDataAccessObject.Pair<CompletePlaylist, Boolean> buildCompletePlaylist(SpotifyAPI api, YoutubePlaylist playlist, CompletePlaylist incompletePlaylist, int songLimit);

    public void addProgressListener(ProgressListener listener);

    public void removeProgressListener(ProgressListener listener);
}

