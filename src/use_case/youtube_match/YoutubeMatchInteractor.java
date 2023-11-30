package use_case.youtube_match;

import data_access.APIs.SpotifyAPI;
import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubeMatchDataAccessObject;
import entity.CompletePlaylist;
import entity.SpotifySong;
import entity.YoutubePlaylist;

public class YoutubeMatchInteractor implements YoutubeMatchInputBoundary {
    final YoutubeMatchDataAccessInterface youtubeMatchDataAccessObject;
    final TempFileWriterDataAccessObject fileWriter;
    final TempFileWriterDataAccessObject backupFileWriter;
    final YoutubeMatchOutputBoundary youtubeMatchPresenter;

    public YoutubeMatchInteractor(YoutubeMatchDataAccessInterface youtubeMatchDataAccessInterface,
                                  TempFileWriterDataAccessObject fileWriter, TempFileWriterDataAccessObject backupFileWriter,
                                  YoutubeMatchOutputBoundary youtubeMatchOutputBoundary) {
        this.youtubeMatchDataAccessObject = youtubeMatchDataAccessInterface;
        this.fileWriter = fileWriter;
        this.backupFileWriter = backupFileWriter;
        this.youtubeMatchPresenter = youtubeMatchOutputBoundary;
    }

    @Override
    public void execute(YoutubeMatchInputData youtubeMatchInputData) {
        SpotifyAPI api = new SpotifyAPI();

        YoutubePlaylist playlist = youtubeMatchInputData.getPlaylist();
        CompletePlaylist incompletePlaylist = (CompletePlaylist) youtubeMatchInputData.getIncompletePlaylist();
        int songLimit = youtubeMatchInputData.getSongLimit();

        YoutubeMatchDataAccessObject.Pair<CompletePlaylist, Boolean> result =
                youtubeMatchDataAccessObject.buildCompletePlaylist(api, playlist, incompletePlaylist, songLimit);

        CompletePlaylist matchedPlaylist = result.p();
        Boolean completed = result.completed();

        fileWriter.writePlaylistFile(matchedPlaylist);
        if (!completed) {
            backupFileWriter.writePlaylistFile(playlist);
        }

        if (completed) {
            YoutubeMatchOutputData youtubeMatchOutputData = new YoutubeMatchOutputData(matchedPlaylist);
            youtubeMatchPresenter.prepareSuccessView(youtubeMatchOutputData, youtubeMatchInputData.getGotoNextView());
        } else {
            YoutubeMatchOutputData youtubeMatchOutputData = new YoutubeMatchOutputData(matchedPlaylist);
            youtubeMatchPresenter.failSaveExit(
                    playlist, youtubeMatchOutputData.getPlaylist(),
                    "Couldn't complete playlist matching. Will now save playlist as file.");
        }
    }
}
