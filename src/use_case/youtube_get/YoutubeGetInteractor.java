package use_case.youtube_get;

import data_access.TempPlaylistDataAccessObject;
import entity.YoutubePlaylist;

public class YoutubeGetInteractor implements YoutubeGetInputBoundary {
    final YoutubeGetDataAccessInterface youtubeGetDataAccessObject;
    final TempPlaylistDataAccessObject fileWriter;
    final YoutubeGetOutputBoundary youtubeGetPresenter;

    public YoutubeGetInteractor(YoutubeGetDataAccessInterface youtubeGetDataAccessInterface,
                                TempPlaylistDataAccessObject fileWriter, YoutubeGetOutputBoundary youtubeGetOutputBoundary) {
        this.youtubeGetDataAccessObject = youtubeGetDataAccessInterface;
        this.fileWriter = fileWriter;
        this.youtubeGetPresenter = youtubeGetOutputBoundary;
    }

    @Override
    public void execute(YoutubeGetInputData youtubeGetInputData) {
        String id = youtubeGetInputData.getId();

        // get json file from youtube api
        String jsonFile = youtubeGetDataAccessObject.getPlaylistJSON(id); // (DAO request 1)

        if (!(jsonFile).startsWith("FAILED")) {
            // build youtubePlaylist object from json (DAO request 2)
            YoutubePlaylist youtubePlaylist = youtubeGetDataAccessObject.buildYoutubePlaylist(jsonFile, id);

            // store instance in project temp save file (DAO request 3)
            fileWriter.writePlaylistFile(youtubePlaylist);

//            // invoke presenter
//            YoutubeGetOutputData youtubeGetOutputData = new YoutubeGetOutputData(youtubePlaylist, false);
//            youtubeGetPresenter.prepareSuccessView(youtubeGetOutputData);

        } else {
            // TODO: implement this situation
            // failed HTTP request, so we must now save the playlist and inform the user
            // failed HTTP request may be due to passing the api call quota (youtube API has a 10,000 quota a day)
        }
    }
}
