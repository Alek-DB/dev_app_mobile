package com.example.annexe8;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


//    ObjectAnimator animation2;
//    View object;
//
//    Button bouton;

    ObjectAnimator animation;
    LinearLayout layout;

    ImageView galon, bouteille, verre;
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


//        object = findViewById(R.id.object);
//        bouton = findViewById(R.id.start);
//
//        Path p = new Path();
//        p.moveTo(50,200);
//        p.lineTo(900,200);
//        p.lineTo(900,600);
//        p.arcTo(400, 600 ,900, 1000,0,359,false);
//
//        animation = ObjectAnimator.ofFloat(object, "translationX",300);
//        animation.setDuration(3000);
//        animation.setInterpolator(new BounceInterpolator());
//        animation.setRepeatCount(ValueAnimator.INFINITE);
//        animation.setRepeatMode(ValueAnimator.REVERSE);
//
//        animation = ObjectAnimator.ofFloat(object, "x", "y", p);
//        //animation.setDuration(3000);
//        animation.setInterpolator(new BounceInterpolator());
//        animation.setRepeatCount(ValueAnimator.INFINITE);
//        animation.setRepeatMode(ValueAnimator.REVERSE);
//
//
//        animation2 = ObjectAnimator.ofArgb(object, "backgroundColor", Color.RED, Color.BLUE);
//        animation2.setRepeatCount(ValueAnimator.INFINITE);
//        animation2.setRepeatMode(ValueAnimator.REVERSE);
//
//        AnimatorSet set = new AnimatorSet();
//        set.playTogether(animation, animation2);
//        set.setDuration(3000);
//
//
//        bouton.setOnClickListener(v -> set.start());

        layout = findViewById(R.id.layout);

        animation = ObjectAnimator.ofFloat(layout, "translationY",0);

        findViewById(R.id.click).setOnClickListener(v -> {
            if(layout.getTranslationY() == 0) animation.reverse();
            else animation.start();
        });

        galon = findViewById(R.id.galon);
        bouteille = findViewById(R.id.bouteille);
        verre = findViewById(R.id.verre);

        galon.setOnClickListener(v -> {
            System.out.println("galon");
        });
        bouteille.setOnClickListener(v -> {
            System.out.println("bouteille");
        });
        verre.setOnClickListener(v -> {
            System.out.println("verre");
        });
    }


    public static int pxToDp(int px) {
        return (int) (px / Resources.getSystem().getDisplayMetrics().density);
    }

    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


}