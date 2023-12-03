package entity;

public interface YoutubePlaylistBuilder extends PlaylistBuilder{
    void Playlist(YoutubePlaylist playlist);
    @Override
    YoutubePlaylist build();
}
