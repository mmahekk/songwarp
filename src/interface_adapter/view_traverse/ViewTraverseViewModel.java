package interface_adapter.view_traverse;

import interface_adapter.ViewModel;

import java.beans.PropertyChangeListener;

public class ViewTraverseViewModel extends ViewModel {
    public ViewTraverseViewModel() {
        super("page 3");
    }

    @Override
    public void firePropertyChanged() {

    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener listener) {

    }
}
