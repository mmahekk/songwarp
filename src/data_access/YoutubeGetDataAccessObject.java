package data_access;

import data_access.APIs.YoutubeAPIAdapter;
import entity.YoutubePlaylist;
import entity.YoutubeSong;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
public class YoutubeGetDataAccessObject implements YoutubeGetDataAccessInterface {
    @Override
    public JSONObject getPlaylistJSON(YoutubeAPIAdapter api, String youtubePlaylistID) {
        String response = api.getPlaylist(youtubePlaylistID, null);
        return new JSONObject(response);
    }

    /**
     * uses next page tokens to get the rest of the playlist, since max return is 50 songs
     *
     * @return
     */
    @Override
    public JSONArray getAllPlaylist(YoutubeAPIAdapter api, JSONObject jsonObject, String playlistID) {
        String nextPageToken;
        if (jsonObject.has("nextPageToken")) {
            nextPageToken = jsonObject.getString("nextPageToken");
        } else {
            nextPageToken = null;
        }
        JSONArray firstItems = jsonObject.getJSONArray("items");
        while (nextPageToken != null) {
            System.out.println(nextPageToken);
            String data = api.getPlaylist(playlistID, nextPageToken);
            if (data != null) {
                // Parse the JSON response
                JSONObject nextPage = new JSONObject(data);
                // get next page token ready for next time
                if (nextPage.has("nextPageToken")) {
                    nextPageToken = nextPage.getString("nextPageToken");
                } else {
                    nextPageToken = null;
                    System.out.println("no next page");
                }

                if (nextPage.has("items")) {
                    // get items and append them to our JSON array
                    JSONArray newItems = nextPage.getJSONArray("items");
                    for (int i = 0; i < newItems.length(); i++) {
                        firstItems.put(newItems.getJSONObject(i));
                    }
                }
            }
        }
        return firstItems;
    }

    @Override
    public YoutubePlaylist buildYoutubePlaylist(JSONArray songList, String playlistId) {
        // convert JSON string to JSON object
        String name = "unknown name";

        // create empty youtubePlaylist object
        YoutubePlaylist youtubePlaylist = new YoutubePlaylist(name, null, playlistId);

        for (int i = 0; i < songList.length(); i++) {
            JSONObject entry = songList.getJSONObject(i);
            JSONObject snippet = entry.getJSONObject("snippet");

            if (snippet.has("videoOwnerChannelTitle")) {
                String title = snippet.getString("title");
                String channel = snippet.getString("videoOwnerChannelTitle");
                String date = snippet.getString("publishedAt");
                JSONObject extraInfo = snippet.getJSONObject("resourceId");
                String id = extraInfo.getString("videoId");

                YoutubeSong song = new YoutubeSong(title, channel, id, date);
                youtubePlaylist.addSong(song);
            } else {
                System.out.println("There was a deleted video here");
            }
        }
        return youtubePlaylist;
    }
}