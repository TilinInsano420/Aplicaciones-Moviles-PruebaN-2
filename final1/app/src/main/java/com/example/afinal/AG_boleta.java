package com.example.afinal;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class AG_boleta extends AppCompatActivity {

    private EditText adminServicioInput, transporteInput, consumoInput, valorKwhInput;
    private TextView tvFechaSeleccionada;
    private Button botAgregarBoleta, botSelectDate;
    private String fechaSeleccionada = "No seleccionada";
    private String nombreUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ag_boleta);

        nombreUsuario = getIntent().getStringExtra("nombreUsuario");

        adminServicioInput = findViewById(R.id.AdminServicioinput);
        transporteInput = findViewById(R.id.Transporteinput);
        consumoInput = findViewById(R.id.Consumoinput);
        valorKwhInput = findViewById(R.id.ValorKwhinput);
        tvFechaSeleccionada = findViewById(R.id.tvFechaSeleccionada);
        botAgregarBoleta = findViewById(R.id.botAgregarBoleta);
        botSelectDate = findViewById(R.id.botSelectDate);

        botSelectDate.setOnClickListener(v -> showDatePicker());

        botAgregarBoleta.setOnClickListener(v -> agregarBoleta());
    }

    private void showDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            fechaSeleccionada = dayOfMonth + "/" + (month1 + 1) + "/" + year1;
            tvFechaSeleccionada.setText(fechaSeleccionada);
        }, year, month, day);

        datePickerDialog.show();
    }

    private void agregarBoleta() {
        if (validarCampos()) {
            SharedPreferences preferences = getSharedPreferences("userPrefs_" + nombreUsuario, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            //esto era un contador para poder poner en ver perfil cuantas boletas agrego el usuario,
            // quizas mas adelante la agrege, pero trate y no me funciono
            int numeroBoletas = preferences.getInt("numeroBoletas", 0);
            numeroBoletas++;

            editor.putInt("numeroBoletas", numeroBoletas);
            editor.putString("fechaBoleta_" + numeroBoletas, fechaSeleccionada);
            editor.putString("adminServicio_" + numeroBoletas, adminServicioInput.getText().toString());
            editor.putString("transporte_" + numeroBoletas, transporteInput.getText().toString());
            editor.putString("consumo_" + numeroBoletas, consumoInput.getText().toString());
            editor.putString("valorKwh_" + numeroBoletas, valorKwhInput.getText().toString());
            editor.apply();

            Toast.makeText(this, "Boleta agregada correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(AG_boleta.this, MenuPrincipal.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean validarCampos() {
        if (adminServicioInput.getText().toString().isEmpty() ||
                transporteInput.getText().toString().isEmpty() ||
                consumoInput.getText().toString().isEmpty() ||
                valorKwhInput.getText().toString().isEmpty()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (fechaSeleccionada.equals("No seleccionada")) {
            Toast.makeText(this, "Debe seleccionar una fecha", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
