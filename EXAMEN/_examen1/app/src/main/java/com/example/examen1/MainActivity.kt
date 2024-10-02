package com.example.examen1

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Scanner
import java.util.Vector


class MainActivity : AppCompatActivity() {
    private lateinit var utilisation: TextView
    private lateinit var langages: TextView
    private var nbLang: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v: View, insets: WindowInsetsCompat ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        utilisation = findViewById(R.id.utilisation)
        langages = findViewById(R.id.nb_languages)


        var fis : FileInputStream
        val languagesVector = Vector<Language>()

        try {
            fis = openFileInput("langage.txt")
            fis.use {
                val s = Scanner(fis)
                s.useDelimiter(",")
                s.use {
                    while (s.hasNext()){
                        val nom = s.next()
                        val _2024 = s.next().toInt()
                        val _2019 = s.next().toInt()
                        val _2012 = s.next().toInt()
                        languagesVector.add(Language(nom,_2024, _2019, _2012))
                    }
                }
            }
            for (lang in languagesVector)
                if (lang._2012 == 99)
                    nbLang++
            langages.text = "Nombre de langages qui n'existaient pas en 2012 : " + nbLang.toString()
        }
        catch (e : Exception){
            langages.text = "Fichier de langage non trouvé"
        }




        var temps = ""
        try {
            fis = openFileInput("temps.ser")
            var ois = ObjectInputStream(fis)
            ois.use {
                temps = ois.readObject() as String
            }
            utilisation.text = "Dernière utilisation : " + temps
        }
        catch (e : Exception){
            println("first")
            utilisation.text = "1ère utilisation"
        }


    }

    override fun onStop() {
        super.onStop()


        val date = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val text = date.format(formatter)

        val fos : FileOutputStream = openFileOutput("temps.ser", MODE_PRIVATE)
        val oos = ObjectOutputStream(fos)

        oos.writeObject(text)
        oos.close()
        finish();
    }

    internal inner class Language(var nom: String, var _2024: Int, var _2019: Int, var _2012: Int)
}