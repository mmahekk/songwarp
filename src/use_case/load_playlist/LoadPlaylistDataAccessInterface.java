package use_case.load_playlist;

import entity.CompletePlaylist;
import entity.Playlist;
import entity.SpotifyPlaylist;
import entity.YoutubePlaylist;

public interface LoadPlaylistDataAccessInterface {

    void SetFilePath(String filePath);
    String FetchFilePath();
    String GetFilePath();
//    Playlist LoadPlaylist(String file);
    YoutubePlaylist LoadYoutubePlaylist(String file);
    SpotifyPlaylist LoadSpotifyPlaylist(String file);
    CompletePlaylist LoadCompletePlaylist(String file);
    String Type(String file);
}
