package entity;

import org.json.JSONObject;

public interface SongInterface {

    String getName();
    String getAuthor();
    String getDate();
    JSONObject convertToJSON();
}
