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
import androidx.core.view.isVisible
import androidx.room.Room
import com.example.calculatortest.model.History
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    lateinit var database: AppDatabase
    private val expressionTextView: TextView by lazy {
        findViewById<TextView>(R.id.expressionTextView)
    }

    private val resultTextView: TextView by lazy {
        findViewById<TextView>(R.id.resultTextView)
    }

    private val historyLayout: View by lazy {
        findViewById<View>(R.id.historyLayout)
    }

    private val historyLinearLayout: View by lazy{
        findViewById<View>(R.id.historyLinearLayout)
    }



    private var isOperator = false
    private var hasOperator = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "historyDataBase",
        ).build()
    }

    fun buttonClicked(v: View) {
        when (v.id) {
            R.id.button0 -> numberButtonClicked("0")
            R.id.button1 -> numberButtonClicked("1")
            R.id.button2 -> numberButtonClicked("2")
            R.id.button3 -> numberButtonClicked("3")
            R.id.button4 -> numberButtonClicked("4")
            R.id.button5 -> numberButtonClicked("5")
            R.id.button6 -> numberButtonClicked("6")
            R.id.button7 -> numberButtonClicked("7")
            R.id.button8 -> numberButtonClicked("8")
            R.id.button9 -> numberButtonClicked("9")
            R.id.buttonPlus -> operatorButtonClicked("+")
            R.id.buttonMinus -> operatorButtonClicked("-")
            R.id.buttonMulti -> operatorButtonClicked("x")
            R.id.buttonDivider -> operatorButtonClicked("/")
            R.id.buttonModulo -> operatorButtonClicked("%")
        }
    }

    private fun numberButtonClicked(number: String){

        if(isOperator){
            expressionTextView.append(" ")
        }

        isOperator = false

        val expressionText = expressionTextView.text.split(" ")

        if (expressionText.isNotEmpty() && expressionText.last().length >= 15){
            Toast.makeText(this,"15자리까지 쓰실 수 있습니다. ", Toast.LENGTH_SHORT).show()
            return
        }   else if ( expressionText.last().isEmpty() && number == "0"){
            Toast.makeText(this,"0 앞에 큰 숫자는 오실수 없습니다 ", Toast.LENGTH_SHORT).show()
            return
        }

        expressionTextView.append(number)
        resultTextView.text = calculateExpression()

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
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.green)),
            expressionTextView.text.length-1,
            expressionTextView.text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        expressionTextView.text = ssb

        isOperator = true
        hasOperator = true


    }

    fun resultButtonClicked(v : View){

        val expressionTexts = expressionTextView.text.split(" ")

        if(expressionTextView.text.isEmpty() || expressionTexts.size ==1 ){
            return
        }

        if(expressionTexts.size != 3 && hasOperator){

            Toast.makeText(this,"아직 완성되지 않은 수식 입니다",Toast.LENGTH_SHORT).show()
            return
        }
        if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            Toast.makeText(this,"오류가 발생하였습니다",Toast.LENGTH_SHORT).show()
            return
        }

        val expressionText = expressionTextView.text.toString()
        val resultText = calculateExpression()
        // 먼저 저장 하고 
        Thread(Runnable{
            database.historyDao().insertAll(History(null,expressionText,resultText))
        }).start()
        // 원위치 시키기
        resultTextView.text = ""
        expressionTextView.text = resultText
        isOperator = false
        hasOperator = false



    }

    private fun calculateExpression(): String {

        val expressionTexts = expressionTextView.text.split(" ")

        if (hasOperator.not() || expressionTexts.size != 3){
            return ""
        }

        else if (expressionTexts[0].isNumber().not() || expressionTexts[2].isNumber().not()){
            return ""
        }

        val exp1 = expressionTexts[0].toBigInteger()
        val exp2 = expressionTexts[2].toBigInteger()
        val op = expressionTexts[1]

        return when(op) {
            "+" -> (exp1 + exp2).toString()
            "-" -> (exp1 - exp2).toString()
            "x" -> (exp1 * exp2).toString()
            "/" -> (exp1 / exp2).toString()
            "%" -> (exp1 % exp2).toString()
            else -> ""
        }
    }

    fun historyButtonClicked(v : View){
        historyLayout.isVisible=true

        //todo 디비에서 모든 기록 가져오기, 뷰에 모든 기록 할당
    }
    fun historyClearButtonClicked(v : View){
        //TODO 모든 기록 삭제, 뷰에서 모든 기록 삭제
    }

    fun closeHistoryButtonClicked(v: View){
        historyLayout.isVisible=false
    }

    fun clearButtonClicked(v : View){
        expressionTextView.text=""
        resultTextView.text = ""
        isOperator = false
        hasOperator = false
    }

    fun String.isNumber() : Boolean {
        return try {
            this.toBigInteger()
            true
        }catch (e : NumberFormatException){
            false
        }
    }

}