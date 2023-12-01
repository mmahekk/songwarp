package use_case.spotify_get;

import data_access.APIs.SpotifyAPIAdapter;
import data_access.TempFileWriterDataAccessObject;
import entity.SpotifyPlaylist;
import org.json.JSONObject;


public class SpotifyGetInteractor implements SpotifyGetInputBoundary {

    final SpotifyGetDataAccessInterface spotifyGetDataAccessObject;
    final TempFileWriterDataAccessObject fileWriter;
    final SpotifyGetOutputBoundary spotifyGetPresenter;

    public SpotifyGetInteractor(SpotifyGetDataAccessInterface spotifyGetDataAccessInterface,
                                TempFileWriterDataAccessObject fileWriter, SpotifyGetOutputBoundary spotifyGetOutputBoundary) {
        this.spotifyGetDataAccessObject = spotifyGetDataAccessInterface;
        this.fileWriter = fileWriter;
        this.spotifyGetPresenter = spotifyGetOutputBoundary;
    }


    public void execute(SpotifyGetInputData spotifyGetInputData){

        String id = spotifyGetInputData.getId();

        if (id != null) {
            SpotifyAPIAdapter api = new SpotifyAPIAdapter();

            JSONObject jsonFile = spotifyGetDataAccessObject.getPlaylistJSON(api, id);

            if (!jsonFile.has("error")) {
                System.out.println(jsonFile);
                // build youtubePlaylist object from json (DAO request 2)
                SpotifyPlaylist spotifyPlaylist = spotifyGetDataAccessObject.buildSpotifyPlaylist(jsonFile, id);

                // store instance in project temp save file (DAO request 3)
                fileWriter.writePlaylistFile(spotifyPlaylist);

                // invoke presenter
                SpotifyGetOutputData spotifyGetOutputData = new SpotifyGetOutputData(spotifyPlaylist, false);
                spotifyGetPresenter.prepareSuccessView(spotifyGetOutputData);
            } else {
                String errorMessage = "Error: " + jsonFile.getJSONObject("error").getString("message");
                spotifyGetPresenter.prepareFailView(errorMessage);
            }
        } else {
            spotifyGetPresenter.prepareFailView("Invalid Spotify Playlist Url");
        }
    }
}
