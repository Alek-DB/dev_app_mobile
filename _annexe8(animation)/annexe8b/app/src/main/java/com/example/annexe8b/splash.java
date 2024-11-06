package com.example.annexe8b;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.OvershootInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class splash extends AppCompatActivity {

    ObjectAnimator anim, anim2;

    AnimatorSet set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anim = ObjectAnimator.ofFloat(findViewById(R.id.soleil), View.TRANSLATION_Y,1100);
        anim.setInterpolator(new OvershootInterpolator());

        anim2 = ObjectAnimator.ofFloat(findViewById(R.id.soleil), View.SCALE_X,5);

        set = new AnimatorSet();
        set.setDuration(1000);
        set.playSequentially(anim,anim2);

        findViewById(R.id.playSplash).setOnClickListener(v -> {
            set.start();
        });
    }
}