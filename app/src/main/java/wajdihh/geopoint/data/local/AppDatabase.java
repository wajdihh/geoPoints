package wajdihh.geopoint.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import wajdihh.geopoint.data.entities.GeoPoint;
import wajdihh.geopoint.data.entities.GeoPointGroup;

/**
 * Created by wajdihh on 04/09/2018.
 * Initi la database Room
 */

@Database(entities = {GeoPoint.class, GeoPointGroup.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    public abstract GeoPointDao geoPointDao();
    public abstract GeoPointGroupDao geoPointGroupDao();

    private static  AppDatabase INSTANCE;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "geoPoint.db")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}