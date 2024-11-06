package com.example.annexe8b;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class titre extends AppCompatActivity {


    ObjectAnimator anim, anim2, anim3;

    AnimatorSet set;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_titre);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        anim = ObjectAnimator.ofFloat(findViewById(R.id.txtTitre), View.SCALE_X, 1);
        anim2 = ObjectAnimator.ofFloat(findViewById(R.id.txtTitre), View.SCALE_Y, 1);
        anim3 = ObjectAnimator.ofFloat(findViewById(R.id.txtTitre), View.ALPHA, 1);

        set = new AnimatorSet();
        set.playTogether(anim, anim2, anim3);
        set.setInterpolator(new BounceInterpolator());
        set.setDuration(3000);

        findViewById(R.id.playTitre).setOnClickListener(v -> {
            set.start();
        });
    }
}