package wajdihh.geopoint.mvp.main;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import wajdihh.geopoint.R;
import wajdihh.geopoint.data.entities.GeoPointGroup;

/**
 * Created by wajdihh on 06/09/2018.
 * Adapter utilisé par la liste
 */

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

    private List<GeoPointGroup> mData = new ArrayList<>();
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    MyRecyclerViewAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }

    public void setData(List<GeoPointGroup> data) {
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
        GeoPointGroup geoPointGroup = mData.get(position);
        holder.myTextView.setText(geoPointGroup.getName());
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            myTextView = itemView.findViewById(R.id.myTv);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    GeoPointGroup getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}