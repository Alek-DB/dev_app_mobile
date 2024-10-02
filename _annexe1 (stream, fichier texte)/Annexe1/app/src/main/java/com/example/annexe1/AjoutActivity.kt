package com.example.annexe1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedWriter
import java.io.FileOutputStream
import java.io.OutputStreamWriter

class AjoutActivity : AppCompatActivity() {
    lateinit var ajout: Button
    lateinit var champ: EditText
    val ec = Ecouteur()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_ajout)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        champ = findViewById(R.id.champ)
        ajout = findViewById(R.id.btn_ajouter2)
        ajout.setOnClickListener(ec)
    }

    inner class Ecouteur : View.OnClickListener {
        override fun onClick(v: View) {
            if (v === ajout) {

                var toSave: String = champ.text.toString()

                val fos : FileOutputStream = openFileOutput("memos.txt", MODE_APPEND)
                // append pour ajouter a la fin du fichier et pas ecraser
                val osw = OutputStreamWriter(fos)
                // val osw : OutputStreamWriter = OutputStreamWriter(fos)
                val bw = BufferedWriter(osw)

                bw.write(toSave)
                bw.newLine()
                bw.close()


                finish();

            }
        }
    }
}