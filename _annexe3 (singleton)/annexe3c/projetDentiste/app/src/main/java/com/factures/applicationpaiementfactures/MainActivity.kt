package com.factures.applicationpaiementfactures

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.CheckBox
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


class MainActivity : AppCompatActivity() {

    lateinit var dent1: LinearLayout
    lateinit var dent2: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        /*
        dent1 = findViewById(R.id.dent1)
        var num1:String
        var canal1:Boolean
        var notes1:String

        dent2 = findViewById(R.id.dent2)
        var num2:String
        var canal2:Boolean
        var notes2:String



        try {
            val fis = openFileInput("fichier.ser")
            val ois = ObjectInputStream(fis)

            println("fichier trouvé")
            num1 = ois.readObject() as String
            canal1 = ois.readBoolean()
            notes1 = ois.readObject() as String

            num2 = ois.readObject() as String
            canal2 = ois.readBoolean()
            notes2 = ois.readObject() as String

            (dent1.getChildAt(0) as EditText).setText(num1)
            (dent1.getChildAt(1) as CheckBox).isChecked = canal1
            (dent1.getChildAt(2) as EditText).setText(notes1)

            (dent2.getChildAt(0) as EditText).setText(num2)
            (dent2.getChildAt(1) as CheckBox).isChecked = canal2
            (dent2.getChildAt(2) as EditText).setText(notes2)


            println("terminé")
        } catch (ignored: IOException) {
        } catch (ignored: ClassNotFoundException) {
        }

         */



    }

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        println("onCreate")
    }

    override fun onStart() {
        super.onStart()
        println("onStart")
    }

    override fun onResume() {
        super.onResume()
        println("onResume")
    }

    override fun onRestart() {
        super.onRestart()
        println("onRestart")
    }

    override fun onPause() {
        super.onPause()
        println("onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        println("onDestroy")
    }


    override fun onStop() {
        super.onStop()
        println("onStop")
        /*
        try {
            val fos = openFileOutput("fichier.ser", MODE_PRIVATE)
            val oos = ObjectOutputStream(fos)
            oos.writeObject(  ((dent1.getChildAt(0) as EditText).text.toString()) )
            oos.writeBoolean( ((dent1.getChildAt(1) as CheckBox).isChecked)  )
            oos.writeObject( ((dent1.getChildAt(2) as EditText).text.toString())   )


            oos.writeObject(  ((dent2.getChildAt(0) as EditText).text.toString()) )
            oos.writeBoolean( ((dent2.getChildAt(1) as CheckBox).isChecked)  )
            oos.writeObject( ((dent2.getChildAt(2) as EditText).text.toString())   )
        } catch (ignored: IOException) {
        }

         */

    }
}