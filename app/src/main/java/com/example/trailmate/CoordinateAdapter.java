package com.example.trailmate;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.trailmate.models.Coordinate;

import java.util.List;

public class CoordinateAdapter extends RecyclerView.Adapter<CoordinateViewHolder> {

    private List<Coordinate> mCoordinatesList;
    private OnItemClickListener onItemClickListener;
    private OnItemLongClickListener onItemLongClickListener;

    public CoordinateAdapter(List<Coordinate> coordinatesList) {
        this.mCoordinatesList = coordinatesList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    // Interface for item long click events
    public interface OnItemLongClickListener {
        void onItemLongClick(int position);
    }

    // Setter methods for click listeners
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        this.onItemLongClickListener = listener;
    }

    @NonNull
    @Override
    public CoordinateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_coordinate, parent, false);
        return new CoordinateViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CoordinateViewHolder holder, final int position) {
        Coordinate coordinate = mCoordinatesList.get(position);
        holder.titleTextView.setText(coordinate.getTitle());

        // Handle item click
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });

        // Handle item long click
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemLongClickListener != null) {
                    onItemLongClickListener.onItemLongClick(position);
                }
                return true; // consume the long click event
            }
        });
    }



    @Override
    public int getItemCount() {
        return mCoordinatesList.size();
    }
}
