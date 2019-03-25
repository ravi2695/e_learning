package com.example.ravi.species.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ravi.species.Interface.ItemClickListener;
import com.example.ravi.species.R;

public class SpeciesViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView species_name;
    public ImageView species_image;

    private ItemClickListener itemClickListener;

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public SpeciesViewHolder(View itemView) {
        super(itemView);

        species_name = (TextView)itemView.findViewById(R.id.species_name);
        species_image = (ImageView)itemView.findViewById(R.id.species_image);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);
    }
}
