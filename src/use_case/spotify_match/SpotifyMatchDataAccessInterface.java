package use_case.spotify_match;

import data_access.SpotifyMatchDataAccessObject;
import entity.CompletePlaylist;
import entity.SpotifySong;
import entity.SpotifyPlaylist;
import entity.SpotifySong;
import interface_adapter.ProgressListener;
import org.json.JSONObject;

import java.io.IOException;

public interface SpotifyMatchDataAccessInterface {
    SpotifySong findSpotifySongMatch(SpotifySong song) throws IOException, InterruptedException;

    SpotifySong buildSpotifySong(JSONObject data);


    public void addProgressListener(ProgressListener listener);

    public void removeProgressListener(ProgressListener listener);
}

