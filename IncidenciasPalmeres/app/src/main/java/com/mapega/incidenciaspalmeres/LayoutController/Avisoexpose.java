package com.mapega.incidenciaspalmeres.LayoutController;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.mapega.incidenciaspalmeres.ObjectClass.Aviso;
import com.mapega.incidenciaspalmeres.R;

public class Avisoexpose extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avisoexpose);

        Aviso aviso = getIntent().getParcelableExtra("aviso");
    }
}