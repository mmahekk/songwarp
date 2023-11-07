package entity;

import org.json.JSONObject;

abstract class Song implements SongInterface {

    private final String name;
    private final String author;
    private final String date;

    public Song(String name, String author, String date) {
        this.name = name;
        this.author = author;
        this.date = date;
    }
    @Override
    public String getName() {
        return this.name;
    }
    @Override
    public String getAuthor() {
        return this.author;
    }
    @Override
    public String getDate() {
        return this.date;
    }

    @Override
    public JSONObject convertToJSON() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.append("name", name);
        jsonObject.append("author", author);
        jsonObject.append("date", date);
        return jsonObject;
    }
}
