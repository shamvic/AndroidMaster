package com.example.androidmaster.imccalculator

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.example.androidmaster.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.text.DecimalFormat

class ImcCalculatorActivity : AppCompatActivity() {

    private lateinit var viewMale: CardView
    private lateinit var viewFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var btnSubstractWeight: FloatingActionButton
    private lateinit var btnPlusWeight: FloatingActionButton
    private lateinit var tvWeight: TextView
    private lateinit var btnPlusAge:FloatingActionButton
    private lateinit var btnSubstractAge:FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var btnCalculate:Button

    private var isMaleSelected: Boolean = true
    private var isFemaleSelected: Boolean = false
    private var currentWeight: Int = 75
    private var currentAge: Int = 68
    private var currentHeight:Int=155

    companion object{
        const val IMC_KEY="RESULT_IMC"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_imc_calculator)

        initComponents()
        initListeners()
        initUI()
    }


    private fun initComponents() {
        viewMale = findViewById(R.id.viewMale)
        viewFemale = findViewById(R.id.viewFemale)
        tvHeight = findViewById(R.id.tvHeight)
        rsHeight = findViewById(R.id.rsHeight)
        btnSubstractWeight = findViewById(R.id.btnSubstractWeight)
        btnPlusWeight = findViewById(R.id.btnPlusWeight)
        tvWeight = findViewById(R.id.tvWeight)
        btnSubstractAge = findViewById(R.id.btnSubstractAge)
        btnPlusAge = findViewById(R.id.btnPlusAge)
        tvAge = findViewById(R.id.tvAge)
        btnCalculate=findViewById(R.id.btnCalculate)
    }

    private fun initListeners() {

        viewMale.setOnClickListener {

            changeGender()
            setGenderColor()
        }

        viewFemale.setOnClickListener {

            changeGender()
            setGenderColor()
        }

        rsHeight.addOnChangeListener { _, value, _ ->

            val df = DecimalFormat("#.##")
            currentHeight = df.format(value).toInt()
            tvHeight.text = "$currentHeight cm"
        }

        btnPlusWeight.setOnClickListener {
            currentWeight += 1
            setWeight()
        }
        btnSubstractWeight.setOnClickListener {
            currentWeight -= 1
            setWeight()
        }

        btnPlusAge.setOnClickListener {
            currentAge += 1
            setAge()
        }
        btnSubstractAge.setOnClickListener {
            currentAge -= 1
            setAge()
        }

        btnCalculate.setOnClickListener{
            val result = calculateIMC()
            navigateToResult(result)

        }
    }

    private fun navigateToResult(result:Double) {
        intent= Intent(this, ResultIMCActivity::class.java)
        intent.putExtra(IMC_KEY ,result)
        startActivity(intent)
            }

    private fun calculateIMC():Double {
        val df = DecimalFormat("#.##")
        val hieghtMetro:Double=currentHeight.toDouble()/100
        //val imc=currentHeight/((currentWeight.toDouble()/100)*(currentWeight.toDouble()/100))
        val imc = currentWeight/(hieghtMetro*hieghtMetro)
        val result = df.format(imc).toDouble()
        return result
        //Log.i("androidmaster","El IMC es $result")
    }

    private fun setWeight(){
        tvWeight.text=currentWeight.toString()
    }

    private fun setAge(){
        tvAge.text=currentAge.toString()
    }

    private fun setGenderColor() {

        viewMale.setCardBackgroundColor(getBackgrounColor(isMaleSelected))
        viewFemale.setCardBackgroundColor(getBackgrounColor(isFemaleSelected))

    }

    private fun changeGender() {

        isMaleSelected = !isMaleSelected
        isFemaleSelected = !isFemaleSelected
    }

    private fun getBackgrounColor(isSelectedComponent: Boolean): Int {

        val colorReference = if (isSelectedComponent) {
            R.color.background_component_selected
        } else {
            R.color.background_component
        }
        return ContextCompat.getColor(this, colorReference)

    }

    private fun initUI() {
        setGenderColor()
        setWeight()
        setAge()
    }
}