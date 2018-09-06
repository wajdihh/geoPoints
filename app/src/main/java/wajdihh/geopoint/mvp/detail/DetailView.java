package wajdihh.geopoint.mvp.detail;

import java.util.List;

import wajdihh.geopoint.data.entities.GeoPoint;

/**
 * Created by wajdihh on 06/09/2018.
 * Interface du view : voir MVP
 */

public interface DetailView {

    void onSuccessLoad(List<GeoPoint> points);

    void onErrorLoad(Throwable throwable);

}
