package com.example.geartocare;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ExpandableListView;
import android.widget.TextView;


import com.example.geartocare.databinding.ActivityBookingDetailBinding;
import com.ms.square.android.expandabletextview.ExpandableTextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BookingDetailActivity extends AppCompatActivity {

    ArrayList<ExpandableModel> list;
    ExapndableAdapter adapter;
    RecyclerView recyclerView;
    ActivityBookingDetailBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBookingDetailBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        int unicode =  0x1F60A;
        String emojis = getEmoji(unicode);

        binding.bio.setText("Professionals service by them. Best part is gear to car provides price details up front and informs the technician arrival time while booking itself.i m Happy with the service"+emojis);
        list = new ArrayList<>();
        list.add(new ExpandableModel("Which engine oil do you use ?",null,false));
        list.add(new ExpandableModel("Are there any additional charges other that mentioned price",null,false));
        list.add(new ExpandableModel("Which engine oil do you use ?",null,false));

        adapter = new ExapndableAdapter(this,list);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private String getEmoji(int unicode) {
        return new String(Character.toChars(unicode));
    }
}