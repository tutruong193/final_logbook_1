package com.example.final_project_logbook;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.MaterialAutoCompleteTextView;

public class MainActivity extends AppCompatActivity {

    private EditText etInputValue;
    private MaterialAutoCompleteTextView spinnerFromUnit, spinnerToUnit;
    private Button btnConvert,btnRefresh;
    private TextView tvResult;

    private String[] units = {"Metre", "Millimetre", "Mile", "Foot"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etInputValue = findViewById(R.id.et_input_value);
        spinnerFromUnit = findViewById(R.id.spinner_from_unit);
        spinnerToUnit = findViewById(R.id.spinner_to_unit);
        btnConvert = findViewById(R.id.btn_convert);
        btnRefresh = findViewById(R.id.btn_refresh);
        tvResult = findViewById(R.id.tv_result);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, units);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFromUnit.setAdapter(adapter);
        spinnerToUnit.setAdapter(adapter);

        btnRefresh.setEnabled(false);

        btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertLength();
            }
        });
        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshFields();
            }
        });
    }

    private void convertLength() {
        String input = etInputValue.getText().toString();

        if (input.isEmpty()) {
            Toast.makeText(this, "Please enter value", Toast.LENGTH_SHORT).show();
            return;
        }
        double value;
        try {
            value = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid number", Toast.LENGTH_SHORT).show();
            return;
        }
        if (value < 0) {
            Toast.makeText(this, "Value must not be less than 0", Toast.LENGTH_SHORT).show();
            return;
        }

        String fromUnit = spinnerFromUnit.getText().toString();
        String toUnit = spinnerToUnit.getText().toString();
        if (fromUnit.isEmpty() || toUnit.isEmpty()) {
            Toast.makeText(this, "Please select unit", Toast.LENGTH_SHORT).show();
            return;
        }

        double result = convertUnits(value, fromUnit, toUnit);
        btnConvert.setEnabled(false);
        btnRefresh.setEnabled(true);
        tvResult.setText(String.format("Result: %.2f %s", result, toUnit));
    }


    private double convertUnits(double value, String fromUnit, String toUnit) {
        double valueInMetres;
        if (fromUnit.equals("Millimetre")) {
            valueInMetres = value / 1000;
        } else if (fromUnit.equals("Mile")) {
            valueInMetres = value * 1609.34;
        } else if (fromUnit.equals("Foot")) {
            valueInMetres = value * 0.3048;
        } else {
            valueInMetres = value;
        }

        if (toUnit.equals("Millimetre")) {
            return valueInMetres * 1000;
        } else if (toUnit.equals("Mile")) {
            return valueInMetres / 1609.34;
        } else if (toUnit.equals("Foot")) {
            return valueInMetres / 0.3048;
        } else {
            return valueInMetres;
        }
    }
    private void refreshFields() {
        etInputValue.setText("");
        spinnerFromUnit.setText("");
        spinnerToUnit.setText("");
        tvResult.setText("Result");

        btnConvert.setEnabled(true);
        btnRefresh.setEnabled(false);
    }
}
