package wajdihh.geopoint.data.local;

import android.content.Context;

import java.util.List;

import io.reactivex.Single;
import wajdihh.geopoint.data.entities.GeoPoint;
import wajdihh.geopoint.data.entities.GeoPointGroup;

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

    public Single<List<GeoPointGroup>> getAllGroups() {
        return myDatabase.geoPointGroupDao().getAll();
    }
     public Single<List<GeoPoint>> getAllPointForGroup(String groupID) {
        return myDatabase.geoPointDao().getAllPointForGroup(groupID);
    }

    public void insertGroup(GeoPointGroup group){
        myDatabase.geoPointGroupDao().insert(group);
    }

    public GeoPointGroup getGroup(String id){
        return myDatabase.geoPointGroupDao().getGroup(id);
    }

    public int getCount(){
        return myDatabase.geoPointGroupDao().getCount();
    }
}
