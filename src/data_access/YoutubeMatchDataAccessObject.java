package data_access;

import data_access.APIs.SpotifyAPIAdapter;
import entity.*;
import interface_adapter.ProgressListener;
import org.json.JSONObject;
import use_case.youtube_match.YoutubeMatchDataAccessInterface;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static utilities.SearchQueryEncoder.encodeSearchQuery;
import static utilities.YoutubeTitleInfoExtract.youtubeTitleInfoExtract;

public class YoutubeMatchDataAccessObject implements YoutubeMatchDataAccessInterface {
    @Override
    public SpotifySong findSpotifySongMatch(SpotifyAPIAdapter api, YoutubeSong song) {
        String[] nameAndAuthor = youtubeTitleInfoExtract(song.getName(), song.getAuthor());
        String firstTryQuery = nameAndAuthor[0] + " " + nameAndAuthor[1];
        String secondTryQuery = (nameAndAuthor[2] + " " + nameAndAuthor[3]).replaceAll("null", "");
        System.out.println(firstTryQuery);
        String data = api.searchSong(encodeSearchQuery(firstTryQuery));
        if (data != null && !data.isEmpty()) {
            SpotifySong newSong = buildSpotifySong(new JSONObject(data));
            if (!firstTryQuery.contains(newSong.getAuthor().toLowerCase())) {
                String secondData = api.searchSong(encodeSearchQuery(firstTryQuery + " " + secondTryQuery));
                if (secondData != null) {
                    newSong = buildSpotifySong(new JSONObject(secondData));
                }
            }
            return newSong;
        }
        return null;
    }

    @Override
    public SpotifySong buildSpotifySong(JSONObject data) {
        if (data.has("tracks")) {
            JSONObject topSearchResults = data.getJSONObject("tracks").getJSONArray("items").getJSONObject(0);

            SongBuilderDirector director = new SongBuilderDirector();
            SpotifySongBuilder builder = new SpotifySong.Builder();
            director.BuildSpotifySong(builder, topSearchResults);
            SpotifySong song = builder.build();
            System.out.println(song.convertToJSON());
            return song;
        }
        return null;
    }


    @Override
    public Pair<CompletePlaylist, Boolean> buildCompletePlaylist(SpotifyAPIAdapter api, YoutubePlaylist playlist, CompletePlaylist incompletePlaylist, int songLimit) {
        ArrayList<YoutubeSong> songList = playlist.getYoutubeSongs();
        CompletePlaylist matchedPlaylist = Objects.requireNonNullElseGet(incompletePlaylist, () ->
                new CompletePlaylist("unknown name", null, playlist.getYoutubeID(), "unknown"));

        int offset;
        if (matchedPlaylist.getTotal() < songList.size()) {
            offset = matchedPlaylist.getTotal();
        } else {
            offset = 0;
        }

        for (int i = offset; i < songList.size(); i++) {
            YoutubeSong song = songList.get(i);
            if (song != null) {
                if (songLimit != -1 && matchedPlaylist.getTotal() >= songLimit) {
                    return new Pair<>(matchedPlaylist, false);
                }
                SpotifySong matchedSong = findSpotifySongMatch(api, song);
                if (matchedSong != null) {
                    CompleteSong completeSong = new CompleteSong(
                            matchedSong.getName(), matchedSong.getAuthor(), matchedSong.getSpotifyID(),
                            song.getYoutubeID(), matchedSong.getDate(),
                            song.getName(), song.getAuthor(), matchedSong.getDuration());

                    matchedPlaylist.addSong(completeSong);
                } else {  // we got an http error, i.e. ran out of tokens, therefore, it means we stop building and indicate that it wasn't a completed playlist
                    return new Pair<>(matchedPlaylist, false);
                }
            }
            for (ProgressListener listener : progressListeners) {
                listener.onProgressUpdated(Math.min(100, (i * 100) / songList.size()));
            }
        }
        return new Pair<>(matchedPlaylist, true);
    }

    public record Pair<CompletePlaylist, Boolean>(CompletePlaylist p, Boolean completed) {}

    private final List<ProgressListener> progressListeners = new ArrayList<>();

    public void addProgressListener(ProgressListener listener) {
        progressListeners.add(listener);
    }

    public void removeProgressListener(ProgressListener listener) {
        progressListeners.remove(listener);
    }
}


