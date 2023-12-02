package use_case.spotify_match;

import data_access.APIs.YoutubeAPIAdapter;
import data_access.SpotifyMatchDataAccessObject;
import entity.*;
import entity.SpotifySong;
import interface_adapter.ProgressListener;
import org.json.JSONObject;

import java.io.IOException;

public interface SpotifyMatchDataAccessInterface {
    YoutubeSong findYouTubeSongMatch(YoutubeAPIAdapter api, SpotifySong song) throws IOException, InterruptedException;

    YoutubeSong buildYouTubeSong(JSONObject data);


//    public void addProgressListener(ProgressListener listener);
//
//    public void removeProgressListener(ProgressListener listener);
}

