package com.example.tp1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;


import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements ObservateurChangement {


    Playlist playlist;
    Sujet modele;



    Vector<Hashtable<String, Object>> vector = new Vector<>();
    ListView l;

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

    }


    @Override
    protected void onStart() {
        super.onStart();            //créer le modele, donc va chercher la playlist
        modele = new Modele(this);
        modele.ajouterObservateur(this); // on ajouter l'observateur ( l'activité ) au modèle ( le sujet )
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        modele.enleverObservateur(this);
    }

    @Override
    public void changement(Playlist p) {

        playlist = p;

        int song_index = 0;
        long song_position = 0;

        //voir si j'ai sauvegarder qqpart
        try {
            FileInputStream fis = openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            song_index = ois.read();
            song_position = ois.readLong();

            ois.close();
            fis.close();
        } catch (IOException ignored) {
            System.out.println("crashed in get_songs");
        }

        if(song_position != 0 || song_index !=0){   //si j'ai sauvegardé, va a la toune
            Intent intent = new Intent(getApplicationContext(),songActivity.class);
            intent.putExtra("index", song_index);
            intent.putExtra("time", song_position);
            intent.putExtra("playlist", playlist.music);
            startActivity(intent);
        }
        else{       //sinon montre la liste view

            l = findViewById(R.id.listview);
            for(Track m: playlist.music){
                Hashtable<String, Object> temp = new Hashtable<>();
                temp.put("position", m.getArtist());
                temp.put("nom", m.getTitle());
                temp.put("date", m.getDuration());
                temp.put("image", m.getImage());
                vector.add(temp);
            }


            SimpleAdapter adapter = new SimpleAdapter(this, vector, R.layout.un_item, new String[]{"position", "nom", "date", "image"}, new int[]{R.id.textView, R.id.textNom, R.id.textView3, R.id.imageView2}){
                @Override
                public void setViewImage(ImageView v, int value) {
                    super.setViewImage(v, value);
                }
            };
            l.setAdapter(adapter);
            l.setOnItemClickListener((parent, view, position, id) -> {
                Intent intent = new Intent(getApplicationContext(),songActivity.class);
                intent.putExtra("index", position);
                intent.putExtra("playlist", playlist.music);
                startActivity(intent);
            });
        }

    }
}