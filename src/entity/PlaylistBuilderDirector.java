package entity;

import org.json.JSONArray;
import org.json.JSONObject;

public class PlaylistBuilderDirector {

    public void BuildSpotifyPlaylist(SpotifyPlaylistBuilder builder, JSONObject spotifyPlaylistJSON, String playlistId) {
        JSONArray songlist = spotifyPlaylistJSON.getJSONObject("tracks").getJSONArray("items");

        SpotifyPlaylist spotifyPlaylist = new SpotifyPlaylist("unknown name", null, playlistId);

        for (int i = 0; i < songlist.length(); i++) {
            JSONObject entry = songlist.getJSONObject(i);
            JSONObject snippet = entry.getJSONObject("track");

            if (snippet.has("album")) {
                JSONObject snippet2 = snippet.getJSONObject("album");
                String title = snippet.getString("name");
                String author = snippet2.getJSONArray("artists").getJSONObject(0).getString("name");
                String date = snippet2.getString("release_date");
                Integer duration = snippet.getInt("duration_ms");
                String id = snippet.getString("id");

                SongBuilderDirector director = new SongBuilderDirector();
                SpotifySongBuilder songBuilder = new SpotifySong.Builder();
                director.BuildSpotifySong(songBuilder, title, author, duration, id, date);
                SpotifySong song = songBuilder.build();
                spotifyPlaylist.addSong(song);
            } else {
                System.out.println("Deleted.");
            }
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
                String title = snippet.getString("title");
                String author = snippet.getString("videoOwnerChannelTitle");
                String date = snippet.getString("publishedAt");
                JSONObject extraInfo = snippet.getJSONObject("resourceId");
                String id = extraInfo.getString("videoId");


                SongBuilderDirector director = new SongBuilderDirector();
                YoutubeSongBuilder youtubeSongBuilder = new YoutubeSong.Builder();
                director.BuildYoutubeSong(youtubeSongBuilder, title, author, id, date);
                YoutubeSong song = youtubeSongBuilder.build();
                youtubePlaylist.addSong(song);
            } else {
                System.out.println("There was a deleted video here");
            }
        }
        builder.Playlist(youtubePlaylist);
    }

}
