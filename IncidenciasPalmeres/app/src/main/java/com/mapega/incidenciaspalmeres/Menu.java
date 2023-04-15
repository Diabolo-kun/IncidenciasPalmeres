package com.mapega.incidenciaspalmeres;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Menu extends AppCompatActivity {
    private String mEmail; // variable de instancia para guardar el correo electrónico del usuario

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);

        // obtener el correo electrónico del usuario del Intent
        Intent intent = getIntent();
        mEmail = intent.getStringExtra("email");

        // mostrar el correo electrónico en la interfaz de usuario
        TextView emailTextView = findViewById(R.id.textView2);
        emailTextView.setText(mEmail);
    }
}
