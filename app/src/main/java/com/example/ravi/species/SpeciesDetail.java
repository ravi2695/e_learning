package com.example.ravi.species;

import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class SpeciesDetail extends AppCompatActivity {

    TextView species_name,species_description;
    ImageView species_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnplay;
    Button earlyAge;

    String speciesId="";

    FirebaseDatabase database;
    DatabaseReference species;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_species_detail);

        database=FirebaseDatabase.getInstance();
        species=database.getReference("species");

        btnplay=(FloatingActionButton) findViewById(R.id.btnplay);
        earlyAge=findViewById(R.id.preage);
        species_description=findViewById(R.id.species_description);
        species_name=findViewById(R.id.species_name);
        species_image=findViewById(R.id.img_species);

        collapsingToolbarLayout=findViewById(R.id.collapsing);

        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent()!=null)
            speciesId = getIntent().getStringExtra("SpeciesId");

        if(!speciesId.isEmpty())
        {
            getDetailFood(speciesId);
        }

    }

    private void getDetailFood(String speciesId) {

        species.child(speciesId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Species species=dataSnapshot.getValue(Species.class);
                Picasso.with(getBaseContext()).load(species.getImage())
                        .into(species_image);

                collapsingToolbarLayout.setTitle(species.getName());
                species_name.setText(species.getName());
                species_description.setText(species.getDescription());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
