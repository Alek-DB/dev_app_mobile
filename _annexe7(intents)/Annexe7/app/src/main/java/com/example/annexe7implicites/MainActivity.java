package com.example.annexe7implicites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private ImageView img;

    ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button appel = findViewById(R.id.boutonAppel);
        Button map = findViewById(R.id.boutonVille);
        Button pic = findViewById(R.id.boutonPhoto);
        Button txt = findViewById(R.id.boutonMessage);
        ImageView img = findViewById(R.id.imageView);

        launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
            Bundle extras = result.getData().getExtras();
            Bitmap imageBitMap = (Bitmap) extras.get("data");
            img.setImageBitmap(imageBitMap);
        });



        appel.setOnClickListener(v -> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            call.setData(Uri.parse("tel:7897895555"));
            startActivity(call);
        });
        map.setOnClickListener(v -> {
            Intent maps = new Intent(Intent.ACTION_VIEW);
            maps.setData(Uri.parse("geo:0,0?q=Hawksbury,Ontario,Canada"));
            startActivity(maps);
        });
        pic.setOnClickListener(v -> {
            Intent photo = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            launcher.launch(photo);

        });
        txt.setOnClickListener(v -> {
            Intent sendIntent = new Intent(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        });



    }

}