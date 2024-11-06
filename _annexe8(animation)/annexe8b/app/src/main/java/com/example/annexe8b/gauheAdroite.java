package com.example.annexe8b;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class gauheAdroite extends AppCompatActivity {

    ObjectAnimator anim;

    boolean isleft = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gauhe_adroite);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        anim = ObjectAnimator.ofFloat(findViewById(R.id.carre), View.X,600);
        findViewById(R.id.playGauche).setOnClickListener(v -> {
            if(isleft)
                anim.start();
            else
                anim.reverse();
            isleft = !isleft;
        });
    }
}