package wajdihh.geopoint.data.remote;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Url;
import wajdihh.geopoint.data.entities.GeoPoint;

/**
 * Created by wajdihh on 03/09/2018.
 * L interface des methodes web services
 */

public interface GeoPointService {

    /**
     * Retourner toutes les URLs
     */

    @GET("/tests/mobile5/points.json")
    Single<List<String>> getAllPointsURL();

    /**
     * Retourner le point geo par URL donnée
     */

    @GET
    Single<GeoPoint> getPoint(@Url String url);
}
