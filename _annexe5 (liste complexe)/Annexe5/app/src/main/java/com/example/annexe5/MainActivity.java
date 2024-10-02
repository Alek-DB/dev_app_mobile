package com.example.annexe5;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Hashtable;
import java.util.Objects;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {
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
        l = findViewById(R.id.listview);
        Hashtable<String, Object> temp = new Hashtable<>();
        temp.put("position", "3");
        temp.put("nom", "Touch Me");
        temp.put("date", "22/03/1986");
        temp.put("image", R.drawable.touchme);
        vector.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "8");
        temp.put("nom", "Nothings gonna stop me now");
        temp.put("date", "30/05/1986");
        temp.put("image", R.drawable.nothing);
        vector.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "31");
        temp.put("nom", "Santa Maria");
        temp.put("date", "28/03/1998");
        temp.put("image", R.drawable.santamaria);
        vector.add(temp);

        temp = new Hashtable<>();
        temp.put("position", "108");
        temp.put("nom", "Hot Boy");
        temp.put("date", "10/04/2018");
        temp.put("image", R.drawable.hotboy);
        vector.add(temp);

        SimpleAdapter adapter = new SimpleAdapter(this, vector, R.layout.un_item, new String[]{"position", "nom", "date", "image"}, new int[]{R.id.textView, R.id.textNom, R.id.textView3, R.id.imageView2});
        l.setAdapter(adapter);
        l.setOnItemClickListener((parent, view, position, id) -> {
            System.out.println(vector.get(position).get("nom"));
        });

        
    }
}

