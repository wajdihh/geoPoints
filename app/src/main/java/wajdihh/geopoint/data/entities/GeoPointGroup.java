package wajdihh.geopoint.data.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by wajdihh on 03/09/2018.
 * Model du Group des points
 */

@Entity
public class GeoPointGroup {


    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    public GeoPointGroup(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
