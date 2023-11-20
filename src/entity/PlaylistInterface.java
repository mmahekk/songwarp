package entity;

import java.util.ArrayList;
import entity.Song;

interface PlaylistInterface {
     ArrayList<Song> getList(); // Returns a list of songs
     String getGenre(); // Returns the overall genre of the playlist
     int getTotal(); // Returns total number of songs in the playlist
     String getName(); // Returns playlist name

}