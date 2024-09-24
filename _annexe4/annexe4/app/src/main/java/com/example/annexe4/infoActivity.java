package com.example.annexe4;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class infoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_info);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button next = findViewById(R.id.btn_info);
        EditText nom = findViewById(R.id.text_nom);
        EditText prenom = findViewById(R.id.text_prenom);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Utilisateur u = new Utilisateur(nom.getText().toString(), prenom.getText().toString());
            }
        });

    }
}