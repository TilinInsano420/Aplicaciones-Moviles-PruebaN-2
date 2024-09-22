package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Agregar_Aparato extends AppCompatActivity {

    private EditText inputNombreAparato, inputCantidadUsada;
    private RadioGroup radioGroup;
    private RadioButton radioButtonSeleccionado;
    private Button botonAgregarAparato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_aparato);

        inputNombreAparato = findViewById(R.id.InputNomOBJ);
        inputCantidadUsada = findViewById(R.id.InputCantidadUtiliza); // :) kWh del aparato
        radioGroup = findViewById(R.id.radioGroup);
        botonAgregarAparato = findViewById(R.id.button13);

        botonAgregarAparato.setOnClickListener(v -> agregarAparato());
    }

    private void agregarAparato() {
        if (validarCampos()) {
            int radioId = radioGroup.getCheckedRadioButtonId();
            radioButtonSeleccionado = findViewById(radioId);
            String tipoEnergia = radioButtonSeleccionado.getText().toString();

            SharedPreferences preferences = getSharedPreferences("aparatoPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("nombreAparato", inputNombreAparato.getText().toString());
            editor.putString("cantidadUtiliza", inputCantidadUsada.getText().toString());
            editor.putString("tipoEnergia", tipoEnergia);

            editor.apply();

            Toast.makeText(this, "Se agrego el aparato correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(Agregar_Aparato.this, MenuPrincipal.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validarCampos() {
        if (inputNombreAparato.getText().toString().isEmpty() ||
                inputCantidadUsada.getText().toString().isEmpty() ||
                radioGroup.getCheckedRadioButtonId() == -1) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}
