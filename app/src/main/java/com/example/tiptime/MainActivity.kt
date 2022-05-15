package com.example.tiptime

import android.content.Context
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import com.example.tiptime.databinding.ActivityMainBinding.inflate
import java.text.NumberFormat
import kotlin.math.ceil

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnCal.setOnClickListener {
            calculateTip()
        }
        binding.costOfServiceEditText.setOnKeyListener {
            view, keyCode, _ -> handleKeyEvent(view, keyCode)
        }
    }

    private fun calculateTip(){
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()
        val selectedId = binding.ques.checkedRadioButtonId

        if(cost == null){
            binding.tipAmt.text = ""
            return
        }

        val tipPercentage = when(selectedId){
            R.id.twenty_percent -> 0.20
            R.id.eighteen_percent -> 0.18
            R.id.fifteen_percent -> 0.15
            else -> 0.00
        }

        var tip = tipPercentage * cost

        val roundup = binding.tipOptRound.isChecked
        if(roundup){
            tip = ceil(tip)
        }

        val finalTip = NumberFormat.getCurrencyInstance().format(tip)

        binding.tipAmt.text = getString(R.string.tip_amount, finalTip)
    }

    private fun handleKeyEvent(view: View, keyCode: Int): Boolean {
        if (keyCode == KeyEvent.KEYCODE_ENTER) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
            return true
        }
        return false
    }
}
