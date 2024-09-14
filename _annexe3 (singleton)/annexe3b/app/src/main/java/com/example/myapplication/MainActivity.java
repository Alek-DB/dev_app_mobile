package com.example.myapplication;

import android.os.Bundle;
import android.widget.SeekBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    SeekBar s1;
    SeekBar s2;
    SeekBar s3;

    ArrayList<Integer> liste;
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
        liste = new ArrayList<>();
        s1 = findViewById(R.id.seekBar);
        s2 = findViewById(R.id.seekBar2);
        s3 = findViewById(R.id.seekBar3);
        s1.setProgress(0);
        s2.setProgress(0);
        s3.setProgress(0);


        try {
            FileInputStream fis = openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            liste = (ArrayList<Integer>) ois.readObject();
            s1.setProgress(liste.get(0));
            s2.setProgress(liste.get(1));
            s3.setProgress(liste.get(2));
        } catch (IOException | ClassNotFoundException ignored) {

        }

    }



    @Override
    protected void onStop() {
        super.onStop();

        try {
            FileOutputStream fos =  openFileOutput("fichier.ser", MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            liste = new ArrayList<>();
            liste.add(s1.getProgress());
            liste.add(s2.getProgress());
            liste.add(s3.getProgress());
            oos.writeObject(liste);

        } catch (IOException ignored) {

        }


    }
}