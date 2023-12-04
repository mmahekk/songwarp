package use_case.youtube_get;

import data_access.APIs.YoutubeAPIAdapter;
import data_access.TempFileWriterDataAccessObject;
import entity.PlaylistBuilderDirector;
import entity.YoutubePlaylist;
import entity.YoutubePlaylistBuilder;
import org.json.JSONArray;
import org.json.JSONObject;

public class YoutubeGetInteractor implements YoutubeGetInputBoundary {
    final YoutubeGetDataAccessInterface youtubeGetDataAccessObject;
    final TempFileWriterDataAccessObject fileWriter;
    final YoutubeGetOutputBoundary youtubeGetPresenter;

    public YoutubeGetInteractor(YoutubeGetDataAccessInterface youtubeGetDataAccessInterface,
                                TempFileWriterDataAccessObject fileWriter, YoutubeGetOutputBoundary youtubeGetOutputBoundary) {
        this.youtubeGetDataAccessObject = youtubeGetDataAccessInterface;
        this.fileWriter = fileWriter;
        this.youtubeGetPresenter = youtubeGetOutputBoundary;
    }

    @Override
    public void execute(YoutubeGetInputData youtubeGetInputData) {
        String id = youtubeGetInputData.getId();

        if (id != null) {
            // get json file from youtube api
            JSONObject jsonFile = youtubeGetDataAccessObject.getPlaylistJSON(id); // (DAO request 1)
            if (jsonFile != null && jsonFile.has("items")) {
                // get rest of pages via nextPageToken
                JSONArray jsonArray = youtubeGetDataAccessObject.getAllPlaylist(jsonFile, id);

                // build youtubePlaylist object from json (DAO request 2)
                PlaylistBuilderDirector director = new PlaylistBuilderDirector();
                YoutubePlaylistBuilder builder = new YoutubePlaylist.Builder();
                director.BuildYoutubePlaylist(builder, jsonArray, id);
                YoutubePlaylist youtubePlaylist = builder.build();

                // store instance in project temp save file (DAO request 3)
                fileWriter.writePlaylistFile(youtubePlaylist);

                // invoke presenter
                YoutubeGetOutputData youtubeGetOutputData = new YoutubeGetOutputData(youtubePlaylist);
                youtubeGetPresenter.prepareSuccessView(youtubeGetOutputData);
            } else {
                youtubeGetPresenter.prepareFailView("Failed to get playlist.");
            }
        } else {
            youtubeGetPresenter.prepareFailView("Invalid Youtube Playlist Url.");
        }
    }
}
