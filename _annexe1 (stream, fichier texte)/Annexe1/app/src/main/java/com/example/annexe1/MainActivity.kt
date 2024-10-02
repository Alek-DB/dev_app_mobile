package com.example.annexe1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var ajout: Button
    lateinit var affiche: Button
    lateinit var quit: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        ajout = findViewById(R.id.btn_ajout)
        affiche = findViewById(R.id.btn_afficher)
        quit = findViewById(R.id.btn_quit)

        val ec = Ecouteur()
        ajout.setOnClickListener(ec)
        affiche.setOnClickListener(ec)
        quit.setOnClickListener(ec)
    }


    private inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View) {
            if (v === ajout) {
                val vers_ajout = Intent(applicationContext, AjoutActivity::class.java)
                startActivity(vers_ajout)
            } else if (v === affiche) {
                val vers_affiche = Intent(applicationContext, ListeActivity::class.java)
                startActivity(vers_affiche)
            } else if (v === quit) {
                System.exit(0)
            }
        }
    }
}