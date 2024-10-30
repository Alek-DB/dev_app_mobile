package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Settings extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Switch s = findViewById(R.id.switch1);

        Intent intent = getIntent();
        s.setChecked(intent.getBooleanExtra("replay", false));


        ImageButton finish_btn = findViewById(R.id.finsh);
        finish_btn.setOnClickListener(v -> {

            Intent resultIntent = new Intent();

            resultIntent.putExtra("replay", s.isChecked()); // Mettre la donnée
            setResult(RESULT_OK, resultIntent); // Indiquer que le résultat est OK et ajouter l'intent
            finish(); // Fermer l'activité

        });

    }
}