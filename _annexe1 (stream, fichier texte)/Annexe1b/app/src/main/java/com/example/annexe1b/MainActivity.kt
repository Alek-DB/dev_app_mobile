package com.example.annexe1b

import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.util.Scanner
import java.util.Vector

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //val ligne = nbLigne()
        //val character = nbChar()
        //val nombreC = nbC()
        //ecrire()


        //println(ligne)
        //println(character)
        //println(nombreC)
        System.out.println("lalo")
        var planetes = trouver_planetes()

        System.out.println(planetes.count())

        for (planete in planetes) {
            System.out.println(planete.name)
        }


    }

    fun trouver_planetes(): Vector<Planete>{

        val planetes = Vector<Planete>()

        val fis : FileInputStream = openFileInput("planete.txt")
        fis.use {
            val s = Scanner(fis)
            s.use {
                while (s.hasNext()){
                    val nom = s.next()
                    val satellite = s.next().toInt()

                    planetes.add(Planete(nom,satellite))
                }
            }
        }

        return planetes
    }


    fun nbLigne(): Int {
        val fis : FileInputStream = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        var lignes = 0
        while (br.readLine() != null){
            lignes++;
        }

        br.close()

        var textLigne = findViewById<TextView>(R.id.TextLigne)
        textLigne.setText(lignes.toString())

        return lignes
    }

    fun nbChar(): Int {

        val fis : FileInputStream = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        var characters = 0
        var lignes = br.readLine()
        while (lignes != null){
            characters += lignes.length
            lignes = br.readLine()
        }

//        br.forEachLine {
//            ligne -> characters+= ligne.length
//        }

        br.close()

        var textChar = findViewById<TextView>(R.id.TextChar)
        textChar.setText(characters.toString())

        return characters
    }

    fun nbC(): Int {

        val fis : FileInputStream = openFileInput("fichier.txt")
        val isr = InputStreamReader(fis)
        val br = BufferedReader(isr)

        var nombreC = 0
        var c: Int? = null
        c = br.read()
        do{
            if(c == 99)
                nombreC++
            c = br.read()
        } while (c != -1)


        br.close()

        var TextC = findViewById<TextView>(R.id.TextC)
        TextC.setText(nombreC.toString())

        return nombreC
    }

    fun ecrire(){
        var toSave: String = "ALEK"

        val fos : FileOutputStream = openFileOutput("fichier.txt", MODE_APPEND)
        // append pour ajouter a la fin du fichier et pas ecraser
        val osw = OutputStreamWriter(fos)
        // val osw : OutputStreamWriter = OutputStreamWriter(fos)
        val bw = BufferedWriter(osw)

        bw.write(toSave)
        bw.newLine()
        bw.close()
    }

}


public class Planete(var name:String, var satellite: Int){
    //on peut initialiser des variable ici
    // init{} pour initialiser si on veut faire qqchose sur les variables
    // constructor secondaire
}