package com.example.annexe1

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
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

        var memos = Vector<String>()
        liste = findViewById(R.id.liste)

        val fis : FileInputStream = openFileInput("memos.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        var memo: String

        do{
            memo = br.readLine()
            if (memo != null)
                memos.add(memo)
        } while(memo != null)

        var ar = ArrayAdapter(this, android.R.layout.simple_list_item_1, memos)
        liste.adapter = ar

    }
}