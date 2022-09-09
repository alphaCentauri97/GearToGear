package com.example.geartocare;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExapndableAdapter extends RecyclerView.Adapter<ExapndableAdapter.ViewHolder> {

    Context context;
    ArrayList<ExpandableModel> list;

    public ExapndableAdapter(Context context, ArrayList<ExpandableModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expandable,null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvtitle.setText(list.get(position).getTitle());
        holder.tvdesc.setText(list.get(position).getDesc());
        holder.tvdesc.setVisibility(View.GONE);

        holder.tvtitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list.get(position).isVisible()){
                    holder.tvdesc.setVisibility(View.GONE);
                    list.get(position).setVisible(false);
                }
                else{
                    holder.tvdesc.setVisibility(View.VISIBLE);
                    list.get(position).setVisible(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvtitle,tvdesc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvdesc = itemView.findViewById(R.id.tvdesc);
            tvtitle = itemView.findViewById(R.id.tvtitle);
        }
    }
}
