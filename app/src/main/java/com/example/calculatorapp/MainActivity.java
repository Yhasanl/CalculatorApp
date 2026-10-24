package com.example.calculatorapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    TextView tvResult;
    Button btn_mc, btn_mr, btn_ms, btn_m_plus, btn_m_minus;
    Button btn_backspace, btn_ce, btn_c, btn_plus_minus, btn_root;
    Button btn_7, btn_8, btn_9, btn_divide, btn_percent;
    Button btn_4, btn_5, btn_6, btn_multiply, btn_fraction;
    Button btn_1, btn_2, btn_3, btn_minus, btn_equal;
    Button btn_0, btn_dot, btn_plus;

    double firstNumber = 0;
    String currentOperation = "";
    double memory = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        tvResult = findViewById(R.id.tv_result);

        btn_mc = findViewById(R.id.btn_mc);
        btn_mr = findViewById(R.id.btn_mr);
        btn_ms = findViewById(R.id.btn_ms);
        btn_m_plus = findViewById(R.id.btn_m_plus);
        btn_m_minus = findViewById(R.id.btn_m_minus);

        btn_backspace = findViewById(R.id.btn_backspace);
        btn_ce = findViewById(R.id.btn_ce);
        btn_c = findViewById(R.id.btn_c);
        btn_plus_minus = findViewById(R.id.btn_plus_minus);
        btn_root = findViewById(R.id.btn_root);

        btn_7 = findViewById(R.id.btn_7);
        btn_8 = findViewById(R.id.btn_8);
        btn_9 = findViewById(R.id.btn_9);
        btn_divide = findViewById(R.id.btn_divide);
        btn_percent = findViewById(R.id.btn_percent);

        btn_4 = findViewById(R.id.btn_4);
        btn_5 = findViewById(R.id.btn_5);
        btn_6 = findViewById(R.id.btn_6);
        btn_multiply = findViewById(R.id.btn_multiply);
        btn_fraction = findViewById(R.id.btn_fraction);

        btn_1 = findViewById(R.id.btn_1);
        btn_2 = findViewById(R.id.btn_2);
        btn_3 = findViewById(R.id.btn_3);
        btn_minus = findViewById(R.id.btn_minus);
        btn_equal = findViewById(R.id.btn_equal);

        btn_0 = findViewById(R.id.btn_0);
        btn_dot = findViewById(R.id.btn_dot);
        btn_plus = findViewById(R.id.btn_plus);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btn_1.setOnClickListener(v -> appendNumber("1"));
        btn_2.setOnClickListener(v -> appendNumber("2"));
        btn_3.setOnClickListener(v -> appendNumber("3"));
        btn_4.setOnClickListener(v -> appendNumber("4"));
        btn_5.setOnClickListener(v -> appendNumber("5"));
        btn_6.setOnClickListener(v -> appendNumber("6"));
        btn_7.setOnClickListener(v -> appendNumber("7"));
        btn_8.setOnClickListener(v -> appendNumber("8"));
        btn_9.setOnClickListener(v -> appendNumber("9"));
        btn_0.setOnClickListener(v -> appendNumber("0"));

        btn_plus.setOnClickListener(v -> setOperation("+"));
        btn_minus.setOnClickListener(v -> setOperation("-"));
        btn_multiply.setOnClickListener(v -> setOperation("*"));
        btn_divide.setOnClickListener(v -> setOperation("/"));

        btn_equal.setOnClickListener(v -> calculateResult());
        btn_c.setOnClickListener(v -> clearCalculator());

        btn_dot.setOnClickListener(v -> appendDot());
        btn_plus_minus.setOnClickListener(v -> negate());
        btn_percent.setOnClickListener(v -> calculatePercent());
        btn_root.setOnClickListener(v -> calculateRoot());
        btn_fraction.setOnClickListener(v -> calculateFraction());

        btn_ce.setOnClickListener(v -> tvResult.setText("0"));
        btn_backspace.setOnClickListener(v -> backspace());

        btn_mc.setOnClickListener(v -> memory = 0);
        btn_mr.setOnClickListener(v -> formatAndSetResult(memory));
        btn_ms.setOnClickListener(v -> memory = Double.parseDouble(tvResult.getText().toString()));
        btn_m_plus.setOnClickListener(v -> memory += Double.parseDouble(tvResult.getText().toString()));
        btn_m_minus.setOnClickListener(v -> memory -= Double.parseDouble(tvResult.getText().toString()));
    }

    private void appendNumber(String number) {
        if (tvResult.getText().toString().equals("0") || tvResult.getText().toString().equals("Error")) {
            tvResult.setText(number);
        } else {
            tvResult.append(number);
        }
    }

    private void setOperation(String operation) {
        firstNumber = Double.parseDouble(tvResult.getText().toString());
        currentOperation = operation;
        tvResult.setText("0");
    }

    private void calculateResult() {
        if (currentOperation.isEmpty()) return;

        double secondNumber = Double.parseDouble(tvResult.getText().toString());
        double result = 0;

        switch (currentOperation) {
            case "+": result = firstNumber + secondNumber; break;
            case "-": result = firstNumber - secondNumber; break;
            case "*": result = firstNumber * secondNumber; break;
            case "/":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    tvResult.setText("Error");
                    currentOperation = "";
                    return;
                }
                break;
        }

        formatAndSetResult(result);
        currentOperation = "";
    }

    private void clearCalculator() {
        firstNumber = 0;
        currentOperation = "";
        tvResult.setText("0");
    }

    private void appendDot() {
        if (!tvResult.getText().toString().contains(".")) {
            tvResult.append(".");
        }
    }

    private void negate() {
        String text = tvResult.getText().toString();
        if (!text.equals("0") && !text.equals("Error")) {
            if (text.startsWith("-")) {
                tvResult.setText(text.substring(1));
            } else {
                tvResult.setText("-" + text);
            }
        }
    }

    private void formatAndSetResult(double val) {
        if (val == (long) val) {
            tvResult.setText(String.format("%d", (long) val));
        } else {
            tvResult.setText(String.valueOf(val));
        }
    }

    private void calculatePercent() {
        double val = Double.parseDouble(tvResult.getText().toString());
        formatAndSetResult(val / 100);
    }

    private void calculateRoot() {
        double val = Double.parseDouble(tvResult.getText().toString());
        if (val >= 0) formatAndSetResult(Math.sqrt(val));
        else tvResult.setText("Error");
    }

    private void calculateFraction() {
        double val = Double.parseDouble(tvResult.getText().toString());
        if (val != 0) formatAndSetResult(1 / val);
        else tvResult.setText("Error");
    }

    private void backspace() {
        String text = tvResult.getText().toString();
        if (text.length() > 1 && !text.equals("Error") && !text.equals("-")) {
            tvResult.setText(text.substring(0, text.length() - 1));
        } else {
            tvResult.setText("0");
        }
    }
}