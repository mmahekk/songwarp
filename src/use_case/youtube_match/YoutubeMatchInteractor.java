package use_case.youtube_match;

import data_access.TempPlaylistDataAccessObject;
import data_access.YoutubeMatchDataAccessObject;
import entity.CompletePlaylist;
import entity.YoutubePlaylist;

public class YoutubeMatchInteractor implements YoutubeMatchInputBoundary {
    final YoutubeMatchDataAccessInterface youtubeMatchDataAccessObject;
    final TempPlaylistDataAccessObject fileWriter;
    final TempPlaylistDataAccessObject backupFileWriter;
    final YoutubeMatchOutputBoundary youtubeMatchPresenter;

    public YoutubeMatchInteractor(YoutubeMatchDataAccessInterface youtubeMatchDataAccessInterface,
                                  TempPlaylistDataAccessObject fileWriter, TempPlaylistDataAccessObject backupFileWriter,
                                  YoutubeMatchOutputBoundary youtubeMatchOutputBoundary) {
        this.youtubeMatchDataAccessObject = youtubeMatchDataAccessInterface;
        this.fileWriter = fileWriter;
        this.backupFileWriter = backupFileWriter;
        this.youtubeMatchPresenter = youtubeMatchOutputBoundary;
    }

    @Override
    public void execute(YoutubeMatchInputData youtubeMatchInputData) {
        YoutubePlaylist playlist = youtubeMatchInputData.getPlaylist();

        YoutubeMatchDataAccessObject.Pair<CompletePlaylist, Boolean> result =
                youtubeMatchDataAccessObject.buildCompletePlaylist(playlist);

        CompletePlaylist matchedPlaylist = result.p();
        Boolean completed = result.completed();

        fileWriter.writePlaylistFile(matchedPlaylist);
        if (!completed) {
            backupFileWriter.writePlaylistFile(playlist);
        }

        YoutubeMatchOutputData youtubeMatchOutputData = new YoutubeMatchOutputData(matchedPlaylist);
        youtubeMatchPresenter.prepareSuccessView(youtubeMatchOutputData);
    }
}
