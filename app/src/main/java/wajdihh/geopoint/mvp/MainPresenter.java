package wajdihh.geopoint.mvp;

import android.view.View;

/**
 * Created by wajdihh on 03/09/2018.
 *
 * Pour respecter le principe de MVP, cette interface represente le Presenter
 */

public interface  MainPresenter {

    void attachView(View v);

    void loadData();

}
