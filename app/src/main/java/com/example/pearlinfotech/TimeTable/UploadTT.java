package com.example.pearlinfotech.TimeTable;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.pearlinfotech.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.raywenderlich.android.validatetor.ValidateTor;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class UploadTT extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private Uri mImageUri;
    Toolbar mToolbar;
    ValidateTor validateTor=new ValidateTor();
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_tt);
        Toolbar toolbar1=findViewById(R.id.ftoolbar);
        toolbar1.setTitle("Time Table");
        toolbar1.setTitleTextColor(getResources().getColor(R.color.colorAccent));
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar1.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mStorageRef= FirebaseStorage.getInstance().getReference("Uploads");
        mDatabaseRef= FirebaseDatabase.getInstance().getReference("Uploads");
        mButtonChooseImage = findViewById(R.id.choose);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mButtonUpload = findViewById(R.id.upload);
        mProgressBar = findViewById(R.id.progress_bar);
        mImageView = findViewById(R.id.image_view);
        String title=mEditTextFileName.getText().toString();
        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });
        mButtonUpload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mUploadTask != null && mUploadTask.isInProgress()) {
                        Toast.makeText(UploadTT.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                    } else {
                        uploadFile();
                        Toast.makeText(UploadTT.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                    }
                }
            });


    }
    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            Log.d("TAG",mImageUri.toString());
            Picasso.get()
                    .load(mImageUri)
                    .into(mImageView, new Callback() {
                        @Override
                        public void onSuccess() {
                        }

                        @Override
                        public void onError(Exception e) {
                            Toast.makeText(UploadTT.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (mImageUri != null) {
            StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
                    + "." + getFileExtension(mImageUri));
            fileReference.putFile(mImageUri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(0);
                            }
                        }, 500);

                        Toast.makeText(UploadTT.this, "Upload successful", Toast.LENGTH_LONG).show();
                        Upload upload = new Upload(mEditTextFileName.getText().toString().trim(), downloadUri.toString());
                        String uploadId = mDatabaseRef.push().getKey();
                        mDatabaseRef.child(uploadId).setValue(upload);

                    } else {
                        Toast.makeText(UploadTT.this, "upload failed: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}

