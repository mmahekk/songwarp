package entity;

abstract class Song implements SongInterface {

    private final String name;
    private final String author;
    private final int duration;

    public Song(String name, String author, int duration) {
        this.name = name;
        this.author = author;
        this.duration = duration;
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
    public int getDuration() {
        return this.duration;
    }
}
