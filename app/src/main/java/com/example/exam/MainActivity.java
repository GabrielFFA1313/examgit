package com.example.exam;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView resultText;
    private Button addButton, subtractButton, multiplyButton, divideButton, equalButton, clearButton, backspaceButton;
    private Button num1Button, num2Button, num3Button, num4Button;
    private Button num5Button, num6Button, num7Button, num8Button, num9Button, zeroButton, dotButton;
    private Button leftParenButton, rightParenButton;
    private String currentExpression = "";
    Spinner switchUI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchUI = findViewById(R.id.switchUI);

        editText = findViewById(R.id.editText2);
        resultText = findViewById(R.id.resultText);
        clearButton = findViewById(R.id.clear_text);
        backspaceButton = findViewById(R.id.backspace);

        addButton = findViewById(R.id.add);
        subtractButton = findViewById(R.id.sub);
        multiplyButton = findViewById(R.id.mul);
        divideButton = findViewById(R.id.div);
        equalButton = findViewById(R.id.submit);

        leftParenButton = findViewById(R.id.left_paren);
        rightParenButton = findViewById(R.id.right_paren);

        num1Button = findViewById(R.id.num1);
        num2Button = findViewById(R.id.num2);
        num3Button = findViewById(R.id.num3);
        num4Button = findViewById(R.id.num4);
        num5Button = findViewById(R.id.num5);
        num6Button = findViewById(R.id.num6);
        num7Button = findViewById(R.id.num7);
        num8Button = findViewById(R.id.num8);
        num9Button = findViewById(R.id.num9);
        zeroButton = findViewById(R.id.zero);
        dotButton = findViewById(R.id.dot);

        // Setup spinner
        spinner_switch();
        switch_to_other();

        // Parentheses buttons
        leftParenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExpression += "(";
                editText.setText(currentExpression);
            }
        });

        rightParenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentExpression += ")";
                editText.setText(currentExpression);
            }
        });

        // Backspace button - deletes one character
        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentExpression.length() > 0) {
                    currentExpression = currentExpression.substring(0, currentExpression.length() - 1);
                    editText.setText(currentExpression);
                    if (currentExpression.isEmpty()) {
                        resultText.setText("0");
                    }
                }
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendOperator(" + ");
            }
        });

        subtractButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendOperator(" - ");
            }
        });

        multiplyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendOperator(" * ");
            }
        });

        divideButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendOperator(" / ");
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                resultText.setText("0");
                currentExpression = "";
            }
        });

        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String expression = editText.getText().toString();
                    if (!expression.isEmpty()) {
                        double result = evaluateExpression(expression);
                        if (Double.isInfinite(result)) {
                            resultText.setText("Error: Division by zero");
                        } else {
                            resultText.setText(String.valueOf(result));
                            currentExpression = String.valueOf(result);
                            editText.setText(currentExpression);
                        }
                    }
                } catch (Exception e) {
                    resultText.setText("Error");
                }
            }
        });

        num1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("1");
            }
        });

        num2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("2");
            }
        });

        num3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("3");
            }
        });

        num4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("4");
            }
        });

        num5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("5");
            }
        });

        num6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("6");
            }
        });

        num7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("7");
            }
        });

        num8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("8");
            }
        });

        num9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("9");
            }
        });

        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendNumber("0");
            }
        });

        dotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String current = editText.getText().toString();
                // Check if the last number already has a dot
                String[] parts = current.split(" ");
                if (parts.length > 0) {
                    String lastPart = parts[parts.length - 1];
                    if (!lastPart.contains(".")) {
                        appendNumber(".");
                    }
                }
            }
        });
    }

    private void appendNumber(String number) {
        currentExpression += number;
        editText.setText(currentExpression);
    }

    private void appendOperator(String operator) {
        if (!currentExpression.isEmpty() && !currentExpression.trim().endsWith("+")
                && !currentExpression.trim().endsWith("-")
                && !currentExpression.trim().endsWith("*")
                && !currentExpression.trim().endsWith("/")) {
            currentExpression += operator;
            editText.setText(currentExpression);
        }
    }

    // PEMDAS Implementation using Two Stacks (Shunting Yard Algorithm)
    private double evaluateExpression(String expression) {
        expression = expression.replaceAll(" ", "");

        Stack<Double> numbers = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);

            // If current character is a number or decimal point
            if (Character.isDigit(c) || c == '.') {
                StringBuilder sb = new StringBuilder();
                // Continue reading the number (including decimal points)
                while (i < expression.length() &&
                        (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                    sb.append(expression.charAt(i++));
                }
                i--; // Step back one position
                numbers.push(Double.parseDouble(sb.toString()));
            }
            // If current character is an opening parenthesis
            else if (c == '(') {
                operators.push(c);
            }
            // If current character is a closing parenthesis
            else if (c == ')') {
                while (!operators.isEmpty() && operators.peek() != '(') {
                    numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                }
                if (!operators.isEmpty()) {
                    operators.pop(); // Remove the '('
                }
            }
            // If current character is an operator
            else if (isOperator(c)) {
                // Handle negative numbers at the start or after an operator
                if (c == '-' && (i == 0 || isOperator(expression.charAt(i - 1)) || expression.charAt(i - 1) == '(')) {
                    StringBuilder sb = new StringBuilder();
                    sb.append('-');
                    i++;
                    while (i < expression.length() &&
                            (Character.isDigit(expression.charAt(i)) || expression.charAt(i) == '.')) {
                        sb.append(expression.charAt(i++));
                    }
                    i--;
                    numbers.push(Double.parseDouble(sb.toString()));
                } else {
                    // Process operators according to precedence
                    while (!operators.isEmpty() && hasPrecedence(c, operators.peek())) {
                        numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
                    }
                    operators.push(c);
                }
            }
        }

        // Process remaining operators
        while (!operators.isEmpty()) {
            numbers.push(applyOperation(operators.pop(), numbers.pop(), numbers.pop()));
        }

        return numbers.pop();
    }

    private boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    // Returns true if op2 has higher or equal precedence than op1
    private boolean hasPrecedence(char op1, char op2) {
        if (op2 == '(' || op2 == ')') {
            return false;
        }
        if ((op1 == '*' || op1 == '/') && (op2 == '+' || op2 == '-')) {
            return false;
        }
        return true;
    }

    // Apply an operation to two numbers
    private double applyOperation(char operator, double b, double a) {
        switch (operator) {
            case '+':
                return a + b;
            case '-':
                return a - b;
            case '*':
                return a * b;
            case '/':
                if (b == 0) {
                    return Double.POSITIVE_INFINITY; // Division by zero
                }
                return a / b;
        }
        return 0;
    }

    public void spinner_switch() {
        String[] spinneroption = {"Basic Calculator", "Base_Number", "Unit_converter"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, spinneroption);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        switchUI.setAdapter(adapter);
    }

    private void switch_to_other() {
        switchUI.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();

                if (selectedItem.equals("Base_Number")) {
                    Intent intent = new Intent(MainActivity.this, base_calculator.class);
                    startActivity(intent);
                }
                else if (selectedItem.equals("Unit_converter")){
                    Intent intent = new Intent(MainActivity.this, unit_coverter.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do nothing when nothing is selected
            }
        });
    }
}