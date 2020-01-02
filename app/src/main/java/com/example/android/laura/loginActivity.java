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
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.laura.model.users;
import com.example.android.laura.prevalent.prevalent;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.CheckBox;

import io.paperdb.Paper;

public class loginActivity extends AppCompatActivity {
private EditText pno,password;
private Button logbtn;
//private CheckBox rememberme;
private ProgressDialog loadbar;
private TextView adminlink;
private TextView nonadmin;
    String parentdbname="users" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        logbtn=(Button)findViewById(R.id.login_btn);
        pno=(EditText)findViewById(R.id.login_phone_number);
        password=(EditText)findViewById(R.id.login_password);
        loadbar=new ProgressDialog(loginActivity.this);
       // rememberme=(CheckBox)findViewById(R.id.remember_chkb);
        adminlink=(TextView) findViewById(R.id.admin_link);
        nonadmin=(TextView)findViewById(R.id.not_admin_link);
      //  Paper.init(this);


     adminlink.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             logbtn.setText("Admin login");
             adminlink.setVisibility(View.INVISIBLE);
             nonadmin.setVisibility(View.VISIBLE);
             parentdbname="admin";
         }
     });
     nonadmin.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             logbtn.setText("login");
             adminlink.setVisibility(View.VISIBLE);
             nonadmin.setVisibility(View.INVISIBLE);
             parentdbname="users";

         }
     });
        logbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logininto();    }
        });
    }
    private void logininto()
    {
        String upno=pno.getText().toString();
        String upassword=password.getText().toString();


        if(TextUtils.isEmpty(upno))
        {
            Toast.makeText(this, "Please enter registered phone number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(upassword))
        {
            Toast.makeText(this,"Please enter correct password",Toast.LENGTH_SHORT).show();
        }
        else
        {
            loadbar.setTitle("Login account");
            loadbar.setMessage("Please wait, we are checking your credentials");
            loadbar.setCanceledOnTouchOutside(false);
            loadbar.show();

            loginaccount(upno,upassword);
        }
    }
    private void loginaccount(final String upno, final String upassword)
    {

        final DatabaseReference rootref;
        rootref= FirebaseDatabase.getInstance().getReference();
        rootref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(parentdbname).child(upno).exists()) {
                    users userdata = dataSnapshot.child(parentdbname).child(upno).getValue(users.class);
                    if (userdata.getPhone().equals(upno)) {
                        if (userdata.getPassword().equals(upassword)) {
                            if (parentdbname.equals("users")) {
                                Toast.makeText(loginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                                loadbar.dismiss();

                                Intent i = new Intent(loginActivity.this, homedisplay.class);
                                startActivity(i);
                            }
                           else if (parentdbname.equals("admin")) {
                                Toast.makeText(loginActivity.this, "login successful", Toast.LENGTH_SHORT).show();
                                loadbar.dismiss();

                                Intent i = new Intent(loginActivity.this, Categoryactivity.class);
                                startActivity(i);
                            }
                            else
                            {
                                Toast.makeText(loginActivity.this, "No user found", Toast.LENGTH_SHORT).show();
                                loadbar.dismiss();
                            }

                        } else {
                            Toast.makeText(loginActivity.this, "Incorrect password", Toast.LENGTH_SHORT).show();
                            loadbar.dismiss();
                        }

                    } else {
                        loadbar.dismiss();
                        Toast.makeText(loginActivity.this, "Account doesn't. Invalid phone number ", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                 Toast.makeText(loginActivity.this,"Account doesn't exist. Create new one",Toast.LENGTH_SHORT).show();
                 loadbar.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
