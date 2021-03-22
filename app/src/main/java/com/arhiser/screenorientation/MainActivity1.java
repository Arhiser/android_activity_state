package com.arhiser.screenorientation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;

public class MainActivity1 extends AppCompatActivity {

    private static final int REQUEST_GET_PHOTO = 5352;

    ImageView avatar;
    TextView firstNameText;
    TextView lastNameText;
    Spinner countrySelection;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        avatar = findViewById(R.id.avatar);
        firstNameText = findViewById(R.id.first_name);
        lastNameText = findViewById(R.id.last_name);
        countrySelection = findViewById(R.id.place);

        countrySelection.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.countries)));

        avatar.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(intent, "Tack Image"), REQUEST_GET_PHOTO);
        });

        if (savedInstanceState != null) {
            imageUri = savedInstanceState.getParcelable("uri");
        }
        if (imageUri != null) {
            avatar.setImageURI(imageUri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                imageUri = data.getData();
                avatar.setImageURI(imageUri);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable("uri", imageUri);
        super.onSaveInstanceState(outState);
    }
}