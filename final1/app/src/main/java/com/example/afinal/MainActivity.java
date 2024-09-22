package com.example.afinal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//lo mas facil de todo el projecto
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void login(View v) {
        EditText camp1 = findViewById(R.id.nombreus);
        String nombreusu = camp1.getText().toString();
        EditText camp2 = findViewById(R.id.Contra);
        String contra = camp2.getText().toString();

        if (nombreusu.isEmpty() || contra.isEmpty()) {
            Toast.makeText(this, "Por favor, ingrese su nombre de usuario y contraseña", Toast.LENGTH_SHORT).show();
            return;
        }

        SharedPreferences preferences = getSharedPreferences("userPrefs", MODE_PRIVATE);

        String nombreRegistrado = preferences.getString("nombreUsuario", "");
        String contraRegistrada = preferences.getString("contra", "");

        if (nombreusu.equals(nombreRegistrado) && contra.equals(contraRegistrada)) {
            SharedPreferences globalPrefs = getSharedPreferences("globalPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = globalPrefs.edit();
            editor.putString("usuarioActual", nombreusu);
            editor.apply();

            Intent i = new Intent(this, MenuPrincipal.class);
            startActivity(i);
            finish();
        } else {
            Toast.makeText(this, "Error, usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
        }
    }

    public void regis(View v) {
        Intent i = new Intent(this, Registro_Usuario.class);
        startActivity(i);
    }
}
