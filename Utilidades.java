package com.example.adivina_palabra;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.ThreadLocalRandom;

public class Utilidades {


    static public String obtentTextoFicheroCarpetaAssets(InputStream isfichero) {
        BufferedReader entradaConBuffer = null;
        String todo_el_texto="";
        try {
            entradaConBuffer = new BufferedReader(new InputStreamReader(isfichero));
            String linea = entradaConBuffer.readLine();
            while (linea!=null) {
                todo_el_texto = todo_el_texto + linea;
                linea = entradaConBuffer.readLine();
            }
            entradaConBuffer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return todo_el_texto;
    }

}
