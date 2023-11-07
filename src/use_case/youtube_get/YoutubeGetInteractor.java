package use_case.youtube_get;

import entity.YoutubePlaylist;
import interface_adapter.youtube_get.YoutubeGetPresenter;

public class YoutubeGetInteractor implements YoutubeGetInputBoundary {
    final YoutubeGetDataAccessInterface youtubeGetDataAccessObject;
    final YoutubeGetOutputBoundary youtubeGetPresenter;

    public YoutubeGetInteractor(YoutubeGetDataAccessInterface youtubeGetDataAccessInterface,
                           YoutubeGetOutputBoundary youtubeGetOutputBoundary) {
        this.youtubeGetDataAccessObject = youtubeGetDataAccessInterface;
        this.youtubeGetPresenter = youtubeGetOutputBoundary;
    }

    @Override
    public void execute(YoutubeGetInputData youtubeGetInputData) {
        String id = youtubeGetInputData.getId();

        // get json file from youtube api
        String jsonFile = youtubeGetDataAccessObject.getPlaylistJSON(id);

        if (!(jsonFile).startsWith("FAILED")) {
            // build youtubePlaylist object from json
            YoutubePlaylist youtubePlaylist = youtubeGetDataAccessObject.buildYoutubePlaylist(jsonFile);

            // store instance in project temp save file
            // TODO: add save playlist invoke, using FileUserDataAccessObject and the youtubePlaylist made in previous line

            YoutubeGetOutputData youtubeGetOutputData = new YoutubeGetOutputData(youtubePlaylist, false);
            youtubeGetPresenter.prepareSuccessView(youtubeGetOutputData);

        } else {
            // TODO: implement this situation
            // failed HTTP request, so we must now save the playlist and inform the user
            // failed HTTP request may be due to passing the api call quota (youtube API has a 10,000 quota a day)
        }
    }
}
