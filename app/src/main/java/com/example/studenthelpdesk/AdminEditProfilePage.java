package com.example.studenthelpdesk;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AdminEditProfilePage extends AppCompatActivity {
    TextView name,email,department,phone;
    AdminData adminData;
    ImageView profilepic;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_edit_profile_page);
        profilepic=findViewById(R.id.profilePic);
        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        department=findViewById(R.id.deptName);
        phone=findViewById(R.id.phone);
        adminData=AdminPage.adminData;
        name.setText(adminData.getAdminName());
        email.setText(adminData.getEmail());
        department.setText(adminData.getDeptName());
        phone.setText(adminData.getPhoneNumber());

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(adminData.getCollegeId()).child("Photograph").child(adminData.getEmail());
        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Glide.with(AdminEditProfilePage.this)
                        .load(uri).diskCacheStrategy(DiskCacheStrategy.ALL)
                        .error(R.drawable.profile_pic)
                        .placeholder(R.drawable.profile_pic)
                        .into(profilepic);
            }
    });
    }
    public void nameClick(View view)
    {
        AlertDialog.Builder change=new AlertDialog.Builder(this);
        change.setTitle("Edit Name");
        change.setMessage("Enter new Name");
        EditText et=new EditText(view.getContext());
        et.setText(adminData.getAdminName());
        change.setView(et);
        change.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        }).setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //save in database
            }
        });
        change.create().show();
    }
    public void phoneClick(View view)
    {
        AlertDialog.Builder change=new AlertDialog.Builder(this);
        change.setTitle("Edit Phone Number");
        change.setMessage("Enter new Phone Number");
        EditText et=new EditText(view.getContext());
        et.setInputType(InputType.TYPE_CLASS_NUMBER);
        et.setText(adminData.getPhoneNumber());
        change.setView(et);
        change.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //do nothing
            }
        }).setPositiveButton("SAVE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //save in database
            }
        });
        change.create().show();
    }
    public void nonchangeable(View view)
    {
        Toast.makeText(this,"This field in non-Editable!!",Toast.LENGTH_LONG).show();
    }
}