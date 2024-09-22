package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

public class Editar_Eliminar extends AppCompatActivity {
    private ConstraintLayout aparatoItemLayout;
    private TextView nombreTextView, cantidadTextView, energiaTextView;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_eliminar);

        aparatoItemLayout = findViewById(R.id.aparato_item_layout);
        nombreTextView = findViewById(R.id.textViewNombreAparato);
        cantidadTextView = findViewById(R.id.textViewCantidadUtiliza);
        energiaTextView = findViewById(R.id.textViewTipoEnergia);

        preferences = getSharedPreferences("aparatoPrefs", Context.MODE_PRIVATE);
        cargarDatosAparato();

        aparatoItemLayout.setOnClickListener(v -> mostrarPopupOpciones());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
                    getSupportFragmentManager().popBackStack();
                    cargarDatosAparato();
                } else {
                    finish();
                }
            }
        });

        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                aparatoItemLayout.setVisibility(View.VISIBLE);
                cargarDatosAparato();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatosAparato();
    }

    private void cargarDatosAparato() {
        String nombreAparato = preferences.getString("nombreAparato", null);
        String cantidadUtiliza = preferences.getString("cantidadUsada", null);
        boolean usaKwh = preferences.getBoolean("usaKwh", true);
        String tipoEnergia = usaKwh ? "kWh" : "Watts";

        if (nombreAparato != null && cantidadUtiliza != null) {
            aparatoItemLayout.setVisibility(View.VISIBLE);
            nombreTextView.setText("Nombre del Aparato: " + nombreAparato);
            cantidadTextView.setText("Cantidad que utiliza: " + cantidadUtiliza);
            energiaTextView.setText("Tipo de Energía: " + tipoEnergia);
        } else {
            aparatoItemLayout.setVisibility(View.GONE);
            Toast.makeText(this, "No hay aparatos agregados", Toast.LENGTH_SHORT).show();
        }
    }

    private void mostrarPopupOpciones() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Opciones del Aparato")
                .setMessage("¿Qué te gustaría hacer?")
                .setPositiveButton("Editar", (dialog, which) -> editarAparato())
                .setNegativeButton("Eliminar", (dialog, which) -> confirmarEliminarAparato())
                .setNeutralButton("Cancelar", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void confirmarEliminarAparato() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Eliminar Aparato");
        builder.setMessage("¿Estás seguro de que deseas eliminar este aparato?");
        builder.setPositiveButton("Eliminar", (dialog, which) -> eliminarAparato());
        builder.setNegativeButton("Cancelar", (dialog, which) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void eliminarAparato() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove("nombreAparato");
        editor.remove("cantidadUsada");
        editor.remove("usaKwh");
        editor.apply();

        aparatoItemLayout.setVisibility(View.GONE);
        Toast.makeText(this, "Aparato eliminado", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Editar_Eliminar.this, MenuPrincipal.class);
        startActivity(intent);
        finish();
    }

    private void editarAparato() {
        Fragment fragment = new EditarAparatoFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        aparatoItemLayout.setVisibility(View.GONE);
    }
}