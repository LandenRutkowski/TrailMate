package com.example.trailmate;

import android.view.View;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

public class CoordinateViewHolder extends RecyclerView.ViewHolder {
    TextView titleTextView;
    // Add other views as needed

    public CoordinateViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.titleTextView);
        // Initialize other views here
    }
}
