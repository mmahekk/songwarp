package interface_adapter.view_traverse;

import entity.YoutubePlaylist;
import use_case.view_traverse.ViewTraverseInputBoundary;
import use_case.view_traverse.ViewTraverseInputData;
import use_case.youtube_match.YoutubeMatchInputBoundary;
import use_case.youtube_match.YoutubeMatchInputData;

public class ViewTraverseController {
    final ViewTraverseInputBoundary viewTraverseUseCaseInteractor;

    public ViewTraverseController(ViewTraverseInputBoundary viewTraverseUseCaseInteractor) {
        this.viewTraverseUseCaseInteractor = viewTraverseUseCaseInteractor;
    }

    public void execute() {
        ViewTraverseInputData viewTraverseInputData = new ViewTraverseInputData();

        //invoke the use case interactor
        viewTraverseUseCaseInteractor.execute(viewTraverseInputData);
    }
}
