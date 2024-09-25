package com.example.annexe4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MainActivity extends AppCompatActivity {


    TextView salutation;
    Utilisateur user;
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


        salutation = findViewById(R.id.text_main);

        //regarde s'il est serialiser
        try {
            FileInputStream fis = openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            user = (Utilisateur) ois.readObject();
            salutation.setText( "Bonjour " + user.prenom + " " + user.nom  );
        } catch (IOException | ClassNotFoundException ignored) {

        }

        //sinon regarde s'il est dans le savedInstance Bundle
        if(user == null){
            try {
                user = (Utilisateur) savedInstanceState.getSerializable("Utilisateur");
                salutation.setText( "Bonjour " + user.prenom + " " + user.nom  );
            } catch (Exception ignored) {

            }
        }

        ActivityResultLauncher<Intent> lanceur;
        lanceur = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new CallBackInfo());

        Button next = findViewById(R.id.btn_main);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),infoActivity.class);
                lanceur.launch(intent);
            }
        });

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if(user != null)
            outState.putSerializable("Utilisateur", user);
        try {
            FileOutputStream fos =  openFileOutput("fichier.ser", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(user);

        } catch (IOException ignored) {

        }
    }

    public class CallBackInfo implements ActivityResultCallback<ActivityResult> {
        // appelé quand je reviens  dans cette activité, retour du boomerang
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK) {
                user = (Utilisateur) result.getData().getSerializableExtra("Utilisateur");
                salutation.setText( "Bonjour " + user.prenom + " " + user.nom  );
            }

        }
    }



}