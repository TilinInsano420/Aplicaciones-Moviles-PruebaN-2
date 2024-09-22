package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class EditarPerfilFragment extends Fragment {

    private EditText editTextUsuario, editTextCorreo, editTextContraActual, editTextContraNueva, editTextConfirmarContra;
    private Button buttonEditar, buttonCancelar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editar_perfil, container, false);

        editTextUsuario = view.findViewById(R.id.Eusuario);
        editTextCorreo = view.findViewById(R.id.Eemail);
        editTextContraActual = view.findViewById(R.id.EcontraActual);
        editTextContraNueva = view.findViewById(R.id.Econtra);
        editTextConfirmarContra = view.findViewById(R.id.Econcontra);
        buttonEditar = view.findViewById(R.id.botEditar);
        buttonCancelar = view.findViewById(R.id.botCancelar);

        cargarDatosUsuario();

        buttonEditar.setOnClickListener(v -> editarPerfil());

        buttonCancelar.setOnClickListener(v -> {
            if (getActivity() != null) {
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().findViewById(R.id.layoutVerPerfil).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.frameLayoutEditarPerfil).setVisibility(View.GONE);
            }
        });

        return view;
    }

    private void cargarDatosUsuario() {
        Context context = getActivity();
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE);

            String nombreUsuario = preferences.getString("nombreUsuario", "Nombre no disponible");
            String correo = preferences.getString("correo", "Correo no disponible");

            editTextUsuario.setText(nombreUsuario);
            editTextCorreo.setText(correo);
        }
    }

    private void editarPerfil() {
        Context context = getActivity();
        if (context != null) {
            SharedPreferences preferences = context.getSharedPreferences("userPrefs", Context.MODE_PRIVATE);

            String nuevoUsuario = editTextUsuario.getText().toString();
            String nuevoCorreo = editTextCorreo.getText().toString();
            String contraActual = editTextContraActual.getText().toString();
            String nuevaContra = editTextContraNueva.getText().toString();
            String confirmarContra = editTextConfirmarContra.getText().toString();

            if (TextUtils.isEmpty(nuevoUsuario) && TextUtils.isEmpty(nuevoCorreo) &&
                    TextUtils.isEmpty(contraActual) && TextUtils.isEmpty(nuevaContra)) {
                Toast.makeText(context, "No hay cambios por hacer", Toast.LENGTH_SHORT).show();
                return;
            }

            SharedPreferences.Editor editor = preferences.edit();

            if (!TextUtils.isEmpty(nuevoUsuario)) {
                editor.putString("nombreUsuario", nuevoUsuario);
            }

            if (!TextUtils.isEmpty(nuevoCorreo)) {
                editor.putString("correo", nuevoCorreo);
            }

            String storedPassword = preferences.getString("contra", "");
            if (!TextUtils.isEmpty(contraActual) || !TextUtils.isEmpty(nuevaContra) || !TextUtils.isEmpty(confirmarContra)) {
                if (TextUtils.isEmpty(contraActual)) {
                    Toast.makeText(context, "Debes ingresar tu contraseña actual para cambiarla", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!contraActual.equals(storedPassword)) {
                    Toast.makeText(context, "La contraseña actual es incorrecta", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!nuevaContra.equals(confirmarContra)) {
                    Toast.makeText(context, "Las contraseñas nuevas no coinciden", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!TextUtils.isEmpty(nuevaContra)) {
                    editor.putString("contra", nuevaContra);
                }
            }

            editor.apply();
            Toast.makeText(context, "Perfil actualizado", Toast.LENGTH_SHORT).show();

            Intent resultIntent = new Intent();
            resultIntent.putExtra("perfilActualizado", true);
            if (getActivity() != null) {
                getActivity().setResult(AppCompatActivity.RESULT_OK, resultIntent);
                getActivity().getSupportFragmentManager().popBackStack();
                getActivity().findViewById(R.id.layoutVerPerfil).setVisibility(View.VISIBLE);
                getActivity().findViewById(R.id.frameLayoutEditarPerfil).setVisibility(View.GONE);
            }
        }
    }
}
