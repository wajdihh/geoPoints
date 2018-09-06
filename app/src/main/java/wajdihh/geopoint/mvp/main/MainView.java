package wajdihh.geopoint.mvp.main;

import java.util.List;

import wajdihh.geopoint.data.entities.GeoPointGroup;

/**
 * Created by wajdihh on 06/09/2018.
 * Interface du view : voir MVP
 */

public interface MainView {

    void showProgress();

    void hideProgress();

    void onSuccessLoad(List<GeoPointGroup> groupList);

    void onErrorLoad(Throwable throwable);

}
