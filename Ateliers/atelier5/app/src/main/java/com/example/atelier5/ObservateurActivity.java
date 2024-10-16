package com.example.atelier5;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ObservateurActivity extends AppCompatActivity implements ObservateurChangement {

    TextView texte;
    Sujet leModele;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_observateur);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        texte = findViewById(R.id.texte);


    }

    @Override
    protected void onStart() {
        super.onStart();
        leModele = new Modele();
        leModele.ajouterObservateur(this); // on ajouter l'observateur ( l'activité ) au modèle ( le sujet )
    }

    @Override
    public void changement(int valeur) {
        texte.setText("nouvelle valeur : " +valeur); // le modèle a changé son état !
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leModele.enleverObservateur(this);
    }
}