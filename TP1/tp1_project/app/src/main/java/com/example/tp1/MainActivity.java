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


import com.bumptech.glide.Glide;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity implements ObservateurChangement {


    Playlist playlist;
    Sujet modele;



    Vector<Hashtable<String, Object>> vector = new Vector<>();
    ListView liste;

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
        modele = Modele.getInstance(this);
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
        System.out.println("First page");
        try {
            FileInputStream fis = openFileInput("fichier.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            song_index = ois.read();
            song_position = ois.readLong();

            ois.close();
            fis.close();
        } catch (IOException ignored) {
            System.out.println("crashed in fichier ");
        }

        System.out.println(song_index);

        if(song_position != 0 || song_index !=0){   //si j'ai sauvegardé, va a la toune
            go_to(song_index,song_position);
        }
        else{       //sinon montre la liste view
            for(Track m: playlist.music){
                Hashtable<String, Object> temp = new Hashtable<>();
                temp.put("title", m.getTitle());
                temp.put("artist", m.getArtist());
                temp.put("album", m.getAlbum());
                temp.put("image", m.getImage());
                vector.add(temp);
            }

            SimpleAdapter adapter = new SimpleAdapter(this, vector, R.layout.un_item, new String[]{"title", "artist", "album", "image"}, new int[]{R.id.textNom, R.id.textView, R.id.textView3, R.id.imageView2}){
                @Override
                public void setViewImage(ImageView v, String value) {
                    Glide.with(getApplicationContext()).load( value ).into( v );
                }
            };
            liste = findViewById(R.id.listview);
            liste.setAdapter(adapter);
            liste.setOnItemClickListener((parent, view, position, id) -> {
                go_to(position,0);
            });
        }

    }

    private void go_to(int index, long position){
        Intent intent = new Intent(getApplicationContext(),songActivity.class);
        intent.putExtra("index", index);
        intent.putExtra("time", position);
        intent.putExtra("playlist", playlist.music);
        startActivity(intent);
    }
}