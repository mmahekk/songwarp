package entity;

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
}
