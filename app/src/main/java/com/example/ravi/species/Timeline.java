package com.example.ravi.species;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.ravi.species.Interface.ItemClickListener;
import com.example.ravi.species.ViewHolder.MenuViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;


public class Timeline extends AppCompatActivity {

    FirebaseAuth timeLine_firebaseAuth;
    FirebaseUser timeLine_firebaseUser;
    FirebaseDatabase database;
    DatabaseReference category;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        timeLine_firebaseAuth=FirebaseAuth.getInstance();
        timeLine_firebaseUser=timeLine_firebaseAuth.getCurrentUser();

        database=FirebaseDatabase.getInstance();
        category=database.getReference("Category");


        //loading menu
        recycler_menu = (RecyclerView)findViewById(R.id.recyler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);
        
        loadmenu();
    }

    private void loadmenu() {

        FirebaseRecyclerAdapter<Category,MenuViewHolder> adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class,R.layout.menu_item,MenuViewHolder.class,category) {
             @Override
            protected void populateViewHolder(MenuViewHolder viewHolder, Category model, int position) {

                viewHolder.txtMenuName.setText(model.getName());
                Picasso.with(getBaseContext()).load(model.getImage())
                        .into(viewHolder.imageView);

                final Category clickItem=model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Toast.makeText(Timeline.this,clickItem.getName(),Toast.LENGTH_SHORT).show();
                    }
                });

            }
        };
        recycler_menu.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_options,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.signout_from_species:
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(Timeline.this,login_page.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return true;
            case R.id.delete_account_of_species:
                AlertDialog.Builder dialog=new AlertDialog.Builder(Timeline.this);
                dialog.setTitle("Warning(deleting account)..");
                dialog.setMessage("after deleteing your account..you cann't be able to login again"+"are you sure for deleting the account");
                dialog.setPositiveButton("delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        timeLine_firebaseUser.delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Timeline.this,"your account has been deleted successfully",Toast.LENGTH_LONG).show();
                                    Intent intent=new Intent(Timeline.this,login_page.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);
                                }
                                else
                                {
                                    Toast.makeText(Timeline.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });
                dialog.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                        dialogInterface.dismiss();
                    }
                });
                AlertDialog alertDialog=dialog.create();
                alertDialog.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
