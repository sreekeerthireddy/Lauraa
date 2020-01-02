package com.example.android.laura;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
     private Button createbutton;
     private EditText name,pno,password;
     private ProgressDialog loadbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        createbutton =(Button)findViewById(R.id.register_btn);
        name=(EditText)findViewById(R.id.register_username);
        pno=(EditText)findViewById(R.id.register_phone_number);
        password=(EditText)findViewById(R.id.register_password);
        loadbar= new ProgressDialog(this);

        createbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              createaccount();
            }
        });

    }
    private void createaccount()
    {
      String uname=name.getText().toString();
      String upno=pno.getText().toString();
      String upassword=password.getText().toString();
      if(TextUtils.isEmpty(uname)) {
          Toast.makeText(this,"Please enter name",Toast.LENGTH_SHORT).show();
      }
      else if(TextUtils.isEmpty(upno))
      {
          Toast.makeText(this,"please enter valid phone number",Toast.LENGTH_SHORT).show();

      }
      else if(TextUtils.isEmpty(upassword))
      {
          Toast.makeText(this,"please enter password",Toast.LENGTH_SHORT).show();
      }
      else
      {
          loadbar.setTitle("Create Account");
          loadbar.setMessage("Please wait,we are checking credentials");
          loadbar.setCanceledOnTouchOutside(false);
          loadbar.show();
          validate(uname,upassword,upno);
      }
    }
    private void validate(final String uname, final String upassword, final String upno)
    {
       final DatabaseReference rootref;
       rootref= FirebaseDatabase.getInstance().getReference();
       rootref.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               if(!(dataSnapshot.child("users").child(upno).exists()))
               {
                    HashMap<String,Object> usermap=new HashMap<>();
                    usermap.put("phone",upno);
                    usermap.put("password",upassword);
                    usermap.put("name",uname);
                    rootref.child("users").child(upno).updateChildren(usermap)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                   if(task.isSuccessful())
                                   {   loadbar.dismiss();
                                       Toast.makeText(RegisterActivity.this,"Successfully created",Toast.LENGTH_SHORT).show();

                                       Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                       startActivity(i);
                                   }
                                   else
                                   {   loadbar.dismiss();
                                       Toast.makeText(RegisterActivity.this,"Error occurrred,try again",Toast.LENGTH_SHORT).show();

                                   }
                                }
                            });
               }
               else{
                   Toast.makeText(RegisterActivity.this,"Account already exists",Toast.LENGTH_SHORT).show();
                   loadbar.dismiss();
               }
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });
    }
}
