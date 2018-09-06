package wajdihh.geopoint.mvp.main;

import android.content.Context;

import java.util.List;

import io.reactivex.observers.DisposableSingleObserver;
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
        mMainView =  v;
    }

    @Override
    public void loadData() {

        //Show progress
        mMainView.showProgress();
        mDataSource.loadData(new DisposableSingleObserver() {
            @Override
            public void onSuccess(Object o) {
                //Hide
                mMainView.hideProgress();
                List<GeoPointGroup> groups = (List<GeoPointGroup>) o;
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
