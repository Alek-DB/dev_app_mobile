package com.example.tp1;

import android.content.Context;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

public class Modele implements Sujet {

    private ObservateurChangement obs;
    private Playlist playlist;
    private static Modele instance;

    public static Modele getInstance(Context ctx){
        if(instance == null){
            instance = new Modele(ctx);
        }
        return instance;
    }


    public Modele(Context context) {    //va chercher la playlist
        RequestQueue rq = Volley.newRequestQueue( context);
        String url = "https://api.jsonbin.io/v3/b/661ab8b1acd3cb34a837f284?meta=false";
        StringRequest sr = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                playlist = gson.fromJson(response, Playlist.class);

                avertirObservateurs();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("Crashed");
            }
        });
        rq.add(sr);
    }


    // méthodes de l'interface Sujet
    @Override
    public void ajouterObservateur(ObservateurChangement obs) {
        this.obs = obs;
    }
    @Override
    public void enleverObservateur(ObservateurChangement obs) {
        obs = null;
    }
    @Override
    public void avertirObservateurs() {
        obs.changement(playlist);
    }
}
