package com.example.geartocare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.geartocare.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseDatabase database;
    ArrayList<String> list;
    RecyclerAdapter recyclerAdapter;
    private DatabaseReference reference;

    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.next,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.next){
            Intent intent = new Intent(MainActivity.this, BookingDetailActivity.class);
            startActivity(intent);
        }
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.topbar);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        reference = FirebaseDatabase.getInstance().getReference();
        database = FirebaseDatabase.getInstance();
        list = new ArrayList<>();

        GetDataFromFirebase();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                View v  = getLayoutInflater().inflate(R.layout.add_item,null);

                EditText etItem;
                Button btItem;

                etItem = v.findViewById(R.id.etItem);
                btItem = v.findViewById(R.id.btItem);
                dialog.setView(v);
                AlertDialog alertDialog = dialog.create();

                btItem.setOnClickListener(new View.OnClickListener() {

                    String item;

                    @Override
                    public void onClick(View view) {
                        if(etItem.getText().toString().isEmpty()){
                            Toast.makeText(MainActivity.this, "Please Enter Item", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            item = etItem.getText().toString();
                            InsertData(item);
                        }

                        alertDialog.dismiss();;
                    }
                });
                alertDialog.show();
            }
        });

    }

    private void GetDataFromFirebase() {
        Query query = reference.child("Items");
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ClearAll();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    String s = snapshot1.child("title").getValue().toString();
                    list.add(s);
                }
                recyclerAdapter = new RecyclerAdapter(MainActivity.this,list);
                binding.recyclerView.setAdapter(recyclerAdapter);
                recyclerAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void InsertData(String item) {
        String id = database.getReference().push().getKey();
        database.getReference().child("Items").child(id).child("title").setValue(item).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Item Inserted", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }
    private void ClearAll(){
        if(list != null){
            list.clear();
            if (recyclerAdapter != null){
                recyclerAdapter.notifyDataSetChanged();
            }
        }

        list = new ArrayList<>();
    }
}