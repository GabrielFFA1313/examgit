package com.example.exam;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class unit_coverter extends AppCompatActivity {

    private Spinner switchUI, switchCategory, switchUnit1, switchUnit2;
    private EditText editText;
    private TextView unitText;
    private Button switchButton, backspaceButton, clearButton, submitButton;
    private Button num0, num1, num2, num3, num4, num5, num6, num7, num8, num9, dotButton;

    // Conversion rates for Money
//    These conversion are as of Oct 6 3:05 pm
//    these conversion are from https://wise.com/gb/currency-converter
//    The website is live so teh exact amount may differ
    private static final double PHP_TO_USD = 0.01717;
    private static final double PHP_TO_EUR = 0.01468 ;
    private static final double USD_TO_EUR = 0.8552;
    private static final double USD_TO_PHP = 58.24;
    private static final double EUR_TO_PHP = 68.12;
    private static final double EUR_TO_USD = 1.170;

    private String currentCategory = "Money";
    private String currentUnit1 = "PHP";
    private String currentUnit2 = "USD";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_unit_coverter);
        super.onCreate(savedInstanceState);

        switchUI = findViewById(R.id.switchUI);
        switchCategory = findViewById(R.id.switchcategory);
        switchUnit1 = findViewById(R.id.switchunit1);
        switchUnit2 = findViewById(R.id.switchunit2);

        editText = findViewById(R.id.editText2);
        unitText = findViewById(R.id.Unittext);

        switchButton = findViewById(R.id.switchbutton);
        backspaceButton = findViewById(R.id.backspace);
        clearButton = findViewById(R.id.clear_text);
        submitButton = findViewById(R.id.submit);

        num0 = findViewById(R.id.zero);
        num1 = findViewById(R.id.num1);
        num2 = findViewById(R.id.num2);
        num3 = findViewById(R.id.num3);
        num4 = findViewById(R.id.num4);
        num5 = findViewById(R.id.num5);
        num6 = findViewById(R.id.num6);
        num7 = findViewById(R.id.num7);
        num8 = findViewById(R.id.num8);
        num9 = findViewById(R.id.num9);
        dotButton = findViewById(R.id.dot);
//       buttons
        setupNumberButtons();
        setupFunctionButtons();

        spinner_switch_UI();
        switch_to_other();
        spinner_switch_category();
        updateUnitSpinners("Money");
    }



    public void spinner_switch_UI() {
        String[] spinneroption = {"Unit Converter", "Basic Calculator", "Base Calculator"};
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
                    Intent intent = new Intent(unit_coverter.this, MainActivity.class);
                    startActivity(intent);
                } else if (selectedItem.equals("Base Calculator")) {
                    Intent intent = new Intent(unit_coverter.this, base_calculator.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    public void spinner_switch_category() {
        String[] categoryOptions = {"Money", "Temperature", "Speed"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, categoryOptions);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        switchCategory.setAdapter(adapter);

        switchCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedCategory = parent.getItemAtPosition(position).toString();
                currentCategory = selectedCategory;
                updateUnitSpinners(selectedCategory);
                editText.setText("");
                unitText.setText("");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateUnitSpinners(String category) {
        String[] units = {};

        if (category.equals("Money")) {
            units = new String[]{"PHP", "USD", "EUR"};
            currentUnit1 = "PHP";
            currentUnit2 = "USD";
        } else if (category.equals("Temperature")) {
            units = new String[]{"Celsius", "Fahrenheit", "Kelvin"};
            currentUnit1 = "Celsius";
            currentUnit2 = "Fahrenheit";
        } else if (category.equals("Speed")) {
            units = new String[]{"Kilometer", "Miles"};  // Changed: Removed "Meter"
            currentUnit1 = "Kilometer";
            currentUnit2 = "Miles";  // Changed: Default to Miles instead of Meter
        }

        ArrayAdapter<String> unitAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, units);
        unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        switchUnit1.setAdapter(unitAdapter);
        switchUnit2.setAdapter(unitAdapter);

        if (category.equals("Money")) {
            switchUnit1.setSelection(0); // PHP
            switchUnit2.setSelection(1); // USD
        } else if (category.equals("Temperature")) {
            switchUnit1.setSelection(0); // Celsius
            switchUnit2.setSelection(1); // Fahrenheit
        } else if (category.equals("Speed")) {
            switchUnit1.setSelection(0); // Kilometer
            switchUnit2.setSelection(1); // Miles
        }

        setupUnitSpinnerListeners();
        updateUnitLabel();
    }

    private void setupUnitSpinnerListeners() {
        switchUnit1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentUnit1 = parent.getItemAtPosition(position).toString();
                updateUnitLabel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        switchUnit2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentUnit2 = parent.getItemAtPosition(position).toString();
                updateUnitLabel();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });
    }

    private void updateUnitLabel() {
        TextView unitConversionLabel = findViewById(R.id.unitconversion);
        unitConversionLabel.setText(currentUnit1 + " to " + currentUnit2 + ": ");
    }

    private void setupNumberButtons() {
        num1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("1");
            }
        });

        num2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("2");
            }
        });

        num3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("3");
            }
        });

        num4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("4");
            }
        });

        num5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("5");
            }
        });

        num6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("6");
            }
        });

        num7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("7");
            }
        });

        num8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("8");
            }
        });

        num9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("9");
            }
        });

        num0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                appendDigit("0");
            }
        });

    }

    private void appendDigit(String digit) {
        String currentText = editText.getText().toString();
        editText.setText(currentText + digit);
    }

    private void setupFunctionButtons() {
        switchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int unit1Position = switchUnit1.getSelectedItemPosition();
                int unit2Position = switchUnit2.getSelectedItemPosition();

                switchUnit1.setSelection(unit2Position);
                switchUnit2.setSelection(unit1Position);

                String temp = currentUnit1;
                currentUnit1 = currentUnit2;
                currentUnit2 = temp;

                updateUnitLabel();

                if (!editText.getText().toString().isEmpty()) {
                    performConversion();
                }
            }
        });

        backspaceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = editText.getText().toString();
                if (currentText.length() > 0) {
                    editText.setText(currentText.substring(0, currentText.length() - 1));
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
                unitText.setText("");
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performConversion();
            }
        });
    }

    private void performConversion() {
        String input = editText.getText().toString().trim();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            double inputValue = Double.parseDouble(input);
            double result = 0;

            if (currentCategory.equals("Money")) {
                result = convertMoney(inputValue, currentUnit1, currentUnit2);
            } else if (currentCategory.equals("Temperature")) {
                result = convertTemperature(inputValue, currentUnit1, currentUnit2);
            } else if (currentCategory.equals("Speed")) {
                result = convertSpeed(inputValue, currentUnit1, currentUnit2);
            }

            DecimalFormat df = new DecimalFormat("#,##0.00");
            unitText.setText(df.format(result) + " " + currentUnit2);

        } catch (NumberFormatException e) {
            Toast.makeText(this, "Invalid input", Toast.LENGTH_SHORT).show();
        }
    }

    private double convertMoney(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            return value;
        }

        if (fromUnit.equals("PHP") && toUnit.equals("USD")) {
            return value * PHP_TO_USD;
        } else if (fromUnit.equals("PHP") && toUnit.equals("EUR")) {
            return value * PHP_TO_EUR;
        } else if (fromUnit.equals("USD") && toUnit.equals("PHP")) {
            return value * USD_TO_PHP;
        } else if (fromUnit.equals("USD") && toUnit.equals("EUR")) {
            return value * USD_TO_EUR;
        } else if (fromUnit.equals("EUR") && toUnit.equals("PHP")) {
            return value * EUR_TO_PHP;
        } else if (fromUnit.equals("EUR") && toUnit.equals("USD")) {
            return value * EUR_TO_USD;
        }

        return value;
    }

    private double convertTemperature(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            return value;
        }

        // Convert to Celsius first
        double celsius = 0;
        if (fromUnit.equals("Celsius")) {
            celsius = value;
        } else if (fromUnit.equals("Fahrenheit")) {
            celsius = (value - 32) * 5 / 9;
        } else if (fromUnit.equals("Kelvin")) {
            celsius = value - 273.15;
        }

        // Convert from Celsius to target unit
        if (toUnit.equals("Celsius")) {
            return celsius;
        } else if (toUnit.equals("Fahrenheit")) {
            return (celsius * 9 / 5) + 32;
        } else if (toUnit.equals("Kelvin")) {
            return celsius + 273.15;
        }

        return value;
    }

    private double convertSpeed(double value, String fromUnit, String toUnit) {
        if (fromUnit.equals(toUnit)) {
            return value;
        }

        // Direct conversion between Kilometer and Miles
        if (fromUnit.equals("Kilometer") && toUnit.equals("Miles")) {
            return value * 0.621371;  // km to miles
        } else if (fromUnit.equals("Miles") && toUnit.equals("Kilometer")) {
            return value * 1.60934;  // miles to km
        }

        return value;
    }
}