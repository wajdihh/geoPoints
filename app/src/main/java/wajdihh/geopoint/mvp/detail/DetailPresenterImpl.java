package wajdihh.geopoint.mvp.detail;

import android.content.Context;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import wajdihh.geopoint.data.DataSource;
import wajdihh.geopoint.data.entities.GeoPoint;

/**
 * Created by wajdihh on 06/09/2018.
 * Implementation du presenter : voir MVP pattern
 */

public class DetailPresenterImpl implements DetailPresenter {


    private DetailView mMainView;
    private DataSource mDataSource;

    public DetailPresenterImpl(Context context) {
        this.mDataSource = new DataSource(context);
    }

    @Override
    public void attachView(DetailView v) {
        mMainView = v;
    }

    @Override
    public void loadPoints(String groupID) {

        mDataSource.loadPointByGroupId(groupID, new SingleObserver<List<GeoPoint>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<GeoPoint> geoPoints) {
                mMainView.onSuccessLoad(geoPoints);
            }

            @Override
            public void onError(Throwable e) {
                mMainView.onErrorLoad(e);
            }
        });
    }
}
