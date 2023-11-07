package use_case.youtube_get;

import entity.YoutubePlaylist;

public class YoutubeGetOutputData {
    private final YoutubePlaylist playlist;
    private boolean useCaseFailed;

    public YoutubeGetOutputData(YoutubePlaylist playlist, boolean useCaseFailed) {
        this.playlist = playlist;
        this.useCaseFailed = useCaseFailed;
    }

    public YoutubePlaylist getPlaylist() {
        return playlist;
    }
}
