package com.example.calculatortest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.expressionTextView)
    }

    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var isOperator = false
    private var hasOperator = false
    fun buttonClicked(v : View){
        when(v.id){
            R.id.button0 -> numberButtonClicked("0")
            R.id.button1 -> numberButtonClicked("1")
            R.id.button2 -> numberButtonClicked("2")
            R.id.button3 ->numberButtonClicked("3")
            R.id.button4 ->numberButtonClicked("4")
            R.id.button5 ->numberButtonClicked("5")
            R.id.button6 ->numberButtonClicked("6")
            R.id.button7 ->numberButtonClicked("7")
            R.id.button8 ->numberButtonClicked("8")
            R.id.button9 ->numberButtonClicked("9")
            R.id.buttonPlus ->operatorButtonClicked("+")
            R.id.buttonMinus ->operatorButtonClicked("-")
            R.id.buttonDivider ->operatorButtonClicked("/")
            R.id.buttonModulo ->operatorButtonClicked("%")
            R.id.buttonMulti ->operatorButtonClicked("X")
        }
    }

    private fun numberButtonClicked(number: String){



        val expressionText = expressionTextView.text.split(" ")

        if (expressionText.isNotEmpty() && expressionText.last().length >= 15){
            Toast.makeText(this,"15자리까지 쓰실 수 있습니다. ", Toast.LENGTH_SHORT).show()
            return
        }   else if ( expressionText.last().isEmpty() && number == "0"){
            Toast.makeText(this,"0 앞에 큰 숫자는 오실수 없습니다 ", Toast.LENGTH_SHORT).show()
            return
        }

        if(isOperator == true){
            expressionTextView.append(" ")
        }

        isOperator = false


        expressionTextView.append(number)
    }
    private fun operatorButtonClicked(operator : String)
    {
        if (expressionTextView.text.isEmpty()){
            return
        }

        when {
            isOperator->{
                val text = expressionTextView.text.toString()
                expressionTextView.text = text.dropLast(1) +operator
            }
            hasOperator ->{
                Toast.makeText(this,"연산자는 한번만 사용할수 있습니다",Toast.LENGTH_SHORT).show()
                return
            }
            else ->{
                expressionTextView.append(" $operator")
            }
        }

        val ssb = SpannableStringBuilder(expressionTextView.text)
        ssb.setSpan(
            // getcolor depcreated 되어서 contextcompat 하고 this 붙여야 한다
            ForegroundColorSpan(ContextCompat.getColor(this,R.color.green)),
            expressionTextView.text.length-1,
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        expressionTextView.text = ssb

        isOperator = true
        hasOperator = true
    }
    fun resultButtonClicked(v : View){

    }
    fun historyButtonClicked(v : View){

    }
    fun clearButtonClicked(v : View){

    }
}