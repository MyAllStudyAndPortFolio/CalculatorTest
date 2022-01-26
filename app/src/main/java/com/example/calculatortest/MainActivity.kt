package com.example.calculatortest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun buttonClicked(v : View){
        when(v.id){
            R.id.button0 ->
            R.id.button1 ->
            R.id.button2 ->
            R.id.button3 ->
            R.id.button4 ->
            R.id.button5 ->
            R.id.button6 ->
            R.id.button7 ->
            R.id.button8 ->
            R.id.button9 ->
        }
    }
    fun resultButtonClicked(v : View){

    }
    fun historyButtonClicked(v : View){

    }
    fun clearButtonClicked(v : View){

    }
}