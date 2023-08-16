package com.example.androidmaster.firstapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.androidmaster.R

class FirstAppActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_app)
        val btnStart=findViewById<AppCompatButton>(R.id.btnStart)
        val etName=findViewById<AppCompatEditText>(R.id.etName)
        btnStart.setOnClickListener(){

            val textLine= etName.text.toString()
            if (textLine.isNotEmpty()) {
                Log.i("Tecla", "Botón pulsado! Se escrito: ${textLine}")
                val intent= Intent(this, ResultActivity::class.java)
                intent.putExtra("EXTRA_KEY", textLine)
                startActivity(intent)
            }
        }
    }
}