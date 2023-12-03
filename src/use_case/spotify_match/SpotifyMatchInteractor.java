package use_case.spotify_match;

import data_access.APIs.YoutubeAPIAdapter;
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

        YoutubeAPIAdapter api = new YoutubeAPIAdapter();

        SpotifyPlaylist playlist = spotifyMatchInputData.getPlaylist();
        CompletePlaylist incompletePlaylist = (CompletePlaylist) spotifyMatchInputData.getIncompletePlaylist();
        int songLimit = spotifyMatchInputData.getSongLimit();

       SpotifyMatchDataAccessObject.Pair<CompletePlaylist, Boolean> result =
                spotifyMatchDataAccessObject.buildCompletePlaylist(api, playlist, incompletePlaylist, songLimit);

        CompletePlaylist matchedPlaylist = result.p();
        Boolean completed = result.completed();

        fileWriter.writePlaylistFile(matchedPlaylist);
        if (!completed) {
            backupFileWriter.writePlaylistFile(playlist);
        }

        if (completed) {
            SpotifyMatchOutputData spotifyMatchOutputData = new SpotifyMatchOutputData(matchedPlaylist);
            spotifyMatchPresenter.prepareSuccessView(spotifyMatchOutputData, spotifyMatchInputData.getGotoNextView());
        } else {
            SpotifyMatchOutputData spotifyMatchOutputData = new SpotifyMatchOutputData(matchedPlaylist);
            spotifyMatchPresenter.failSaveExit(
                    playlist, spotifyMatchOutputData.getPlaylist(),
                    "Error, can't complete. ");
        }
    }
}
