package wajdihh.geopoint.data.entities;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import static android.arch.persistence.room.ForeignKey.CASCADE;

/**
 * Created by wajdihh on 03/09/2018.
 * Model du point
 */

@Entity(indices = {@Index("groupId")},foreignKeys = @ForeignKey(entity = GeoPointGroup.class,
        parentColumns = "id",
        childColumns = "groupId",
        onDelete = CASCADE))
public class GeoPoint {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private double latitude;

    private double longitude;

    @ColumnInfo(name = "geohash")
    private String geoHash;

    private String groupId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getGeoHash() {
        return geoHash;
    }

    public void setGeoHash(String geoHash) {
        this.geoHash = geoHash;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}
