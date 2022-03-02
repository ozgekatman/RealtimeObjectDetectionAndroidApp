package com.pinarakdag.detectiondictionary.AssetWordList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pinarakdag.detectiondictionary.R;

import java.util.ArrayList;

public class DbAdapter extends RecyclerView.Adapter<DbAdapter.DbViewHolder> {
    ArrayList<DbModelClass> objDbModelClassArrayList;

    public DbAdapter(ArrayList<DbModelClass> objDbModelClassArrayList) {
        this.objDbModelClassArrayList = objDbModelClassArrayList;
    }

    @NonNull
    @Override
    public DbViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View singleRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new DbViewHolder(singleRow);
    }

    @Override
    public void onBindViewHolder(@NonNull DbViewHolder holder, int position) {
        DbModelClass objDbModelClass = objDbModelClassArrayList.get(position);
        holder.en.setText(objDbModelClass.getEn());
        holder.tr.setText(objDbModelClass.getTr());


    }

    @Override
    public int getItemCount() {
        return objDbModelClassArrayList.size();
    }

    public static class DbViewHolder extends RecyclerView.ViewHolder{

        TextView en;
        TextView tr;


        public DbViewHolder(@NonNull View itemView) {
            super(itemView);
            en = itemView.findViewById(R.id.txt_en);
            tr = itemView.findViewById(R.id.txt_tr);

        }
    }
}
