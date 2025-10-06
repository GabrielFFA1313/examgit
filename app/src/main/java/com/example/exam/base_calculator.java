package com.example.exam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class base_calculator extends AppCompatActivity {

    private EditText editText;
    private TextView decimalText, binaryText, octalText, hexText;
    private Button submitButton, clearButton;
    private Button num1Button, num2Button, num3Button, num4Button;
    private Button num5Button, num6Button, num7Button, num8Button, num9Button, zeroButton;
    private Button btnA, btnB, btnC, btnD, btnE, btnF;

    private Spinner switchUI, switchbase;
    private String currentBase = "Decimal";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_calculator);

        // Initialize UI components
        switchUI = findViewById(R.id.switchUI);
        switchbase = findViewById(R.id.switchbase);

        editText = findViewById(R.id.editText2);
        decimalText = findViewById(R.id.decimalText);
        binaryText = findViewById(R.id.binarytext);
        octalText = findViewById(R.id.octaltext);
        hexText = findViewById(R.id.hextext);

        clearButton = findViewById(R.id.clear_text);
        submitButton = findViewById(R.id.submit);

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

        btnA = findViewById(R.id.btnA);
        btnB = findViewById(R.id.btnB);
        btnC = findViewById(R.id.btnC);
        btnD = findViewById(R.id.btnD);
        btnE = findViewById(R.id.btnE);
        btnF = findViewById(R.id.btnF);

        // Setup Spinners
        spinner_switch_UI();
        switch_to_other();
        spinner_switch_base();

        // Setup Number Buttons
        setupNumberButtons();

        // Setup Clear Button
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = editText.getText().toString();
                if (currentText.length() > 0) {
                    editText.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        // Setup Submit Button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertNumber();
            }
        });
    }

    public void spinner_switch_UI() {
        String[] spinneroption = {"Base Calculator", "Basic Calculator", "Unit Converter"};
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

                if (selectedItem.equals("Basic Calculator")) {
                    Intent intent = new Intent(base_calculator.this, MainActivity.class);
                    startActivity(intent);
                } else if (selectedItem.equals("Unit Converter")) {
                    Intent intent = new Intent(base_calculator.this, unit_coverter.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void spinner_switch_base() {
        String[] baseOptions = {"Binary", "Decimal", "Octal", "Hexadecimal"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, baseOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switchbase.setAdapter(adapter);
        switchbase.setSelection(1); // Set default to Decimal

        switchbase.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedBase = parent.getItemAtPosition(position).toString();
                currentBase = selectedBase;
                updateButtonAvailability();
                editText.setText("");
                resetOutputs();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void setupNumberButtons() {
        num1Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("1");
            }
        });

        num2Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("2");
            }
        });

        num3Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("3");
            }
        });

        num4Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("4");
            }
        });

        num5Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("5");
            }
        });

        num6Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("6");
            }
        });

        num7Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("7");
            }
        });

        num8Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("8");
            }
        });

        num9Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("9");
            }
        });

        zeroButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("0");
            }
        });

        btnA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("A");
            }
        });

        btnB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("B");
            }
        });

        btnC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("C");
            }
        });

        btnD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("D");
            }
        });

        btnE.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("E");
            }
        });

        btnF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("F");
            }
        });
    }

    private void appendDigit(String digit) {
        String currentText = editText.getText().toString();
        editText.setText(currentText + digit);
    }

    private void updateButtonAvailability() {
        // Enable/disable buttons based on selected base
        switch (currentBase) {
            case "Binary":
                // Only 0 and 1 allowed
                setButtonState(num2Button, false);
                setButtonState(num3Button, false);
                setButtonState(num4Button, false);
                setButtonState(num5Button, false);
                setButtonState(num6Button, false);
                setButtonState(num7Button, false);
                setButtonState(num8Button, false);
                setButtonState(num9Button, false);
                setButtonState(btnA, false);
                setButtonState(btnB, false);
                setButtonState(btnC, false);
                setButtonState(btnD, false);
                setButtonState(btnE, false);
                setButtonState(btnF, false);
                break;
            case "Octal":
                // 0-7 allowed
                setButtonState(num2Button, true);
                setButtonState(num3Button, true);
                setButtonState(num4Button, true);
                setButtonState(num5Button, true);
                setButtonState(num6Button, true);
                setButtonState(num7Button, true);
                setButtonState(num8Button, false);
                setButtonState(num9Button, false);
                setButtonState(btnA, false);
                setButtonState(btnB, false);
                setButtonState(btnC, false);
                setButtonState(btnD, false);
                setButtonState(btnE, false);
                setButtonState(btnF, false);
                break;
            case "Decimal":
                // 0-9 allowed
                setButtonState(num2Button, true);
                setButtonState(num3Button, true);
                setButtonState(num4Button, true);
                setButtonState(num5Button, true);
                setButtonState(num6Button, true);
                setButtonState(num7Button, true);
                setButtonState(num8Button, true);
                setButtonState(num9Button, true);
                setButtonState(btnA, false);
                setButtonState(btnB, false);
                setButtonState(btnC, false);
                setButtonState(btnD, false);
                setButtonState(btnE, false);
                setButtonState(btnF, false);
                break;
            case "Hexadecimal":
                // All digits and A-F allowed
                setButtonState(num2Button, true);
                setButtonState(num3Button, true);
                setButtonState(num4Button, true);
                setButtonState(num5Button, true);
                setButtonState(num6Button, true);
                setButtonState(num7Button, true);
                setButtonState(num8Button, true);
                setButtonState(num9Button, true);
                setButtonState(btnA, true);
                setButtonState(btnB, true);
                setButtonState(btnC, true);
                setButtonState(btnD, true);
                setButtonState(btnE, true);
                setButtonState(btnF, true);
                break;
        }
    }

    private void setButtonState(Button button, boolean enabled) {
        button.setEnabled(enabled);
        if (enabled) {
            button.setBackgroundColor(Color.parseColor("#000000")); // Black for enabled buttons
        } else {
            button.setBackgroundColor(Color.parseColor("#FF0000")); // Red for disabled buttons
        }
    }

    private void convertNumber() {
        String input = editText.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            long decimalValue;

            // Convert input to decimal first
            switch (currentBase) {
                case "Binary":
                    decimalValue = Long.parseLong(input, 2);
                    break;
                case "Decimal":
                    decimalValue = Long.parseLong(input, 10);
                    break;
                case "Octal":
                    decimalValue = Long.parseLong(input, 8);
                    break;
                case "Hexadecimal":
                    decimalValue = Long.parseLong(input, 16);
                    break;
                default:
                    decimalValue = 0;
            }

            // Convert to all bases and display
            decimalText.setText(String.valueOf(decimalValue));
            binaryText.setText(Long.toBinaryString(decimalValue));
            octalText.setText(Long.toOctalString(decimalValue));
            hexText.setText(Long.toHexString(decimalValue).toUpperCase());

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input for " + currentBase + " base", Toast.LENGTH_SHORT).show();
            resetOutputs();
        }
    }

    private void resetOutputs() {
        decimalText.setText("0");
        binaryText.setText("0");
        octalText.setText("0");
        hexText.setText("0");
    }
}