package com.example.android.laura;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.example.android.laura.model.products;
import com.example.android.laura.viewholder.productviewholder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import io.paperdb.Paper;

//import io.paperdb.Paper;

public class homedisplay extends AppCompatActivity
{



    private RecyclerView rview;
    private DatabaseReference dref;
    RecyclerView.LayoutManager lmanager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homedisplay);
       // Paper.init(this);


        dref= FirebaseDatabase.getInstance().getReference().child("Products");
        rview=findViewById(R.id.recycler_menu);
        rview.setHasFixedSize(true);
        lmanager=new LinearLayoutManager(this);
        rview.setLayoutManager(lmanager);

    }

    @Override
    public void onStart()
    {
        super.onStart();
        FirebaseRecyclerOptions<products> options= new FirebaseRecyclerOptions.Builder<products>()
                .setQuery(dref,products.class)
                .build();

        FirebaseRecyclerAdapter<products,productviewholder> adapter=new FirebaseRecyclerAdapter<products, productviewholder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final productviewholder holder, int position, @NonNull final products model) {
                holder.nameview.setText(model.getName());
              //  holder.desview.setText(model.getDescription());
                Picasso.get().load(model.getImage()).into(holder.iview);
             holder.delete .setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {

                     Intent intent = new Intent(Intent.ACTION_SENDTO);
                     intent.setData(Uri.parse("mailto:")); // only email apps should handle this
                     intent.putExtra(Intent.EXTRA_SUBJECT, "Laura orders");

                     intent.putExtra(Intent.EXTRA_TEXT, "Product name- "+model.getName()+"\n"+"price is- 800");

                     if (intent.resolveActivity(getPackageManager()) != null) {
                         startActivity(intent);
                     }


                 }
             });


            }
            @NonNull
            @Override
            public productviewholder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.productlayout,parent,false);
                productviewholder holder=new productviewholder(view);
                return holder;
            }
        };
        rview.setAdapter(adapter);
        adapter.startListening();
    }



   /*@Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }




}
