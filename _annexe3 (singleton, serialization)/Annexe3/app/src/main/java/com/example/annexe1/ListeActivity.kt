package com.example.annexe1

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.ArrayList
import java.util.Vector

class ListeActivity : AppCompatActivity() {
    lateinit var liste: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_liste)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        liste = findViewById(R.id.liste)
        var ar = ArrayAdapter(this, android.R.layout.simple_list_item_1, convertir())
        liste.adapter = ar

    }

    fun convertir():Vector<String>{

        SingletonMemo.getInstance(applicationContext).deserialiser();

        var listeTexteMemo = Vector<String>()
        var listeMemo = SingletonMemo.getInstance(applicationContext)._memo

        for(memo in listeMemo){
            listeTexteMemo.add(memo.texte)
        }

        return listeTexteMemo
    }


}