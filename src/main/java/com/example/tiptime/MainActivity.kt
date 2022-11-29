package com.example.tiptime

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.costOfServiceEditText.setOnKeyListener { view, keyCode, _ -> handleKeyEvent(view, keyCode)}
        binding.calculateButton.setOnClickListener{calculateTip()}
    }

    private fun calculateTip()
    {
        val stringInTextField=binding.costOfServiceEditText.text.toString()//Hold the entered text
        val cost= stringInTextField.toDoubleOrNull()//convert it to double
        if(cost ==null|| cost ==0.0)//if user entered nothing/null
        {
            val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(0.0)
            binding.tipResult.text=getString(R.string.tip_amount,formattedTip)
            //or
            //binding.tipResult.text=""
            return
        }
        val tipPercentage=when(binding.tipOptions.checkedRadioButtonId){//hold which option is selected//hold its value a/c to the selected option
            R.id.option_twenty->0.20
            R.id.option_eighteen->0.18
            else->0.15
        }
        var tip = tipPercentage*cost//calculate tip
//        val roundup = binding.roundUpSwitch.isChecked//check if roundup switch is on or off
//        if(roundup){ OR DO IT THIS WAY
        if(binding.roundUpSwitch.isChecked){
            tip=kotlin.math.ceil(tip)//roundup the cost
        }
        NumberFormat.getCurrencyInstance(Locale("en", "in"))
        val formattedTip = NumberFormat.getCurrencyInstance(Locale("en", "in")).format(tip)
        binding.tipResult.text=getString(R.string.tip_amount,formattedTip)

    }
    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            // Hide the keyboard
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)

            return true
        }
        return false
    }
}