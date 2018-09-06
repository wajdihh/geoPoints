package wajdihh.geopoint.mvp.detail;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.List;

import wajdihh.geopoint.R;
import wajdihh.geopoint.data.entities.GeoPoint;

/**
 * Created by wajdihh on 06/09/2018.
 * Details pour afficher les points d un group
 */

public class DetailActivity extends AppCompatActivity implements DetailView {

    private DetailPresenter mDetailPresenter;
    private MyDetailRecyclerViewAdapter adapter;
    private String groupName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.myRVPoints);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyDetailRecyclerViewAdapter(this);
        recyclerView.setAdapter(adapter);

        /**
         * Init du MVP
         */
        mDetailPresenter = new DetailPresenterImpl(this);
        //Attacher le view au presenter
        mDetailPresenter.attachView(this);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String id = bundle.getString("ID");
            groupName = bundle.getString("NAME");

            //Commencer le chargement des data au lancement
            mDetailPresenter.loadPoints(id);

        }
    }

    @Override
    public void onSuccessLoad(List<GeoPoint> points) {
        //Set data
        adapter.setData(points);

        //set title
        setTitle(groupName + " (" + points.size() + ")");
    }

    @Override
    public void onErrorLoad(Throwable throwable) {
        Toast.makeText(this, getString(R.string.msg_error_data), Toast.LENGTH_LONG).show();
    }

}