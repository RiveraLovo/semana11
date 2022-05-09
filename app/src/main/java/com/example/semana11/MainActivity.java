package com.example.semana11;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;

public class MainActivity extends AppCompatActivity {
    JSONArray datosJSon;
    JSONObject jsonObject;
    Bundle parametros = new Bundle();
    Integer posicion = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        obtenerDatos myAsync = obtenerDatos();
        myAsync.execute();

        FloatingActionButton btn = (FloatingActionButton) findViewById(R.id.btnAgregar);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                parametros.putString("accion", "nuevo");
                nuevo_agenda();
            }
        });


    }

    public void nuevo_agenda() {
        Intent agregar_agregar = new Intent(MainActivity.this, agregar_agregar.class);
        agregar_agregar.putExtras(parametros);
        startActivity(agregar_agregar);
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.resmenu, menu);

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        try {
            datosJSon.getJSONObject(info.position);
            menu.setHeaderTitle(datosJSon.getJSONObject(info.position).getJSONObject("value").getString("nombre").toString());
            posicion = info.position;
        } catch (Exception ex) {

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.mnxAgregar:
                parametros.putString("accion", "nuevo");
                nuevo_agenda();
                return true;

            case R.id.mnxModificar:
                parametros.putString("accion","modificar");
            try {
                parametros.putString("valores", datosJSon.getJSONObject(posicion).getJSONObject("value").toString());
            }catch (Exception ex){

            }
            return true;
            case R.id.mnxEliminar:
                JSONObject miData = new JSONObject();
                try {
                    miData.put("_id", datosJSon.getJSONObject(posicion).getJSONObject("value").getString("_id"));
                }catch (Exception ex){

                }

                eliminarDatos obEliminar = new eliminarDatos();
                obEliminar.execute(miData.toString() );
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private class obtenerDatos extends AsyncTask<Void, Void, String>{
        HttpURLConnection urlConnection;
        @Override
        protected String doIntBackground(Void... params){
            StringBuilder result = new StringBuilder();
            try{

            }
        }
    }
}