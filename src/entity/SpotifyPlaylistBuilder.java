package entity;

public interface SpotifyPlaylistBuilder extends PlaylistBuilder{
    void Playlist(SpotifyPlaylist playlist);
    @Override
    SpotifyPlaylist build();
}
