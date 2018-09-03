package wajdihh.geopoint.data;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import wajdihh.geopoint.data.entities.GeoPoint;
import wajdihh.geopoint.data.remote.GeoPointService;
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

public class DataHandler {

    private GeoPointService mGeoPointService;

    public DataHandler(Context context) {
        mGeoPointService = Retrofit2Client.getClient(context).create(GeoPointService.class);
    }

    public List<GeoPoint> getAllPoint() {


        List<GeoPoint> myPoints = new ArrayList<>();

        mGeoPointService.getAllPointsURL().toObservable()
                //Iteration de la liste des urls vers une SEULE url observable
                .flatMapIterable(list -> list)
                //Recuperer chaque point de chaque URL
                .flatMap(url -> mGeoPointService.getPoint(url).toObservable())
                //Attacher l observable au Thread IO car c'une operation en background
                .subscribeOn(Schedulers.io())
                //L'obserbateur sera le Thread UI car apres le traitement on va faire un refresh de la liste
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GeoPoint>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GeoPoint geoPoint) {
                        System.out.println(geoPoint.getLatitude());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        System.out.println("oh yeah I finished");

                    }
                });


        return myPoints;
    }


}
