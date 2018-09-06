package wajdihh.geopoint.mvp.detail;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wajdihh.geopoint.R;
import wajdihh.geopoint.data.entities.GeoPoint;

/**
 * Created by wajdihh on 06/09/2018.
 * Adapter utilisé par la liste
 */

public class MyDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyDetailRecyclerViewAdapter.ViewHolder> {

    private List<GeoPoint> mData = new ArrayList<>();
    private LayoutInflater mInflater;

    MyDetailRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<GeoPoint> data) {
        this.mData = data;
        notifyDataSetChanged();
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rowlv, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GeoPoint point = mData.get(position);
        holder.myTextView.setText(String.format("%s , %s", point.getLatitude(), point.getLongitude()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.myTv);
        }
    }

}