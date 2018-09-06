package wajdihh.geopoint.mvp.main;

import android.content.Context;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import wajdihh.geopoint.data.DataSource;
import wajdihh.geopoint.data.entities.GeoPointGroup;

/**
 * Created by wajdihh on 06/09/2018.
 * Implementation du presenter : voir MVP pattern
 */

public class MainPresenterImpl implements MainPresenter {


    private MainView mMainView;
    private DataSource mDataSource;

    public MainPresenterImpl(Context context) {
        this.mDataSource = new DataSource(context);
    }

    @Override
    public void attachView(MainView v) {
        mMainView = v;
    }

    @Override
    public void loadData() {

        //Show progress
        mMainView.showProgress();
        mDataSource.loadData(new SingleObserver<List<GeoPointGroup>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onSuccess(List<GeoPointGroup> geoPointGroups) {
                //Hide
                mMainView.hideProgress();
                List<GeoPointGroup> groups = geoPointGroups;
                mMainView.onSuccessLoad(groups);
            }

            @Override
            public void onError(Throwable e) {
                //Hide
                mMainView.hideProgress();
            }
        });
    }
}
