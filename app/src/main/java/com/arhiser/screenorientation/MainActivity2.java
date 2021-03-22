package com.arhiser.screenorientation;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.arhiser.screenorientation.state.MainState;
import com.arhiser.screenorientation.state.StateManager;

public class MainActivity2 extends AppCompatActivity {

    private static final int REQUEST_GET_PHOTO = 5352;

    ImageView avatar;
    TextView firstNameText;
    TextView lastNameText;
    Spinner countrySelection;

    MainState state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        state = StateManager.getState(this, new MainState());

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

        if (state.imageUri != null) {
            avatar.setImageURI(state.imageUri);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_GET_PHOTO && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                state.imageUri = data.getData();
                avatar.setImageURI(state.imageUri);
            }
        }
    }
}