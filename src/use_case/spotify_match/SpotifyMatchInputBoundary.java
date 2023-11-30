package use_case.spotify_match;

import use_case.youtube_match.YoutubeMatchInputData;

public interface SpotifyMatchInputBoundary {

    void execute(SpotifyMatchInputData spotifyMatchInputData);
}
