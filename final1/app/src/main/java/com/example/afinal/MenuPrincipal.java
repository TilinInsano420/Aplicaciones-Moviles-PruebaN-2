package com.example.afinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MenuPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menu_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void Sal(View v) {
        SharedPreferences globalPrefs = getSharedPreferences("globalPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = globalPrefs.edit();

        editor.remove("usuarioActual");
        editor.apply();

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }


    public void aga(View v){
        Intent b = new Intent(this,Agregar_Aparato.class);
        startActivity(b);

    }
    public void his(View v){
        Intent c = new Intent(this,historial.class);
        startActivity(c);}

    public void bol(View v){
        Intent d = new Intent(this,AG_boleta.class);
        startActivity(d);}

    public void perfil(View v){
        Intent d = new Intent(this,Ver_perfil.class);
        startActivity(d);}

    public void editelim(View v){
        Intent b = new Intent(this,Editar_Eliminar.class);
        startActivity(b);

    }

    public void cantus(View v){
        Intent b = new Intent(this,Cantidad_usada.class);
        startActivity(b);

    }

    public void ver_gastos(View v){
        Intent b = new Intent(this,Ver_gastos_mes.class);
        startActivity(b);
    }
}