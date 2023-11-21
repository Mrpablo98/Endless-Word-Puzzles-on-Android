package com.example.adivina_palabra;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class BDpalabras extends SQLiteOpenHelper {

    Context contexto;
    SQLiteDatabase db=null;
    final static int version=1;
    final static String nombre_bd="palabras5c.sql";

    public BDpalabras(@Nullable Context context) {
        super(context, nombre_bd, null, version);
        contexto=context;

    }

    public void abreBD() {
        if (db==null) {
            db = this.getReadableDatabase();
        }
    }

    public  void cierraBD() {
        if (db!=null) {
            db.close();
            db = null;
        }
    }
	
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        try {
            String todasLasSetencias = Utilidades.obtentTextoFicheroCarpetaAssets(contexto.getAssets().open("palabras5c.sql"));
			Scanner sc=new Scanner(todasLasSetencias);
            sc.useDelimiter("\\s*;\\s*");
            String createTable=sc.next();
            String insertDatos=sc.next();
            sqLiteDatabase.execSQL(createTable);
            sqLiteDatabase.execSQL(insertDatos);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public String obtenPalabraAleatoria(){
        abreBD();
		String palabraAleatoria="";
		String sqlSelect="SELECT * FROM TBPALABRAS";
        Cursor filas=db.rawQuery(sqlSelect, null);
        int index= (int) Math.round(Math.random()* filas.getCount());
        if(filas.getCount()>0){
            int indiceCol=filas.getColumnIndex("palabra");
            filas.moveToPosition(index);
            palabraAleatoria=filas.getString(indiceCol).toUpperCase();
        }
		// Completar
		
        return palabraAleatoria;
    }

    public boolean palabraEnDiccionario(String palabraBuscar) {
        abreBD();
        boolean existe=false;
        String sqlSelect="SELECT * FROM TBPALABRAS";
        Cursor filas=db.rawQuery(sqlSelect, null);
        for(int i=0;i< filas.getCount();i++){
            filas.moveToPosition(i);
            int indiceCol=filas.getColumnIndex("palabra");
            String palabra=filas.getString(indiceCol);
            if(palabra.equalsIgnoreCase(palabraBuscar)){
                existe=true;
            }
        }

       
	   // Completar
	   
        return existe;
    }


}
