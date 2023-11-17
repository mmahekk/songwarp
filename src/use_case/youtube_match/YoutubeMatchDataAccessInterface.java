package use_case.youtube_match;

import data_access.YoutubeMatchDataAccessObject;
import entity.CompletePlaylist;
import entity.SpotifySong;
import entity.YoutubePlaylist;
import entity.YoutubeSong;
import org.json.JSONObject;

import java.io.IOException;

public interface YoutubeMatchDataAccessInterface {
    SpotifySong findSpotifySongMatch(YoutubeSong song) throws IOException, InterruptedException;

    YoutubeMatchDataAccessObject.Pair<CompletePlaylist, Boolean> buildCompletePlaylist(YoutubePlaylist playlist);
}
