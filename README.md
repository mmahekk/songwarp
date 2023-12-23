# SongWarp
## Group Members:
- Abhishek Sharma
- Ethan Fine
- Aria Coventry
- Xinze Wang
- Mahek Cheema

## Our Project
A Music playlist manager that can convert a YouTube playlist to a Spotify playlist and vice versa, plus additional playlist manager features.
![image](https://github.com/mmahekk/songwarp/assets/96924464/58080877-4b47-4d82-af64-3e423d2bee1a)

## Software Specification
- Uses YouTube and Spotify APIs to build song instances with as much attributes as possible. This is done by
getting info about a song from one site and matching it to a song on the other site.
- The YouTube to Spotify matching algorithm takes a YouTube url and outputs a song name & author name by
deciphering the video title and channel name, which it then uses for a Spotify search.
- The Spotify to YouTube matching algorithm takes a song’s name and author name attributes and searches
YouTube for best match.
- After it matches as much as it can, it prompts the user to manually decide what the matching song should be
(via suggestion or manual lookup) for all the songs it couldn’t find a definite match for.
- These song instances combine to form the playlist object, which contains enough info to create a YouTube
playlist, a Spotify playlist, or a json file (which can be downloaded or copied from a textbox).
- The song instances also have a genre attribute, which can be used to split the playlist into multiple playlists
based on genre. (And then the program can upload all or some of those playlists at once)
- The program needs access to your accounts to upload the outputted playlist onto your account (but for
inputting an initial playlist, it just needs a url pasted into a textbox

## Use case examples
1. Convert a playlist from Youtube to Spotify/ Spotify to Youtube
   - a. user is prompted to input the link to their curated Youtube or Spotify playlist
   - b. the application will match songs across platform databases by identifying song attributes, such as song title
   and author
   - c. if a song is not found then the user is prompted to choose a similar song or skip the current song
   - d. after matching songs, the playlist is created and uploaded to the recipient Youtube or Spotify account
2. Saving a playlist as a save file or loading a saved playlist file onto the program
   - a. user manually downloads or uploads a json formatted file of the respective playlist to the application
3. Splitting a Youtube playlist into several sub-playlists by genre (can also be done with Spotify playlists)
   - a. user is prompted to input the link to their curated Youtube playlist
   - b. songs are matched in the Spotify database and genre attributes for each song are identified
   - c. songs are then sorted into sub-playlists based on the genre attributes
     - i. if a genre attribute is not found for a song then the user is prompted to input the genre manually or
     the song will be placed in an “unknown genre” playlist
   - d. playlists are then uploaded onto the specified Youtube account


## API usage documentation links
- **Spotify API:** (for PUT/GET requests for the user's spotify account)
  https://developer.spotify.com/documentation/web-api
- **YouTube API:** (for PUT/GET requests for the user's youtube account)
  https://developers.google.com/youtube/v3/getting-started
