package use_case.save_playlist;

import entity.Playlist;

public interface SavePlaylistDataAccessInterface {
    // Gets a playlist from a JSON file
//    Playlist getPlaylist(String filePath);
    // Creates a JSON file from a Playlist object and saves it to the users computer, returning a success message
    // if JSON file is successfully created
    void createJSONFile(SavePlaylistInputData inputData);

}