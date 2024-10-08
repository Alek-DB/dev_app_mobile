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
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.stream.JsonReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
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
        //ATELIER 2B

        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                ListeProduit ListeArticles = gson.fromJson(response, ListeProduit.class) ;

                Hashtable<String, Object> temp;
                for (int i = 0; i < ListeArticles.articles.size(); i++){
                    temp = new Hashtable<>();
                    temp.put("champ1", ListeArticles.articles.get(i).nom);
                    temp.put("champ2", ListeArticles.articles.get(i).prix);
                    vector.add(temp);
                }


                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), vector, R.layout.produit, new String[]{"champ1", "champ2"}, new int[]{R.id.champ1, R.id.champ2});
                liste.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        rq.add(sr);


//        ATERLIER 2
//        JsonObjectRequest jor = new JsonObjectRequest(url, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject jsonObject) {
//                try {
//
//                    JSONArray ja = jsonObject.getJSONArray("articles");
//                    Hashtable<String, Object> temp;
//                    for (int i = 0; i < ja.length(); i++){
//                        temp = new Hashtable<>();
//                        Produit p = new Produit.Builder()
//                                .setNom((String) ja.getJSONObject(i).get("nom"))
//                                .setPrix((String) ja.getJSONObject(i).get("prix"))
//                                .build();
//                        temp.put("champ1", p.nom);
//                        temp.put("champ2", p.prix);
//
//                        vector.add(temp);
//                    }
//
//                    SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(), vector, R.layout.produit, new String[]{"champ1", "champ2"}, new int[]{R.id.champ1, R.id.champ2});
//                    liste.setAdapter(adapter);
//                    liste.setOnItemClickListener((parent, view, position, id) -> {
//                        System.out.println(vector.get(position).get("nom"));
//                    });
//                } catch (JSONException e) {
//                    System.out.println("crashed");
//                    throw new RuntimeException(e);
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//
//            }
//        });
    }
}

class ListeProduit{
    public Vector<Produit> articles;

}


class Produit {
    public String nom;
    public String prix;

    public Produit ( Builder b )
    {
        this.nom = b.nom;
        this.prix = b.prix;
    }

    public static class Builder {
        private String nom;
        private String prix;

        public Produit build() {
            return new Produit(this); // on doit avoir coder un constructeur de Personne dans la classe prenant en paramètre un Builder comme :
        }
        public Builder() {
        }


        public Builder setNom(String champ1) {
            this.nom = champ1;
            return this;
        }

        public Builder setPrix(String champ2) {
            this.prix = champ2;
            return this;
        }
    }

}