package com.example.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat
import java.util.Stack

class SecondActivity : AppCompatActivity(){
    private lateinit var display: TextView
    private var input = ""
    private var operator = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        supportActionBar?.hide()

        val back: ImageButton = findViewById(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        display = findViewById(R.id.Display)
        val tableLayout = findViewById<TableLayout>(R.id.tableLayout)
        setupButtonListeners(tableLayout)
    }

    private fun setupButtonListeners(tableLayout: TableLayout) {
        for (i in 0 until tableLayout.childCount) {
            val row = tableLayout.getChildAt(i) as? TableRow
            row?.let { r ->
                for (j in 0 until r.childCount) {
                    val button = r.getChildAt(j) as? Button
                    button?.setOnClickListener { handleButtonClick(it) }
                }
            }
        }
    }

    private fun handleButtonClick(view: android.view.View) {
        val button = view as? Button ?: return
        val text = button.text.toString()
        when (text) {
            "C" -> reset()
            "=" -> calculateResult()
            in "+-x/" -> setOperator(text)
            else -> appendInput(text)
        }
    }

    private fun appendInput(text: String) {
        if (text == ",") {

            if (operator.isEmpty() && !input.endsWith(",")) {
                input += text
                display.text = input
            }
        } else {
            if (operator.isEmpty()) {
                input += text
            } else {
                input += "$operator$text"
                operator = ""
            }
            display.text = input
        }
    }

    private fun setOperator(op: String) {
        if (input.isNotEmpty() && !input.endsWith(" ")) {
            operator = op
            display.text = "$input $operator "
        }
    }

    private fun calculateResult() {
        try {
            val cleanedInput = input.replace(',', '.').replace(" ", "")
            val result = evaluateExpression(cleanedInput)
            display.text = formatResult(result)
            input = result.toString()
        } catch (e: Exception) {
            display.text = "Error"
        }
    }

    private fun reset() {
        input = ""
        operator = ""
        display.text = "0"
    }

    private fun evaluateExpression(expression: String): Double {
        val tokens = expression.split("(?=[-+x/])|(?<=[-+x/])".toRegex())
        val values = Stack<Double>()
        val ops = Stack<Char>()

        for (token in tokens) {
            when {
                token.matches(Regex("\\d+(\\.\\d+)?")) -> values.push(token.toDouble())
                token[0] in "+-x/" -> {
                    while (ops.isNotEmpty() && precedence(token[0]) <= precedence(ops.peek())) {
                        values.push(applyOp(ops.pop(), values.pop(), values.pop()))
                    }
                    ops.push(token[0])
                }
            }
        }

        while (ops.isNotEmpty()) {
            values.push(applyOp(ops.pop(), values.pop(), values.pop()))
        }

        return values.pop()
    }

    private fun precedence(op: Char): Int {
        return when (op) {
            '+', '-' -> 1
            'x', '/' -> 2
            else -> 0
        }
    }

    private fun applyOp(op: Char, b: Double, a: Double): Double {
        return when (op) {
            '+' -> a + b
            '-' -> a - b
            'x' -> a * b
            '/' -> a / b
            else -> 0.0
        }
    }

    private fun formatResult(result: Double): String {
        val formatter = DecimalFormat("#,##0.###")
        return formatter.format(result)
    }

    private fun formatForDisplay(input: String): String {
        return input
    }

}