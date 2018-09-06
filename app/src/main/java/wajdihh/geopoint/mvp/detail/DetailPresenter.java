package wajdihh.geopoint.mvp.detail;

/**
 * Created by wajdihh on 03/09/2018.
 *
 * Pour respecter le principe de MVP, cette interface represente le Presenter
 */

public interface DetailPresenter {

    void attachView(DetailView v);

    void loadPoints(String groupID);

}
