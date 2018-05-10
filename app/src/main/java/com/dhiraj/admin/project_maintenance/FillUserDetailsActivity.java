package com.dhiraj.admin.project_maintenance;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.Toolbar;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class FillUserDetailsActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final int CHOOSE_IMAGE = 101;
    ImageView profilePicture;
    EditText name;
    Uri uriProfileImage;
    Button saveButton;
    ProgressBar progressBar;
    String profileImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_user_details);
        mAuth = FirebaseAuth.getInstance();
        name = findViewById(R.id.user_name);
        profilePicture = findViewById(R.id.profile_pic);
        saveButton = findViewById(R.id.save);
        progressBar = findViewById(R.id.progressbar);
        Toolbar toolbar = findViewById(R.id.toolbar);
        profilePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showImageChooser();
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveUserInfo();
            }
        });
        loadUserInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(mAuth.getCurrentUser()==null){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==CHOOSE_IMAGE && resultCode== RESULT_OK && data != null && data.getData()!=null){
            uriProfileImage = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uriProfileImage);
                profilePicture.setImageBitmap(bitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile Image"), CHOOSE_IMAGE);
    }

    private void uploadImage(){
        progressBar.setVisibility(View.VISIBLE);
        StorageReference profilePictureReference =
                FirebaseStorage.getInstance().getReference("profilepics/"+System.currentTimeMillis()+".jpg");
        if(uriProfileImage!=null){
            profilePictureReference.putFile(uriProfileImage)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                           progressBar.setVisibility(View.GONE);
                           profileImageUrl = taskSnapshot.getDownloadUrl().toString();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void saveUserInfo(){
        String displayName = name.getText().toString();
        if(displayName.isEmpty()){
            name.setError("Name required");
            name.requestFocus();
            return;
        }
        FirebaseUser user = mAuth.getCurrentUser();
        if(user!=null){
            UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                    .setDisplayName(displayName)
                    .setPhotoUri(Uri.parse(profileImageUrl))
                    .build();

            user.updateProfile(profile)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(),"Profile updated",Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(getApplicationContext(),UserHomeActivity.class));
                            }
                        }
                    });
        }
    }

    private void loadUserInfo(){
        FirebaseUser user = mAuth.getCurrentUser();

        if(user!=null) {
            if (user.getPhotoUrl() != null) {
                String photoUrl = user.getPhotoUrl().toString();
                Glide.with(this)
                        .load(photoUrl)
                        .into(profilePicture);
            }
            if (user.getDisplayName() != null) {
                String displayName = user.getDisplayName();
                name.setText(displayName);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuLogout:
                FirebaseAuth.getInstance().signOut();
                finish();
                startActivity(new Intent(this,RegistrationActivity.class));
                break;
        }
        return true;
    }
}
