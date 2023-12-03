package entity;

public class SongBuilderDirector {
    public void BuildSpotifySong(SpotifySongBuilder builder, String title, String author, int duration, String Id, String date) {
        builder.Title(title);
        builder.Author(author);
        builder.Duration(duration);
        builder.Id(Id);
        builder.Date(date);
    }
    public void BuildYoutubeSong(YoutubeSongBuilder builder, String title, String author, String Id, String date) {
        builder.Title(title);
        builder.Author(author);
        builder.Id(Id);
        builder.Date(date);
    }
}
