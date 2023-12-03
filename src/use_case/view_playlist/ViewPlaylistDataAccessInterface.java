package use_case.view_playlist;

import entity.Playlist;

import java.util.List;

public interface ViewPlaylistDataAccessInterface {

    //Gets the playlist data and returns a list of strings, each string including song information
    // to be displayed
    List<String> getPlaylistData(Playlist playlist);

}