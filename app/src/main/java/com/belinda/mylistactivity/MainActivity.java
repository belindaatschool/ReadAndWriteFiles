package com.belinda.mylistactivity;

import java.util.ArrayList;


import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


// Based on Vogella
public class MainActivity extends Activity {
    TextView tvSelected;
    RecyclerView recyclerView;
    MyViewAdapter adapter;
    ArrayList<Person> artists;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_main);

        initViews();

        artists = new ArrayList<Person>();

        // setup RecyclerView
        adapter = new MyViewAdapter(this, artists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // add click listener to the recyclerView
        ItemClickListener itemClickListener = new ItemClickListener(); // nested class below
        RecyclerItemClickListener gridListener = new RecyclerItemClickListener(this, recyclerView, itemClickListener);
        recyclerView.addOnItemTouchListener(gridListener);
    }

    private void initViews() {
        tvSelected = findViewById(R.id.tvSelected);
        recyclerView = findViewById(R.id.list);

        findViewById(R.id.btnReadRsc).setOnClickListener(this::initialize);
        findViewById(R.id.btnWriteFile).setOnClickListener(this::writeFile);
        findViewById(R.id.btnReadFile).setOnClickListener(this::readFile);
        findViewById(R.id.btnWriteJson).setOnClickListener(this::writeJson);
        findViewById(R.id.btnReadJson).setOnClickListener(this::readJson);
    }

    public void initialize(View v) {
        FileReadWrite.readFileFromResources(artists, this);
        adapter.notifyDataSetChanged();
    }

    public void readFile(View v) {
        FileReadWrite.readFile(artists, this);
        adapter.notifyDataSetChanged();
    }

    public void writeFile(View v) {
        FileReadWrite.writeFile(artists, this);
    }

    public void writeJson(View v) {
        JsonReadWrite jsonReadWrite = new JsonReadWrite<Person>();
        if (!jsonReadWrite.writeJsonArrayList(artists, "artists.json", this))
            Toast.makeText(this, "Failed Writing JSON", Toast.LENGTH_LONG).show();
    }

    public void readJson(View v) {
        JsonReadWrite jsonReadWrite = new JsonReadWrite<Person>();
        ArrayList<Person> artists2 = jsonReadWrite.readJsonArrayList("artists.json", this, Person.class);
        if (artists2 != null) {
            artists.clear();
            artists.addAll(artists2);
            adapter.notifyDataSetChanged();
        } else
            Toast.makeText(this, "Failed reading JSON", Toast.LENGTH_LONG).show();
    }

    //Add on click capabilities to the list

    /**
     * A nested class
     * Adds click listeners to the RecyclerView items (rows)
     * using the RecyclerItemClickListener class.
     * It implements the OnItemClickListener interface to define the click listeners
     */
    private class ItemClickListener implements RecyclerItemClickListener.OnItemClickListener {
        @Override
        public void onItemClick(View view, int position) {
            Toast.makeText(getApplicationContext(), "select: " + artists.get(position), Toast.LENGTH_LONG).show();
            tvSelected.setText(artists.get(position).toString());
        }

        @Override
        public void onLongItemClick(View view, int position) {
            Toast.makeText(getApplicationContext(),
                    "del: " + artists.get(position),
                    Toast.LENGTH_LONG).show();
            artists.remove(position);
            adapter.notifyDataSetChanged(); // Update the ListView
        }
    }
}

