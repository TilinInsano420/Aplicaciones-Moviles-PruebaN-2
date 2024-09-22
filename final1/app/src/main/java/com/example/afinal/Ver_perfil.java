package com.example.afinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class Ver_perfil extends AppCompatActivity {
// nunca supe como hacer para que se actualizara rapido como estaba usando fragmentos.
    private TextView textViewNombreUsuario, textViewCorreo;
    private Button botonEditarPerfil;
    private static final int REQUEST_CODE_EDITAR = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        textViewNombreUsuario = findViewById(R.id.NomUs);
        textViewCorreo = findViewById(R.id.Email1);
        botonEditarPerfil = findViewById(R.id.Editar_perfil);

        cargarDatosUsuario();

        botonEditarPerfil.setOnClickListener(v -> mostrarEditarPerfil());

        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (findViewById(R.id.frameLayoutEditarPerfil).getVisibility() == View.VISIBLE) {
                    getSupportFragmentManager().popBackStack();
                    findViewById(R.id.layoutVerPerfil).setVisibility(View.VISIBLE);
                    findViewById(R.id.frameLayoutEditarPerfil).setVisibility(View.GONE);
                } else {
                    finish();
                }
            }
        });
    }

    private void cargarDatosUsuario() {
        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        String usuarioActual = preferences.getString("nombreUsuario", "Nombre no disponible");

        SharedPreferences userPreferences = getSharedPreferences("userPrefs_" + usuarioActual, MODE_PRIVATE);
        String correo = preferences.getString("correo", "Correo no disponible");


        textViewNombreUsuario.setText("Nombre de usuario: " + usuarioActual);
        textViewCorreo.setText("Email: " + correo);

    }

    public void recargarDatosUsuario() {
        cargarDatosUsuario();
    }

    private void mostrarEditarPerfil() {

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        EditarPerfilFragment editarPerfilFragment = new EditarPerfilFragment();
        fragmentTransaction.replace(R.id.frameLayoutEditarPerfil, editarPerfilFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

        findViewById(R.id.layoutVerPerfil).setVisibility(View.GONE);
        findViewById(R.id.frameLayoutEditarPerfil).setVisibility(View.VISIBLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_EDITAR && resultCode == RESULT_OK) {
            if (data != null && data.hasExtra("perfilActualizado")) {
                boolean perfilActualizado = data.getBooleanExtra("perfilActualizado", false);
                if (perfilActualizado) {
                    recargarDatosUsuario();
                }
            } else {
                Log.e("Error", "NOPE");
            }
        }
    }
}