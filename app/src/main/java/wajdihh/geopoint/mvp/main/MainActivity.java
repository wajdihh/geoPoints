package wajdihh.geopoint.mvp.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import wajdihh.geopoint.R;
import wajdihh.geopoint.data.entities.GeoPointGroup;
import wajdihh.geopoint.mvp.detail.DetailActivity;

public class MainActivity extends AppCompatActivity implements MainView, MyRecyclerViewAdapter.ItemClickListener {

    private MainPresenter mainPresenter;
    private MyRecyclerViewAdapter adapter;

    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * FindBy ID
         */
        mProgress = findViewById(R.id.myProgress);
        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.myRVGroups);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this);
        adapter.setClickListener(this);
        recyclerView.setAdapter(adapter);

        /**
         * Init du MVP
         */
        mainPresenter = new MainPresenterImpl(this);
        //Attacher le view au presenter
        mainPresenter.attachView(this);

        //Commencer le chargement des data au lancement
        mainPresenter.loadData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.action_refresh:
                mainPresenter.loadData();
                break;
            default:
                break;
        }

        return true;
    }

    @Override
    public void showProgress() {
        mProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgress.setVisibility(View.GONE);
    }

    @Override
    public void onSuccessLoad(List<GeoPointGroup> groupList) {
        //Set data
        adapter.setData(groupList);
        Toast.makeText(this, getString(R.string.msg_sucess_data), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onErrorLoad(Throwable throwable) {
        Toast.makeText(this, getString(R.string.msg_error_data), Toast.LENGTH_LONG).show();
    }

    /**
     * Quand on clique sur l element
     *
     * @param view
     * @param position
     */
    @Override
    public void onItemClick(View view, int position) {

        String id = adapter.getItem(position).getId();
        String name = adapter.getItem(position).getName();

        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("ID", id);
        intent.putExtra("NAME", name);
        startActivity(intent);
    }
}
