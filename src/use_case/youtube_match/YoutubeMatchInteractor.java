package use_case.youtube_match;

import data_access.TempFileWriterDataAccessObject;
import data_access.YoutubeMatchDataAccessObject;
import entity.CompletePlaylist;
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
        YoutubePlaylist playlist = youtubeMatchInputData.getPlaylist();
        CompletePlaylist incompletePlaylist = (CompletePlaylist) youtubeMatchInputData.getIncompletePlaylist();

        YoutubeMatchDataAccessObject.Pair<CompletePlaylist, Boolean> result =
                youtubeMatchDataAccessObject.buildCompletePlaylist(playlist, incompletePlaylist);

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
