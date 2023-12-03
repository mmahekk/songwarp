package entity;

public interface SongBuilder {
    void Title(String title);
    void Author(String author);
    void Id(String spotifyID);
    void Date(String date);
    Song build();
}
