package com.example.annexe8b;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


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


        findViewById(R.id.gauche).setOnClickListener(v -> {
            Intent i = new Intent(this, gauheAdroite.class);
            startActivity(i);
        });
        findViewById(R.id.titre).setOnClickListener(v -> {
            Intent i = new Intent(this, titre.class);
            startActivity(i);
        });
        findViewById(R.id.splash).setOnClickListener(v -> {
            Intent i = new Intent(this, splash.class);
            startActivity(i);
        });
    }
}