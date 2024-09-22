package com.example.afinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditarAparatoFragment extends Fragment {

    private EditText editTextNombreAparato, editTextCantidadUsada;
    private RadioGroup radioGroup;
    private Button buttonEditar, buttonCancelar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_aparato, container, false);

        editTextNombreAparato = view.findViewById(R.id.InputNomOBJ);
        editTextCantidadUsada = view.findViewById(R.id.InputCantidadUtiliza);
        radioGroup = view.findViewById(R.id.radioGroup);
        buttonEditar = view.findViewById(R.id.buttonEditarAparato);
        buttonCancelar = view.findViewById(R.id.buttonCancelarEditarAparato);

        cargarDatosAparato();

        buttonEditar.setOnClickListener(v -> {
            editarAparato(); // Editar aparato
            requireActivity().getSupportFragmentManager().popBackStack(); // Volver a la actividad anterior
        });

        buttonCancelar.setOnClickListener(v -> requireActivity().getSupportFragmentManager().popBackStack());

        return view;
    }

    private void cargarDatosAparato() {
        Context context = getActivity();
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("aparatoPrefs", Context.MODE_PRIVATE);
            String nombreAparato = preferences.getString("nombreAparato", "Nombre no disponible");
            String cantidadUsada = preferences.getString("cantidadUsada", "0");
            boolean usaKwh = preferences.getBoolean("usaKwh", true);

            editTextNombreAparato.setText(nombreAparato);
            editTextCantidadUsada.setText(cantidadUsada);

            if (usaKwh) {
                radioGroup.check(R.id.radioButtonKwh);
            } else {
                radioGroup.check(R.id.radioButtonWatts);
            }
        }
    }

    private void editarAparato() {
        String nombreAparato = editTextNombreAparato.getText().toString();
        String cantidadUsada = editTextCantidadUsada.getText().toString();
        int selectedId = radioGroup.getCheckedRadioButtonId();
        boolean usaKwh = (selectedId == R.id.radioButtonKwh);

        Context context = getActivity();
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("aparatoPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();

            editor.putString("nombreAparato", nombreAparato);
            editor.putString("cantidadUsada", cantidadUsada);
            editor.putBoolean("usaKwh", usaKwh);
            editor.apply();

            Toast.makeText(context, "Aparato actualizado", Toast.LENGTH_SHORT).show();
        }
    }
}
