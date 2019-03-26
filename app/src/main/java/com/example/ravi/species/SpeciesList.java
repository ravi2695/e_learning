package com.example.ravi.species;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.ravi.species.Interface.ItemClickListener;
import com.example.ravi.species.ViewHolder.SpeciesViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SpeciesList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference speciesList;

    String categoryId="";

    FirebaseRecyclerAdapter<Species,SpeciesViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species_list);

        database=FirebaseDatabase.getInstance();
        speciesList=database.getReference("species");

        recyclerView=(RecyclerView)findViewById(R.id.recycler_species);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //getting intent
        if(getIntent()!=null)
        {
            categoryId=getIntent().getStringExtra("CategoryId");
        }
        if(!categoryId.isEmpty()&&categoryId!=null)
        {
            loadListSpecies(categoryId);
        }
    }

    private void loadListSpecies(String categoryId) {
        adapter=new FirebaseRecyclerAdapter<Species, SpeciesViewHolder>(Species.class,R.layout.species_item,SpeciesViewHolder.class
                ,speciesList.orderByChild("MenuId").equalTo(categoryId)) {
            @Override
            protected void populateViewHolder(SpeciesViewHolder viewHolder, Species model, int position) {

                viewHolder.species_name.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage()).into(viewHolder.species_image);

                final Species local=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                       Intent speciesDetails=new Intent(SpeciesList.this,SpeciesDetail.class);
                       speciesDetails.putExtra("SpeciesId",adapter.getRef(position).getKey());
                       startActivity(speciesDetails);
                    }
                });
            }
        };

        recyclerView.setAdapter(adapter);
    }
}
