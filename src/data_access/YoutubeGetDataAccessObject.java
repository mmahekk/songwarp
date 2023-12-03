package data_access;

import data_access.APIs.YoutubeAPIAdapter;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.youtube_get.YoutubeGetDataAccessInterface;
public class YoutubeGetDataAccessObject implements YoutubeGetDataAccessInterface {
    @Override
    public JSONObject getPlaylistJSON(String youtubePlaylistID) {
        YoutubeAPIAdapter api = new YoutubeAPIAdapter();
        String response = api.getPlaylist(youtubePlaylistID, null);
        return new JSONObject(response);
    }

    /**
     * uses next page tokens to get the rest of the playlist, since max return is 50 songs
     *
     * @return
     */
    @Override
    public JSONArray getAllPlaylist(JSONObject jsonObject, String playlistID) {
        YoutubeAPIAdapter api = new YoutubeAPIAdapter();
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
}