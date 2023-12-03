package entity;

import org.json.JSONArray;
import org.json.JSONObject;

public class SongBuilderDirector {
    public void BuildSpotifySong(SpotifySongBuilder builder, JSONArray songList, int i) {
        JSONObject entry = songList.getJSONObject(i);
        String title = entry.getJSONArray("name").getString(0);
        String author = entry.getJSONArray("author").getString(0);
        String date = entry.getJSONArray("date").getString(0);
        String id = entry.getJSONArray("spotifyID").getString(0);
        int duration = entry.getJSONArray("duration").getInt(0);
        builder.Title(title);
        builder.Author(author);
        builder.Duration(duration);
        builder.Id(id);
        builder.Date(date);
    }
    public void BuildSpotifySong(SpotifySongBuilder builder, JSONObject snippet) {
            JSONObject snippet2 = snippet.getJSONObject("album");
            String title = snippet.getString("name");
            String author = snippet2.getJSONArray("artists").getJSONObject(0).getString("name");
            String date = snippet2.getString("release_date");
            Integer duration = snippet.getInt("duration_ms");
            String id = snippet.getString("id");
            builder.Title(title);
            builder.Author(author);
            builder.Duration(duration);
            builder.Id(id);
            builder.Date(date);
    }
    public void BuildYoutubeSong(YoutubeSongBuilder builder, JSONObject snippet) {
        String title = snippet.getString("title");
        String author = snippet.getString("videoOwnerChannelTitle");
        String date = snippet.getString("publishedAt");
        JSONObject extraInfo = snippet.getJSONObject("resourceId");
        String id = extraInfo.getString("videoId");
        builder.Title(title);
        builder.Author(author);
        builder.Id(id);
        builder.Date(date);
    }
    public void BuildYoutubeSong(YoutubeSongBuilder builder, JSONArray songList, int i) {
        JSONObject entry = songList.getJSONObject(i);
        String title = entry.getJSONArray("name").getString(0);
        String author = entry.getJSONArray("author").getString(0);
        String id = entry.getJSONArray("youtubeID").getString(0);
        String date = entry.getJSONArray("date").getString(0);
        builder.Title(title);
        builder.Author(author);
        builder.Id(id);
        builder.Date(date);
    }
}
