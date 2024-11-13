package com.example.annexe9;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;


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

        RequestQueue rq = Volley.newRequestQueue(this);
        String url = "https://api.jsonbin.io/v3/b/6733b233ad19ca34f8c9149a?meta=false";
        //ATELIER 2B

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Traiter la réponse JSON
                        try {
                            // Afficher le JSON brut dans le TextView
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println(response.toString(2)); // Formaté avec une indentation de 2 espaces
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println(response.getJSONObject("menu").getString("header"));
                            System.out.println("-----------------------------------------------------------------");
                            JSONArray items = response.getJSONObject("menu").getJSONArray("items");
                            System.out.println(items.length());
                            System.out.println("-----------------------------------------------------------------");
                            int nullCount = 0;
                            int label_null = 0;
                            for (int i = 0; i < items.length(); i++) {
                                try {
                                    JSONObject item = items.getJSONObject(i);
                                    if (!item.has("label")) {  // Vérifier si l'attribut "label" est absent
                                        label_null++;
                                    }
                                }
                                catch (JSONException e){
                                    nullCount++;
                                }
                            }
                            System.out.println(nullCount);
                            System.out.println("-----------------------------------------------------------------");
                            System.out.println(label_null);
                            System.out.println("-----------------------------------------------------------------");

                        } catch (JSONException e) {
                            System.out.println( "Error: " + e.getMessage());
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Gérer les erreurs
                        System.out.println( "Error: " + error.getMessage());
                        System.out.println("Erreur de connexion");
                    }
                });

        // Ajouter la requête à la queue Volley
        rq.add(jsonObjectRequest);


        // Charger le fichier JSON depuis le dossier raw
        InputStream is = getResources().openRawResource(R.raw.voitures);
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStreamReader sr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(sr);
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Convertir la String en objets Java avec Gson
        String jsonString = stringBuilder.toString();

        // Utiliser GSON pour convertir la chaîne JSON en objets Java
        Gson gson = new Gson();
        VoituresList voituresList = gson.fromJson(jsonString, VoituresList.class);

        // Calculer la moyenne des prix
        List<Voiture> voitures = voituresList.getCars();
        int totalPrice = 0;
        for (Voiture voiture : voitures) {
            totalPrice += voiture.getPrice();
        }

        double averagePrice = (double) totalPrice / voitures.size();
        System.out.println(averagePrice);


    }
}