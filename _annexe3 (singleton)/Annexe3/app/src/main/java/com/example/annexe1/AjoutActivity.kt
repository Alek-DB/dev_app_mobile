package com.example.annexe1


import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.time.LocalDate


class AjoutActivity : AppCompatActivity() {
    lateinit var ajout: Button
    lateinit var date_btn: Button
    lateinit var champ: EditText
    lateinit var date_txt: TextView
    val ec = Ecouteur()
    var dateChoisi: LocalDate = LocalDate.now()

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
        date_txt = findViewById(R.id.text_date)
        ajout = findViewById(R.id.btn_ajouter2)
        date_btn = findViewById(R.id.btn_echeance)
        ajout.setOnClickListener(ec)
        date_btn.setOnClickListener(ec)
    }

    inner class Ecouteur : View.OnClickListener, OnDateSetListener{
        override fun onClick(v: View) {
            if(v == date_btn){
                System.out.println("allo")
                var dp = DatePickerDialog(this@AjoutActivity)
                dp.setOnDateSetListener(this)
                dp.show()


            }
            else if(v == ajout){
                var toSave: String = champ.text.toString()

                Singleton.getInstance().ajouter_memo(Memo(toSave, dateChoisi))

                finish();
            }
        }

        override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
            dateChoisi = LocalDate.of(year,month+1,dayOfMonth)
            date_txt.setText(dateChoisi.toString())
        }
    }
}