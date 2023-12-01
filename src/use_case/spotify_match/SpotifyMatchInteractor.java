package use_case.spotify_match;

import data_access.TempFileWriterDataAccessObject;
import data_access.SpotifyMatchDataAccessObject;
import entity.CompletePlaylist;
import entity.SpotifyPlaylist;

public class SpotifyMatchInteractor implements SpotifyMatchInputBoundary {


    final SpotifyMatchDataAccessInterface spotifyMatchDataAccessObject;
    final TempFileWriterDataAccessObject fileWriter;
    final TempFileWriterDataAccessObject backupFileWriter;
    final SpotifyMatchOutputBoundary spotifyMatchPresenter;




    public SpotifyMatchInteractor(SpotifyMatchDataAccessInterface spotifyMatchDataAccessInterface,
                                  TempFileWriterDataAccessObject fileWriter, TempFileWriterDataAccessObject backupFileWriter,
                                  SpotifyMatchOutputBoundary spotifyMatchOutputBoundary) {
        this.spotifyMatchDataAccessObject = spotifyMatchDataAccessInterface;
        this.fileWriter = fileWriter;
        this.backupFileWriter = backupFileWriter;
        this.spotifyMatchPresenter = spotifyMatchOutputBoundary;
    }

    @Override
    public void execute(SpotifyMatchInputData spotifyMatchInputData) {


    }
}
