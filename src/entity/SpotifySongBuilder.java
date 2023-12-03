package entity;


public interface SpotifySongBuilder extends SongBuilder{
    void Duration(int duration);
    @Override
    SpotifySong build();
}
