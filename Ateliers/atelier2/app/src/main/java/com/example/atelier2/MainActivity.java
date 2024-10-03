package com.example.atelier2;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Hashtable;
import java.util.Vector;

public class MainActivity extends AppCompatActivity {

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

        liste = findViewById(R.id.listeView);


        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "https://api.jsonbin.io/v3/b/637056232b3499323bfe110a?meta=false";

        JsonObjectRequest jor = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {

                    JSONArray ja = jsonObject.getJSONArray("articles");
                    Hashtable<String, Object> temp;
                    for (int i = 0; i < ja.length(); i++){
                        temp = new Hashtable<>();
                        Produit p = new Produit.Builder()
                                .setChamp1((String) ja.getJSONObject(i).get("nom"))
                                .setChamp2((String) ja.getJSONObject(i).get("prix"))
                                .build();
                        temp.put("champ1", p.champ1);
                        temp.put("champ2", p.champ2);

                        vector.add(temp);
                    }

                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), vector, R.layout.produit, new String[]{"champ1", "champ2"}, new int[]{R.id.champ1, R.id.champ2});
                    liste.setAdapter(adapter);
                    liste.setOnItemClickListener((parent, view, position, id) -> {
                        System.out.println(vector.get(position).get("nom"));
                    });
                } catch (JSONException e) {
                    System.out.println("crashed");
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        rq.add(jor);

    }
}


class Produit {
    public String champ1;
    public String champ2;

    public Produit ( Builder b )
    {
        this.champ1 = b.champ1;
        this.champ2 = b.champ2;
    }

    public static class Builder {
        private String champ1;
        private String champ2;

        public Produit build() {
            return new Produit(this); // on doit avoir coder un constructeur de Personne dans la classe prenant en paramètre un Builder comme :
        }
        public Builder() {
        }

        public Builder setPrenom(String champ1, String champ2) {
            this.champ1 = champ1;
            this.champ2 = champ2;
            return this;
        }

        public Builder setChamp1(String champ1) {
            this.champ1 = champ1;
            return this;
        }

        public Builder setChamp2(String champ2) {
            this.champ2 = champ2;
            return this;
        }
    }

}