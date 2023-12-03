package entity;

import org.json.JSONArray;
import org.json.JSONObject;

import static data_access.TempFileWriterDataAccessObject.readTempJSON;

public class PlaylistBuilderDirector {

    public void BuildSpotifyPlaylist(SpotifyPlaylistBuilder builder, JSONObject spotifyPlaylistJSON, String playlistId) {
        JSONArray songlist = spotifyPlaylistJSON.getJSONObject("tracks").getJSONArray("items");

        SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist("unknown name", null, playlistId);

        for (int i = 0; i < songlist.length(); i++) {
            JSONObject entry = songlist.getJSONObject(i);
            JSONObject snippet = entry.getJSONObject("track");
            if (snippet.has("album")) {
                SongBuilderDirector director = new SongBuilderDirector();
                SpotifySongBuilder songBuilder = new SpotifySong.Builder();
                director.BuildSpotifySong(songBuilder, snippet);
                SpotifySong song = songBuilder.build();
                spotifyPlaylist.addSong(song);
            } else {
                System.out.println("Deleted.");
            }
        }
        builder.Playlist(spotifyPlaylist);
    }
    public void BuildSpotifyPlaylist(SpotifyPlaylistBuilder builder, String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        JSONArray songList = jsonObject.getJSONArray("items").getJSONArray(0);

        String spotifyID = jsonObject.getJSONArray("spotifyID").getString(0);
        SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist("loaded playlist", null, spotifyID);
        for (int i = 0; i < songList.length(); i++) {

            SongBuilderDirector director = new SongBuilderDirector();
            SpotifySongBuilder spotifySongBuilder = new SpotifySong.Builder();
            director.BuildSpotifySong(spotifySongBuilder, songList, i);
            SpotifySong song = spotifySongBuilder.build();
            spotifyPlaylist.addSong(song);
        }
        builder.Playlist(spotifyPlaylist);
    }
    public void BuildYoutubePlaylist(YoutubePlaylistBuilder builder, JSONArray songList, String playlistId) {
        // create empty youtubePlaylist object
        YoutubePlaylist youtubePlaylist = new YoutubePlaylist("unknown name", null, playlistId);

        for (int i = 0; i < songList.length(); i++) {
            JSONObject entry = songList.getJSONObject(i);
            JSONObject snippet = entry.getJSONObject("snippet");

            if (snippet.has("videoOwnerChannelTitle")) {

                SongBuilderDirector director = new SongBuilderDirector();
                YoutubeSongBuilder youtubeSongBuilder = new YoutubeSong.Builder();
                director.BuildYoutubeSong(youtubeSongBuilder, snippet);
                YoutubeSong song = youtubeSongBuilder.build();
                youtubePlaylist.addSong(song);
            } else {
                System.out.println("There was a deleted video here");
            }
        }
        builder.Playlist(youtubePlaylist);
    }
    public void BuildYoutubePlaylist(YoutubePlaylistBuilder builder, String file) {
        JSONObject jsonObject = readTempJSON(file, false);
        assert jsonObject != null;
        JSONArray songList = jsonObject.getJSONArray("items").getJSONArray(0);

        String youtubeID = jsonObject.getJSONArray("youtubeID").getString(0);
        YoutubePlaylist youtubePlaylist = new YoutubePlaylist("loaded playlist", null, youtubeID);
        for (int i = 0; i < songList.length(); i++) {
            SongBuilderDirector director = new SongBuilderDirector();
            YoutubeSongBuilder youtubeSongBuilder = new YoutubeSong.Builder();
            director.BuildYoutubeSong(youtubeSongBuilder, songList, i);
            YoutubeSong song = youtubeSongBuilder.build();
            youtubePlaylist.addSong(song);
        }
        builder.Playlist(youtubePlaylist);
    }
}
