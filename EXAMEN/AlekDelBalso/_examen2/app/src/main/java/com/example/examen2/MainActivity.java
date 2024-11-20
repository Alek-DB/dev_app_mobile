package com.example.examen2;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    private Button btn;
    private ImageView montgolfiere;
    private EditText text;

    private ArrayList<AnimatorSet> sets = new ArrayList<>();
    private int position = 0;

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

        btn = findViewById(R.id.btn);
        montgolfiere = findViewById(R.id.montgolfiere);
        text = findViewById(R.id.txt);


        //  QUESTION 1 ------------------------
//        ObjectAnimator move_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_X,400);
//        ObjectAnimator move_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_Y,-800);
//
//        AnimatorSet set = new AnimatorSet();
//        set.setDuration(2000);
//        set.playTogether(move_x,move_y);
//
//        btn.setOnClickListener(v -> {
//            set.start();
//        });




        //  QUESTION 2 ------------------------
        ObjectAnimator move_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_X,400);
        ObjectAnimator move_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_Y,-800);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(move_x,move_y);

        sets.add(set);


        move_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_X,-300);
        move_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_Y,-1100);

        set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(move_x,move_y);

        sets.add(set);

        move_x = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_X,0);
        move_y = ObjectAnimator.ofFloat(montgolfiere, ImageView.TRANSLATION_Y,-1300);

        set = new AnimatorSet();
        set.setDuration(2000);
        set.playTogether(move_x,move_y);

        sets.add(set);

        btn.setOnClickListener(v -> {
            if(position <= 2){
                if(position != 0 && sets.get(position-1).isRunning())
                    return;
                sets.get(position).start();
                position++;
            }
        });

    }
}