package com.belinda.mylistactivity;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

// Based on code from Vogella tutorial
// + http://theopentutorials.com/tutorials/android/listview/android-custom-listview-with-image-and-text-using-arrayadapter/

/**
 * An adapter class for the RecyclerView for displaying Person objects
 */
public class MyViewAdapter extends RecyclerView.Adapter<MyViewAdapter.PersonViewHolder>
{
    // This arrayb adapter handles *both* array & arraylist
    // This is done for demo purposes only!
    // Normally we will need just one of them
    private Context context;
    private ArrayList<Person> valuesList;

    /**
     * Constructor for an ArrayList
     */
    public MyViewAdapter(Context _context, ArrayList<Person> _valuesList) {
        this.context = _context;
        this.valuesList = _valuesList;
    }

    /**
     * Connection between the adapter and the ViewHolder.
     * Connecting item xml of the ViewHolder to the elements,
     * and creating the ViewHolder
     */
    @Override
    public PersonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_item, parent, false);
        return new PersonViewHolder(itemView);
    }

    /**
     * Binding the data to the ViewHolder
     * @param holder   The ViewHolder which should be updated to represent the contents of the
     *                 item at the given position in the data set.
     * @param position The position of the item within the adapter's data set.
     */
    @Override
    public void onBindViewHolder(@NonNull PersonViewHolder holder, int position) {
        holder.tvFirstName.setText(valuesList.get(position).getFirstName());
        holder.tvLastName.setText(valuesList.get(position).getLastName());
        holder.tvPhone.setText(valuesList.get(position).getPhone());

        // change the icon for Gender
        Gender g = valuesList.get(position).getGender();
        holder.imgIcon.setImageResource(GenderUtils.setImage(g));
    }

    @Override
    public int getItemCount() {
        return valuesList.size();
    }


    /**
     * A ViewHolder class that holds each of the elements in the RecyclerView
     */
    public class PersonViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFirstName;
        public TextView tvLastName;
        public TextView tvPhone;
        public ImageView imgIcon;

        public PersonViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvLastName = itemView.findViewById(R.id.tvLastName);
            tvPhone = itemView.findViewById(R.id.tvPhone);
            imgIcon = itemView.findViewById(R.id.imgIcon);
        }
    }
}
