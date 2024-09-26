package com.example.annexe5;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Vector;

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

        ListView listView = findViewById(R.id.liste); // Assurez-vous que l'ID est correct

        // Créer une liste de données
        List<Map<String, Object>> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Map<String, Object> item = new HashMap<>();
            item.put("nom", "Nom " + (i + 1)); // Remplacez par le nom réel
            item.put("artiste", "Artiste " + (i + 1)); // Remplacez par l'artiste réel
            item.put("info", "Info " + (i + 1)); // Remplacez par l'info réelle
            item.put("image", R.drawable.hotboy); // Remplacez par une image réelle (assurez-vous que l'image est dans res/drawable)
            data.add(item);
        }

        // Clés correspondant aux TextViews et ImageView dans item_layout.xml
        String[] from = {"nom", "artiste", "info", "image"};
        int[] to = {R.id.nom, R.id.artiste, R.id.textView3, R.id.imageView};

        // Créer un SimpleAdapter personnalisé
        SimpleAdapter adapter = new SimpleAdapter(this, data, R.layout.liste_item, from, to);

        listView.setAdapter(adapter);

    }


    class Ecouteur implements AdapterView.OnItemClickListener{

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}