package use_case.youtube_get;

import entity.YoutubePlaylist;

public interface YoutubeGetDataAccessInterface {
    String getPlaylistJSON(String youtubePlaylistID);  // makes request to youtube to get a playlist json
    YoutubePlaylist buildYoutubePlaylist(String youtubePlaylistJSON, String playlistId);
}
