 package com.example.android.laura;
import java.util.Calendar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.HashMap;

public class AdminActivity extends AppCompatActivity {

     private String categoryname,pdes,prate,pname,savedate,savetime;
     private ImageView productimage;
     private EditText prodname,proddes,prodpri;
     private Button add;
     private static final int gallerypick=1;
     private Uri imageuri;
     private StorageReference storageref;
     private DatabaseReference dataref;
     private ProgressDialog loadbar;
     private String key;
     private String downloaduri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
productimage=(ImageView)findViewById(R.id.productimage);
prodname=(EditText)findViewById(R.id.prodname);
proddes=(EditText)findViewById(R.id.proddes);
prodpri=(EditText)findViewById(R.id.prodprice);
add=(Button)findViewById(R.id.add);
loadbar=new ProgressDialog(this);

        categoryname=getIntent().getExtras().get("category").toString();
        Toast.makeText(AdminActivity.this,categoryname,Toast.LENGTH_SHORT).show();
        storageref = FirebaseStorage.getInstance().getReference().child("Product Images");
        dataref = FirebaseDatabase.getInstance().getReference().child("Products");

        productimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opengallery();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addproduct();
            }
        });

    }
    private void opengallery()
    {
        Intent i=new Intent();
        i.setAction(Intent.ACTION_GET_CONTENT);
        i.setType("image/*");
        startActivityForResult(i,gallerypick);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode==gallerypick && resultCode==RESULT_OK && data!=null)
        {
            imageuri=data.getData();
            productimage.setImageURI(imageuri);

        }
    }
    private void addproduct()
    {
        pdes=proddes.getText().toString();
        prate=prodpri.getText().toString();
        pname=prodname.getText().toString();
        if(imageuri==null)
        {
            Toast.makeText(AdminActivity.this,"Image is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pname))
        {
            Toast.makeText(AdminActivity.this,"product name is mandatory",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(pdes))
        {
            Toast.makeText(AdminActivity.this,"product description is necessary",Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(prate))
        {
            Toast.makeText(AdminActivity.this,"Product price is mandatory",Toast.LENGTH_SHORT).show();
        }
        else
        {

            saveproductinfo();

        }
    }
    private void saveproductinfo()
    {
        loadbar.setTitle("Add new product");
        loadbar.setMessage("Please wait while we are uploading");
        loadbar.setCanceledOnTouchOutside(false);
        loadbar.show();
       Calendar calender = Calendar.getInstance();
        SimpleDateFormat currentdate = new SimpleDateFormat("MMM dd,yyyyy");
        savedate = currentdate.format(calender.getTime());
        SimpleDateFormat currenttime = new SimpleDateFormat("hh:mm:ss a");
        savetime = currenttime.format(calender.getTime());

        key = savedate + savetime;
        final StorageReference filepath = storageref.child(imageuri.getLastPathSegment() + key + ".jpg");
        final UploadTask uploadtask = filepath.putFile(imageuri);
        uploadtask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                String msg = e.getMessage();
                Toast.makeText(AdminActivity.this, "Error:" + msg, Toast.LENGTH_SHORT).show();
                loadbar.dismiss();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                Toast.makeText(AdminActivity.this, "Uploaded to storage", Toast.LENGTH_SHORT).show();
                Task<Uri> uritask = uploadtask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        downloaduri = filepath.getDownloadUrl().toString();
                        return filepath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {

                        if (task.isSuccessful()) {
                            downloaduri = task.getResult().toString();
                            Toast.makeText(AdminActivity.this, "got url successfully", Toast.LENGTH_SHORT).show();
                            storagetobase();
                        }
                        else
                        {
                            Toast.makeText(AdminActivity.this,"error in getting urll",Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });
    }

    private void storagetobase()
        {
            HashMap<String,Object> pmap=new HashMap<>();
            pmap.put("pid",key);
            pmap.put("date",savedate);
            pmap.put("time",savetime);
            pmap.put("description",pdes);
            pmap.put("image",downloaduri);
            pmap.put("category",categoryname);
            pmap.put("price",prate);
            pmap.put("name",pname);
            dataref.child(key).updateChildren(pmap)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                       if(task.isSuccessful())
                       {
                           Intent i=new Intent(AdminActivity.this,Categoryactivity.class);
                           startActivity(i);
                           Toast.makeText(AdminActivity.this,"Uploaded succcessfully",Toast.LENGTH_SHORT).show();;
                           loadbar.dismiss();
                       }
                       else
                       {
                           Toast.makeText(AdminActivity.this,"Error in uploading",Toast.LENGTH_SHORT).show();
                           loadbar.dismiss();
                       }
                        }
                    });



        }



}

