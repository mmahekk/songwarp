package data_access.APIs;

public interface YoutubeAPIAdapterInterface {
    String request(APIRequestInfo info);
    String getPlaylist(String playlistID, String pageToken);
    String searchSong(String query);
    String createPlaylist(String name, String relatedUrl, String authKey);
    void addSongToPlaylist(String playlistID, String songID, String authKey);
    String getUserAuthAccessToken();
}
