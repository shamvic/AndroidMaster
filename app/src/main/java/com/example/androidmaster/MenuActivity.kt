package com.example.androidmaster

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.androidmaster.firstapp.FirstAppActivity
import com.example.androidmaster.imccalculator.ImcCalculatorActivity
import com.example.androidmaster.settings.SettingsActivity

class MenuActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnSaludApp = findViewById<Button>(R.id.btnSaludApp)
        val btnIMCApp = findViewById<Button>(R.id.btnIMCApp)
        val btnSettings=findViewById<Button>(R.id.btnSettings)

        btnSaludApp.setOnClickListener() {navigateToSaludApp()}
        btnIMCApp.setOnClickListener() {navigateToIMCApp()}
        btnSettings.setOnClickListener { navigeteToSettings() }
    }

    private fun navigeteToSettings() {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
    }

    fun navigateToSaludApp(){
        val intent = Intent(this,FirstAppActivity::class.java)
        startActivity(intent)
    }

    fun navigateToIMCApp(){
        val intent = Intent(this,ImcCalculatorActivity::class.java)
        startActivity(intent)
    }
}