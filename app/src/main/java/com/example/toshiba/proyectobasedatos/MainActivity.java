package com.example.toshiba.proyectobasedatos;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private EditText editext1,editext2,editext3,editext4;
    private Cursor fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editext1= (EditText) findViewById(R.id.et_nombre);
        editext2= (EditText) findViewById(R.id.et_apellidos);
        editext3= (EditText) findViewById(R.id.et_carrera);
        editext4= (EditText) findViewById(R.id.et_dni);
    }


    public void insertar(View v) {
        SQLiteHelper  admin = new SQLiteHelper(this,"administracion", null, 4);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nombre = editext1.getText().toString();
        String apellido = editext2.getText().toString();
        String carrera = editext3.getText().toString();
        String ndocumento = editext4.getText().toString();
        ContentValues registro = new ContentValues();  //es una clase para guardar datos
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("carrera", carrera);
        registro.put("ndocumento", ndocumento);
        bd.insert("alumno", null, registro);
        bd.close();
        editext1.setText("");
        editext2.setText("");
        editext3.setText("");
        editext4.setText("");

        Toast.makeText(this, "SE CARGARON LOS DATOS DEL ALUMNO",
                Toast.LENGTH_SHORT).show();
    }

    public void buscar(View v) {
        SQLiteHelper admin = new SQLiteHelper(this,
                "administracion", null, 4);
        SQLiteDatabase bd = admin.getWritableDatabase(); //Create and/or open a database that will be used for reading and writing.
        String no_documento = editext4.getText().toString();
        Cursor fila = bd.rawQuery(  //devuelve 0 o 1 fila //es una consulta
                "select nombre,apellido,carrera  from alumno where ndocumento=" + no_documento, null);
        if (fila.moveToFirst()) {  //si ha devuelto 1 fila, vamos al primero (que es el unico)
            editext1.setText(fila.getString(0));
            editext2.setText(fila.getString(1));
            editext3.setText(fila.getString(2));
            ;
        } else
            Toast.makeText(this, "EL ALUMNO NO ESTA REGISTRADO" ,
                    Toast.LENGTH_SHORT).show();
        bd.close();

    }

    public void eliminar(View v) {
        SQLiteHelper admin = new SQLiteHelper(this,
                "administracion", null, 4);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String no_docuemnto = editext4.getText().toString();
        int cant = bd.delete("alumno", "ndocumento=" + no_docuemnto, null); // (votantes es la nombre de la tabla, condición)
        bd.close();
        editext1.setText("");
        editext2.setText("");
        editext3.setText("");
        editext4.setText("");
        if (cant == 1)
            Toast.makeText(this, "SE BORRARON LOS DATOS DEL ALUMNO",
                    Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, "EL ALUMNO NO ESTA REGISTRADO",
                    Toast.LENGTH_SHORT).show();
    }

    public void modificar(View v) {
        SQLiteHelper admin = new SQLiteHelper(this,
                "administracion", null, 4);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String nombre = editext1.getText().toString();
        String apellido = editext2.getText().toString();
        String carrera = editext3.getText().toString();
        String ndocumento = editext4.getText().toString();
        ContentValues registro = new ContentValues();
        registro.put("nombre", nombre);
        registro.put("apellido", apellido);
        registro.put("carrera", carrera);
        int cant = bd.update("alumno", registro, "ndocumento=" + ndocumento, null);
        bd.close();
        if (cant == 1)

            Toast.makeText(this, "SE MODIFICARON LOS DATOS", Toast.LENGTH_SHORT)
                    .show();
        else
            Toast.makeText(this, "EL ALUMNO NO ESTA REGISTRADO",
                    Toast.LENGTH_SHORT).show();
    }
}
