package com.example.examen2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Vector;

public class question3 extends AppCompatActivity {



    private Button btn;
    private ImageView montgolfiere;
    private EditText text;

    private ArrayList<AnimatorSet> sets = new ArrayList<>();
    private int position = 0;
    private Vector<String> albums;
    private Vector<String> given_answers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_question3);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });



        btn = findViewById(R.id.btn);
        montgolfiere = findViewById(R.id.montgolfiere);
        text = findViewById(R.id.txt);

        //  QUESTION 2 ------------------------


        // SET 1 -------
        ObjectAnimator move_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_X,400);
        ObjectAnimator move_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_Y,-800);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(move_x,move_y);

        sets.add(set);

        // SET 2 -------
        move_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_X,-300);
        move_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_Y,-1100);

        set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(move_x,move_y);

        sets.add(set);


        //SET 3 -------
        //DEPLACEMENT
        move_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_X,0);
        move_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_Y,-1300);

        set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(move_x,move_y);

        //SCALE OPACITY
        ObjectAnimator scale_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.SCALE_X,10);
        ObjectAnimator scale_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.SCALE_Y,10);
        ObjectAnimator opacity = ObjectAnimator.ofFloat(montgolfiere, ImageView.ALPHA,0);

        AnimatorSet setFinal = new AnimatorSet();
        setFinal.setDuration(4000);
        setFinal.playTogether(scale_x,scale_y,opacity);

        // SET AVEC LES DEUX AUTRES SET
        AnimatorSet setComplet = new AnimatorSet();
        setComplet.playSequentially(set,setFinal);

        sets.add(setComplet);


        given_answers = new Vector<>();
        btn.setOnClickListener(v -> {

            String answer = text.getText().toString().toUpperCase();
            text.setText("");
            Boolean match = false;

            // regarde si answer est bonne
            for(int i = 0; i < albums.size(); i++){
                if(answer.equals(albums.get(i))){
                    match = true;
                    break;
                }
            }

            if(match){

                //regarde si answer pas déja donné
                for(int i = 0; i < given_answers.size(); i++){
                    if(answer.equals(given_answers.get(i))){
                        Toast.makeText(this, "Vous avez déjà donné cette réponse", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                given_answers.add(answer);

                if(position <= 2){
                    if(position != 0 && sets.get(position-1).isRunning())
                        return;
                    sets.get(position).start();
                    position++;
                }
                if(position == 3){
                    btn.setText("Félicitation");
                    btn.setEnabled(false);
                }
            }
            else{
                Toast.makeText(this, "Mauvaise réponse", Toast.LENGTH_SHORT).show();
            }
        });



        albums = new Vector<>();
        String url = "https://api.jsonbin.io/v3/b/673cf708acd3cb34a8ab4e3c?meta=false";

        RequestQueue rq = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        // Traiter la réponse JSON
                        try {
                            // Afficher le JSON brut dans le TextView
                            JSONArray items = response.getJSONArray("items");
                            for (int i = 0; i < items.length(); i++) {
                                JSONObject obj = (JSONObject) items.get(i);
                                albums.add(obj.get("name").toString().toUpperCase());
                            }
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
    }
}