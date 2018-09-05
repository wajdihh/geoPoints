package wajdihh.geopoint;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import wajdihh.geopoint.data.DataSource;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new DataSource(this).getAllPoint();
    }
}
