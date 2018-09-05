package wajdihh.geopoint.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wajdihh.geopoint.data.entities.GeoPoint;
import wajdihh.geopoint.data.local.GeoPointLocalDataSource;
import wajdihh.geopoint.data.remote.GeoPointRemoteDataSource;
import wajdihh.geopoint.data.remote.Retrofit2Client;

/**
 * Created by wajdihh on 03/09/2018.
 * <p>
 * Cette classe est l'equivalente de DOMAINE module dans la Clean architecture, mais vu que le projet est un exemple, je n'ai pas fait plusieurs modules
 * <p>
 * Cette classe a pour objectif de gerer le data depuis le remote (API) et le local (Cache)
 * <p>
 * Tout simplement depuis l'app si on veut une data on doit la trouver ici
 */

public class DataSource {

    private GeoPointRemoteDataSource mGeoPointRemoteDS;
    private GeoPointLocalDataSource mGeoPointLocalDS;

    public DataSource(Context context) {
        //Init
        mGeoPointRemoteDS = Retrofit2Client.getClient(context).create(GeoPointRemoteDataSource.class);
        //Init
        mGeoPointLocalDS = new GeoPointLocalDataSource(context);
    }

    public List<GeoPoint> getAllPoint() {


        List<GeoPoint> myPoints = new ArrayList<>();

        mGeoPointRemoteDS.getAllPointsURL()
                // faire l'iteration de chaque URL de la liste
                .flatMapIterable(url -> url)
                // Recuperer chaque point de chaque URL et retourner la liste des points à la fin
                .flatMap(mGeoPointRemoteDS::getPoint).toList()
                // Synchroniser la Liste dans le cache
                /**
                 * Dans le cas ou la liste presente des millions des data on peut decouper la liste par lot
                 * et sauvegarder par lot les data
                 *
                 * Retourner le 10000 element de la liste pour faire un insert dans le cache chaque Lot de 10000 items
                 * .flatMap(mGeoPointRemoteDS::getPoint).buffer(10000)
                 */
                .doOnSuccess(mGeoPointLocalDS::sync).toObservable()
                .doOnComplete(mGeoPointLocalDS::getAll)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<GeoPoint>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<GeoPoint> geoPoints) {
                        System.out.println("Siwe in database "+geoPoints.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        return myPoints;
    }


}
