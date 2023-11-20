package use_case.spotify_get;

public class SpotifyGetInputData {

    final private String url;
    final private String id;


    public SpotifyGetInputData(String url){
        this.url = url;
        this.id = this.extractId(url);
    }


    public String getUrl() {
        return url;
    }

    public String getId() {
        return id;
    }


    public String extractId(String playlistUrl) {

        if (playlistUrl != null && playlistUrl.startsWith("https://open.spotify.com/playlist/")){
            int lastIndex = url.lastIndexOf("/");

            if (url.endsWith(Character.toString(22))) {
                return url.substring(lastIndex + 1, url.length() - 1);
            } else {
                return url.substring(lastIndex + 1);
            }

        } else {
            return null;
        }

    }
}
