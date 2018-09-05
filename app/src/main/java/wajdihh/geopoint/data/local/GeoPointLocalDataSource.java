package wajdihh.geopoint.data.local;

import android.content.Context;

import java.util.List;

import wajdihh.geopoint.data.entities.GeoPoint;

/**
 * Created by wajdihh on 04/09/2018.
 * Classe qui gere l access a la db
 */

public class GeoPointLocalDataSource {

    private AppDatabase myDatabase;
    public GeoPointLocalDataSource(Context context) {
        myDatabase=AppDatabase.getInstance(context);
    }

    /**
     * Insert all
     * @param list
     */
    public void sync(List<GeoPoint> list){
        myDatabase.geoPointDao().sync(list);

    }

    public List<GeoPoint> getAll() {
        return myDatabase.geoPointDao().getAll();
    }
}
