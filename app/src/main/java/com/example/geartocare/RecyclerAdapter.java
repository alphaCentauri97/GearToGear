package com.example.geartocare;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import kotlinx.coroutines.internal.LockFreeLinkedListNode;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

    Context context;
    ArrayList<String> arrayList;
    LayoutInflater inflater;

    public RecyclerAdapter(Activity context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
        this.inflater = context.getLayoutInflater();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.text,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return  viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.item.setText(arrayList.get(position));
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                View v  = inflater.inflate(R.layout.delete,null);
                String st = arrayList.get(position);
                Log.d("tag",st);
                Button btItem;

                btItem = v.findViewById(R.id.btItem);
                dialog.setView(v);
                AlertDialog alertDialog = dialog.create();

                btItem.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View view) {

                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                        Query query = ref.child("Items").orderByChild("title").equalTo(st);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dSnapshot:snapshot.getChildren()) {
                                    dSnapshot.getRef().removeValue();
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        alertDialog.dismiss();;
                    }
                });
                alertDialog.show();

                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView item;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            item = itemView.findViewById(R.id.tvitem);
        }
    }
}
