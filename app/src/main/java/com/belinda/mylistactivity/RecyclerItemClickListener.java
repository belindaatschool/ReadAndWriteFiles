package com.belinda.mylistactivity;

// Source: https://stackoverflow.com/questions/24471109/recyclerview-onclick

import android.content.Context;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private OnItemClickListener mListener;
    private GestureDetector mGestureDetector;

    ///////////////////////////////////////////////////////////////////////////
    /**
     * Interface for click listeners
     * This interface is used to define click listeners for the RecyclerView items (rows) in the adapter class
     */
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
        public void onLongItemClick(View view, int position);
    }
    ///////////////////////////////////////////////////////////////////////////

    /**
     * Constructor for the RecyclerItemClickListener
     */
    public RecyclerItemClickListener(Context context, final RecyclerView recyclerView, OnItemClickListener listener) {
        mListener = listener;
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    // Here we call the onItemClick method of the OnItemClickListener interface
                    mListener.onItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
                return true;
            }

            @Override
            public void onLongPress(MotionEvent e) {
                View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                if (child != null && mListener != null) {
                    // Here we call the onLongItemClick method of the OnItemClickListener interface
                    mListener.onLongItemClick(child, recyclerView.getChildAdapterPosition(child));
                }
            }
        });
    }

    /*
     * Overriding the methods of RecyclerView.OnItemTouchListener
     * to handle clicks on the RecyclerView items (rows) in the adapter class
     * before they are handled by the ViewHolder
     */
    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        return true;
    }

    /**
     * Process a touch event as part of a gesture that was claimed by returning true from
     * a previous call to {@link #onInterceptTouchEvent}.
     */
    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        this.mGestureDetector.onTouchEvent(e);
    }

    /**
     * Called when a child of RecyclerView does not want RecyclerView and its ancestors to
     * intercept touch events with
     */
    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {}
}
