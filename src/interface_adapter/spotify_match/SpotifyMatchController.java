//package interface_adapter.spotify_match;
//
//import entity.YoutubePlaylist;
//import use_case.youtube_match.YoutubeMatchDataAccessInterface;
//import use_case.youtube_match.YoutubeMatchInputBoundary;
//import use_case.youtube_match.YoutubeMatchInputData;
//
//public class SpotifyMatchController {
//    final SpotifyMatchInputBoundary spotifyMatchUseCaseInteractor;
//    public SpotifyMatchController(SpotifyMatchInputBoundary spotifyMatchUseCaseInteractor) {
//        this.spotifyMatchUseCaseInteractor = spotifyMatchUseCaseInteractor;
//    }
//
//    public void execute(SpotifyPlaylist playlist) {
//        SpotifyMatchInputData spotifyMatchInputData = new SpotifyMatchInputData(playlist);
//
//        //invoke the use case interactor
//        spotifyMatchUseCaseInteractor.execute(spotifyMatchInputData);
//    }
//}
