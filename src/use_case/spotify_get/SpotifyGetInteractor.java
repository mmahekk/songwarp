package use_case.spotify_get;

import data_access.TempPlaylistDataAccessObject;
import entity.SpotifyPlaylist;

import java.io.IOException;


public class SpotifyGetInteractor implements SpotifyGetInputBoundary {

    final SpotifyGetDataAccessInterface spotifyGetDataAccessObject;
    final TempPlaylistDataAccessObject fileWriter;
    final SpotifyGetOutputBoundary spotifyGetPresenter;

    public SpotifyGetInteractor(SpotifyGetDataAccessInterface spotifyGetDataAccessInterface,
                                TempPlaylistDataAccessObject fileWriter, SpotifyGetOutputBoundary spotifyGetOutputBoundary) {
        this.spotifyGetDataAccessObject = spotifyGetDataAccessInterface;
        this.fileWriter = fileWriter;
        this.spotifyGetPresenter = spotifyGetOutputBoundary;
    }


    public void execute(SpotifyGetInputData spotifyGetInputData){

        String id = spotifyGetInputData.getId();


        String jsonFile = spotifyGetDataAccessObject.getPlaylistJSON(id);


        if (!(jsonFile).startsWith("FAILED")) {
            // build youtubePlaylist object from json (DAO request 2)
            SpotifyPlaylist spotifyPlaylist = spotifyGetDataAccessObject.buildSpotifyPlaylist(jsonFile, id);

            // store instance in project temp save file (DAO request 3)
            fileWriter.writePlaylistFile(spotifyPlaylist);

            // invoke presenter
            SpotifyGetOutputData spotifyGetOutputData = new SpotifyGetOutputData(spotifyPlaylist, false);
            spotifyGetPresenter.prepareSuccessView(spotifyGetOutputData);
    }





}}
