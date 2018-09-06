package wajdihh.geopoint.data;

import android.content.Context;
import android.location.Location;

import com.fonfon.geohash.GeoHash;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import wajdihh.geopoint.R;
import wajdihh.geopoint.data.entities.GeoPoint;
import wajdihh.geopoint.data.entities.GeoPointGroup;
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

    private final static int PRECISION = 3;

    private Context mContext;
    private GeoPointRemoteDataSource mGeoPointRemoteDS;
    private GeoPointLocalDataSource mGeoPointLocalDS;

    public DataSource(Context context) {
        mContext = context;
        //Init
        mGeoPointRemoteDS = Retrofit2Client.getClient(context).create(GeoPointRemoteDataSource.class);
        //Init
        mGeoPointLocalDS = new GeoPointLocalDataSource(context);
    }

    /**
     * Permet de
     * 1 - recuperer la liste des points
     * 2 - Classer par group
     * 3 - Sauvegarder dans la base
     * 4 - Retourner la liste des groupes
     */
    public void loadData(SingleObserver<List<GeoPointGroup>> observer) {

        mGeoPointRemoteDS.getAllPointsURL()
                // faire l'iteration de chaque URL de la liste
                .flatMapIterable(url -> url)
                // Recuperer chaque point de chaque URL et retourner la liste des points à la fin
                .flatMap(mGeoPointRemoteDS::getPoint)
                //Ajouter la GeoHash au point recuperé avant de le sauvegardé dans la base
                .map(this::buildPointWithGeoHash).toList()
                // Synchroniser la Liste dans le cache
                /**
                 * Dans le cas ou la liste presente des millions des data on peut decouper la liste par lot
                 * et sauvegarder par lot les data
                 *
                 * Retourner le 10000 element de la liste pour faire un insert dans le cache chaque Lot de 10000 items
                 * .flatMap(mGeoPointRemoteDS::getPoint).buffer(10000)
                 */
                .doOnSuccess(mGeoPointLocalDS::sync).toObservable()
                //Quand on termine la synchro on recuperer le contenu de la table group pour l afficher dans la liste
                .flatMap(mGeoPointLocalDS::getAllGroups)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    /**
     * Retourner les ponints local pour un group donnée
     * @param groupID
     * @param observer
     */
    public void loadPointByGroupId(String groupID, SingleObserver<List<GeoPoint>> observer) {

        mGeoPointLocalDS.getAllPointForGroup(groupID)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    /**
     * Cette methode permet d'ajouter le GeoHash a un point
     * Le geoHash permet de regrouper les points par localisation la plus proche
     * Voir : https://tech.willhaben.at/geo-clustering-3-000-000-points-on-the-fly-a-brief-how-to-9f04d8d5b3a7
     *
     * @param g
     */
    private GeoPoint buildPointWithGeoHash(GeoPoint g) {


        Location location = new Location("geohash");
        location.setLatitude(g.getLatitude());
        location.setLongitude(g.getLongitude());
        // Voir cette Librairie qui permet de faire le geoHash : https://github.com/drfonfon/android-geohash
        String hash = GeoHash.fromLocation(location, 9).toString();
        g.setGeoHash(hash);

        /**
         * Associer chaque point à un group
         * D'apres le principe de GeoHash, le code generé (Le hash) represente un token unique pour un point geolocalisé
         * apres pour regrouper les pointes les plus proches, il suffit d'appliquer un filtre sur les premiers caractères de chaque hash de chaque points\
         * et la precision (Le rayon de la zone de regroupement) sera definie par le nombre de caractere à apprendre en compte lors du regroupement
         * Exemple
         *  point 1 : Hash = u0bcec21k
         *  point 2 : Hash = gbqx1p4uj
         *  point 3 : Hash = u0d15e245
         *
         *  Si on regroupe par precision de 2 caractere on aura point 1 et point 3 dans la meme zone car leurs hash commence par le meme prefix : u0
         */
        //Generer un group id apartir du hash avec une precision de 3
        String groupId = hash.substring(0, PRECISION);
        //Si le groupe n'existe pas on doit le creer dans la table group
        if (mGeoPointLocalDS.getGroup(groupId) == null) {
            //Generer un nom de cette forme : Groupe 1, Groupe 2 etc...
            String groupName = mContext.getString(R.string.lab_group) + " " + mGeoPointLocalDS.getCount();
            // ajouter le groupe dans la base
            mGeoPointLocalDS.insertGroup(new GeoPointGroup(groupId, groupName));
            //definir le group ID pour le point
            g.setGroupId(groupId);
        } else
            // Si le group existe, on associe juste le point a ce groupe
            g.setGroupId(groupId);


        return g;
    }


}
