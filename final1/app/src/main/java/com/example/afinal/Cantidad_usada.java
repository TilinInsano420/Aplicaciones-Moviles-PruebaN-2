package com.example.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class Cantidad_usada extends AppCompatActivity {

    private TextView nombreTextView, cantidadTextView, tipoEnergiaTextView;
    private RadioGroup radioGroup;
    private EditText editTextCantidad;
    private Button botonAgregarCantidad;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cantidad_usada);

        nombreTextView = findViewById(R.id.textViewNombreAparato);
        cantidadTextView = findViewById(R.id.textViewCantidadUtiliza);
        tipoEnergiaTextView = findViewById(R.id.textViewTipoEnergia);
        radioGroup = findViewById(R.id.radioGroupTipoUso);
        editTextCantidad = findViewById(R.id.editTextCantidad);
        botonAgregarCantidad = findViewById(R.id.buttonAgregarCantidad);

        preferences = getSharedPreferences("aparatoPrefs", Context.MODE_PRIVATE);

        cargarDatosAparato();

        botonAgregarCantidad.setOnClickListener(v -> agregarCantidad());
    }

    private void cargarDatosAparato() {
        String nombreAparato = preferences.getString("nombreAparato", null);
        String tipoEnergia = preferences.getString("tipoEnergia", null);
        String cantidadUtiliza = preferences.getString("cantidadUtiliza", null);
        String cantidadUsada = preferences.getString("cantidadUsada", null);
        int tipoUso = preferences.getInt("tipoUso", -1);

        ConstraintLayout constraintLayout = findViewById(R.id.containerLayout);

        if (nombreAparato != null) {
            constraintLayout.setVisibility(View.VISIBLE);
            nombreTextView.setText("Nombre del Aparato: " + nombreAparato);
            tipoEnergiaTextView.setText("Tipo de Energía: " + tipoEnergia);

            if (cantidadUtiliza != null) {
                cantidadTextView.setText("Cantidad que utiliza: " + cantidadUtiliza);
            } else {
                //sufrimiento cuando aparecia esto :(
                cantidadTextView.setText("Cantidad que utiliza: No encontrada");
            }

            if (cantidadUsada != null) {
                editTextCantidad.setText(cantidadUsada);
            }

            if (tipoUso != -1) {
                RadioButton selectedRadioButton = findViewById(tipoUso);
                selectedRadioButton.setChecked(true);
            }
        } else {
            constraintLayout.setVisibility(View.GONE);
            Toast.makeText(this, "No hay aparatos agregados", Toast.LENGTH_SHORT).show();
        }
    }

    private void agregarCantidad() {
        String cantidadTexto = editTextCantidad.getText().toString();

        if (cantidadTexto.isEmpty() || Integer.parseInt(cantidadTexto) > 99) {
            Toast.makeText(this, "Ingrese una cantidad válida (max 99)", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("cantidadUsada", cantidadTexto);

        int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonId != -1) {
            editor.putInt("tipoUso", selectedRadioButtonId);
        }

        editor.apply();

        Toast.makeText(this, "Cantidad de uso agregada correctamente", Toast.LENGTH_SHORT).show();
        finish();
    }
}
