package com.example.android.laura;

import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class Categoryactivity extends AppCompatActivity {
private ImageView tshirts,sports,women,sweater;
private ImageView glass,hat,headphone,shoe;
private ImageView bag,phone,laptop,watch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoryactivity);
        tshirts=(ImageView)findViewById(R.id.tshirt);
        sports=(ImageView)findViewById(R.id.sports);
        women=(ImageView)findViewById(R.id.women);
        sweater=(ImageView)findViewById(R.id.sweatshirt);
        glass=(ImageView)findViewById(R.id.glasses);
        hat=(ImageView)findViewById(R.id.hats);
        headphone=(ImageView)findViewById(R.id.headphones);
        shoe=(ImageView)findViewById(R.id.shoes);
        bag=(ImageView)findViewById(R.id.purses);
        phone=(ImageView)findViewById(R.id.mobiles);
        laptop=(ImageView)findViewById(R.id.laptop);
        watch=(ImageView)findViewById(R.id.watches);


        tshirts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Tshirts");
                startActivity(i);
            }
        });
        sports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Sportshirt");
                startActivity(i);
            }
        });
        women.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Women dresses");
                startActivity(i);
            }
        });
        sweater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Sweatshirt");
                startActivity(i);
            }
        });
        glass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Glasses");
                startActivity(i);
            }
        });
        hat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Hat");
                startActivity(i);
            }
        });
        headphone.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Headphones");
                startActivity(i);
            }
        });
        shoe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","shoes");
                startActivity(i);
            }
        });
        bag.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Bags");
                startActivity(i);
            }
        });
        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Mobile phone");
                startActivity(i);
            }
        });
        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Laptop");
                startActivity(i);
            }
        });
        watch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Categoryactivity.this,AdminActivity.class);
                i.putExtra("category","Watches");
                startActivity(i);
            }
        });
    }
}
