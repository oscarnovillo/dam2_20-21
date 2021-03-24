package com.example.listacompra

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var tv: TextView

    var numero:Int = 1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        numero = savedInstanceState?.getInt("NUMERO") ?: 1

        button = findViewById(R.id.button)
        tv = findViewById(R.id.tv)

        button.setOnClickListener(){
            numero ++
            tv.setText(numero.toString())
        }

    }


    override fun onSaveInstanceState(outState: Bundle) {
        val numero = this.numero
        outState?.run {
            putString("NUMERO",numero.toString())
        }
        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
   tv.setText("restuando")
    }
}