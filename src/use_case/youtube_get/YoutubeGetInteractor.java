package use_case.youtube_get;

import data_access.TempPlaylistDataAccessObject;
import entity.YoutubePlaylist;
import org.json.JSONArray;
import org.json.JSONObject;

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
        JSONObject jsonFile = youtubeGetDataAccessObject.getPlaylistJSON(id); // (DAO request 1)
        if (jsonFile.has("items")) {
            // get rest of pages via nextPageToken
            JSONArray jsonArray = youtubeGetDataAccessObject.getAllPlaylist(jsonFile, id);

            // build youtubePlaylist object from json (DAO request 2)
            YoutubePlaylist youtubePlaylist = youtubeGetDataAccessObject.buildYoutubePlaylist(jsonArray, id);

            // store instance in project temp save file (DAO request 3)
            fileWriter.writePlaylistFile(youtubePlaylist);

            // invoke presenter
            YoutubeGetOutputData youtubeGetOutputData = new YoutubeGetOutputData(youtubePlaylist);
            youtubeGetPresenter.prepareSuccessView(youtubeGetOutputData);

        } else {
            youtubeGetPresenter.prepareFailView("Failed to get playlist.");
        }
    }
}
