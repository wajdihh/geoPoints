package wajdihh.geopoint.data.remote;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import wajdihh.geopoint.R;

/**
 * Created by wajdihh on 03/09/2018.
 *
 * Client retrofit 2
 */

public class Retrofit2Client {


    private static Retrofit retrofit = null;

    /**
     * Retrounrer le client retrofit (HTTP)
     * @param context
     * @return
     */
    public static Retrofit getClient(Context context) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(context.getString(R.string.url_base))
                    .build();
        }
        return retrofit;
    }
}
