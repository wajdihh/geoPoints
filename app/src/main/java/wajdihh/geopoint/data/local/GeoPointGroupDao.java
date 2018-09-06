package wajdihh.geopoint.data.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Single;
import wajdihh.geopoint.data.entities.GeoPointGroup;

/**
 * Created by wajdihh on 04/09/2018.
 * Interface utilisé par Room ORM
 */

@Dao
public interface GeoPointGroupDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(GeoPointGroup point);

    @Query("SELECT * FROM GeoPointGroup")
    Single<List<GeoPointGroup>> getAll() ;

    @Query("SELECT * FROM GeoPointGroup WHERE id LIKE:id LIMIT 1")
    GeoPointGroup getGroup(String id);

    @Query("SELECT COUNT(*) FROM GeoPointGroup")
    int getCount();
}
