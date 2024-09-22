package com.example.afinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Registro_Usuario extends AppCompatActivity {

    private EditText editTextusuario, editTextEmail, editTextcontra, editTextConcontra;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        editTextusuario = findViewById(R.id.nombreus2);
        editTextEmail = findViewById(R.id.Correoelectronico);
        editTextcontra = findViewById(R.id.Contra);
        editTextConcontra = findViewById(R.id.Confirmar_contra1);

        registerButton = findViewById(R.id.button2);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String nombreUsuario = editTextusuario.getText().toString();
        String correo = editTextEmail.getText().toString();
        String contra = editTextcontra.getText().toString();
        String confirmarContra = editTextConcontra.getText().toString();

        if (nombreUsuario.isEmpty()) {
            editTextusuario.setError("Por favor, ingrese su nombre de usuario");
            editTextusuario.requestFocus();
            return;
        }

        if (correo.isEmpty()) {
            editTextEmail.setError("Por favor, ingrese su correo electronico");
            editTextEmail.requestFocus();
            return;
        }

        if (contra.isEmpty()) {
            editTextcontra.setError("Por favor, ingrese su contraseña");
            editTextcontra.requestFocus();
            return;
        }

        if (confirmarContra.isEmpty()) {
            editTextConcontra.setError("Por favor, confirme su contraseña");
            editTextConcontra.requestFocus();
            return;
        }

        if (!contra.equals(confirmarContra)) {
            editTextConcontra.setError("Las contraseñas no coinciden");
            editTextConcontra.requestFocus();
            return;
        }

        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("nombreUsuario", nombreUsuario);
        editor.putString("correo", correo);
        editor.putString("contra", contra);
        editor.apply();

        Toast.makeText(Registro_Usuario.this, "Registro exitoso Por favor, inicie sesion.", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(Registro_Usuario.this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}