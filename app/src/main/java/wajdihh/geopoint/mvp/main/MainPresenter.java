package wajdihh.geopoint.mvp.main;

/**
 * Created by wajdihh on 03/09/2018.
 *
 * Pour respecter le principe de MVP, cette interface represente le Presenter
 */

public interface  MainPresenter {

    void attachView(MainView v);

    void loadData();

}
