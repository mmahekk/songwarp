package use_case.view_traverse;

public class ViewTraverseInteractor implements ViewTraverseInputBoundary {
    private final ViewTraverseOutputBoundary viewTraversePresenter;

    public ViewTraverseInteractor(ViewTraverseOutputBoundary viewTraversePresenter) {
        this.viewTraversePresenter = viewTraversePresenter;
    }

    @Override
    public void execute(ViewTraverseInputData viewTraverseInputData) {
        viewTraversePresenter.prepareSuccessView();
    }
}
