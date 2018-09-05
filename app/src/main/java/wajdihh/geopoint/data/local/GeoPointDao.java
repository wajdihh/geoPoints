package wajdihh.geopoint.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Transaction;

import java.util.List;

import wajdihh.geopoint.data.entities.GeoPoint;

/**
 * Created by wajdihh on 04/09/2018.
 * Interface utilisé par Room ORM
 */

@Dao
public interface GeoPointDao {

    /**
     * Permet de synchoniser les geo point dans la base local
     * @param list
     */
    @Transaction
    default void sync(List<GeoPoint> list) {
        deleteAll();
        insertAll(list);
    }

    @Insert
    void insertAll(List<GeoPoint> list);

    @Query("DELETE FROM geopoint")
    void deleteAll();

    @Query("SELECT * FROM geopoint")
    List<GeoPoint> getAll();
}
