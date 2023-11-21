package com.example.adivina_palabra;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static final int NUM_PALABRAS= 6;
    public static final int NUM_LETRAS_EN_PALABRA=5;
    Button A, B, C, D, E, F, G, H, I, J, K, L, M, N, Ñ, O, P, Q , r, S, T, U, V, W, X, Y, Z;
    ImageButton mode;
    ToggleButton toggle;
    private TextView palabras[][] = new TextView[NUM_PALABRAS][NUM_LETRAS_EN_PALABRA];
    ArrayList<String>tMarcadas=new ArrayList<String>();
    ArrayList<String>tContains=new ArrayList<String>();
    ArrayList<String>tError=new ArrayList<String>();
    String palabraAAdivinar="";
    int intento;
    int indiceletraActual;
    Button boton;
    TextView reinicio;
    Button[] teclado={A, B, C, D, E, F, G, H, I, J, K, L, M, N, Ñ, O, P, Q , r, S, T, U, V, W, X, Y, Z };
    TableLayout tableLayout_teclado;
    TextView tv_resultado, tv_info, texto;
    BDpalabras db;
    LinearLayout fondo;
    ScrollView scroll;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db=new BDpalabras(this);
        texto=this.findViewById(R.id.texto);
        scroll=this.findViewById(R.id.scroll);
        fondo=this.findViewById(R.id.fondo);
        reinicio=this.findViewById(R.id.reiniciar);
        toggle=this.findViewById(R.id.toggle);
        tableLayout_teclado = findViewById(R.id.tableLayout_teclado);
        tv_resultado = findViewById(R.id.tv_resultado);
        tv_info=this.findViewById(R.id.tv_info);
        mode=this.findViewById(R.id.light);
        inicializaArrayPalabras();
        inicializa_teclado();
        String todasLasSetencias = null;
        try {
            todasLasSetencias = Utilidades.obtentTextoFicheroCarpetaAssets(this.getAssets().open("palabras5c.sql"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        palabraAAdivinar=db.obtenPalabraAleatoria();
		// Completar. Aquí debemos obtener y asignar a palabraAAdivinar una palabra aleatoria de la base de datos
		intento = 0;
        indiceletraActual = 0;
        reinicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tMarcadas=new ArrayList<String>();
                tContains=new ArrayList<String>();
                tError=new ArrayList<String>();
                coloreaTeclado();

                for(int i=0;i< 6;i++){
                    for(int j=0;j< 5;j++){
                        palabras[i][j].setText("");
                        palabras[i][j].setBackgroundResource(R.drawable.cuadrado);
                    }
                }
                for(int i=0;i< teclado.length;i++){
                    teclado[i].setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                }
                tableLayout_teclado.setVisibility(View.VISIBLE);
                palabraAAdivinar=db.obtenPalabraAleatoria();
                intento = 0;
                indiceletraActual = 0;
                tv_info.setTextColor(getResources().getColor(R.color.white));
                tv_info.setTextSize(14);
                tv_info.setText("1.Palabras válidas: Palabras sin acentos en singular y verbos sin conjugar.\n2.Color verde: letra y posición acertada.\n3.Color amarillo: letra acertada.\n4.Color gris: letra no acertada.");
                reinicio.setVisibility(View.GONE);
                tv_resultado.setVisibility(View.GONE);
            }
        });
        toggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                coloreaTeclado();
            }
        });
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.isActivated()){
                    scroll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    fondo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    tv_resultado.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    tv_info.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    texto.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    for(int i=0;i<5;i++){
                        for(int j=0;j<6;j++){
                            palabras[j][i].setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                        }
                    }
                    view.setActivated(false);
                }else{
                    scroll.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    fondo.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    tv_resultado.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    tv_info.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    texto.setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                    for(int i=0;i<5;i++){
                        for(int j=0;j<6;j++){
                            palabras[j][i].setTextColor(ColorStateList.valueOf(getResources().getColor(R.color.black)));
                        }
                    }
                    view.setActivated(true);
                }
            }
        });
    }


    void inicializaArrayPalabras() {
        palabras[0][0] = findViewById(R.id.textView_0_0);
        palabras[0][1] = findViewById(R.id.textView_0_1);
        palabras[0][2] = findViewById(R.id.textView_0_2);
        palabras[0][3] = findViewById(R.id.textView_0_3);
        palabras[0][4] = findViewById(R.id.textView_0_4);

        palabras[1][0] = findViewById(R.id.textView_1_0);
        palabras[1][1] = findViewById(R.id.textView_1_1);
        palabras[1][2] = findViewById(R.id.textView_1_2);
        palabras[1][3] = findViewById(R.id.textView_1_3);
        palabras[1][4] = findViewById(R.id.textView_1_4);

        palabras[2][0] = findViewById(R.id.textView_2_0);
        palabras[2][1] = findViewById(R.id.textView_2_1);
        palabras[2][2] = findViewById(R.id.textView_2_2);
        palabras[2][3] = findViewById(R.id.textView_2_3);
        palabras[2][4] = findViewById(R.id.textView_2_4);

        palabras[3][0] = findViewById(R.id.textView_3_0);
        palabras[3][1] = findViewById(R.id.textView_3_1);
        palabras[3][2] = findViewById(R.id.textView_3_2);
        palabras[3][3] = findViewById(R.id.textView_3_3);
        palabras[3][4] = findViewById(R.id.textView_3_4);

        palabras[4][0] = findViewById(R.id.textView_4_0);
        palabras[4][1] = findViewById(R.id.textView_4_1);
        palabras[4][2] = findViewById(R.id.textView_4_2);
        palabras[4][3] = findViewById(R.id.textView_4_3);
        palabras[4][4] = findViewById(R.id.textView_4_4);

        palabras[5][0] = findViewById(R.id.textView_5_0);
        palabras[5][1] = findViewById(R.id.textView_5_1);
        palabras[5][2] = findViewById(R.id.textView_5_2);
        palabras[5][3] = findViewById(R.id.textView_5_3);
        palabras[5][4] = findViewById(R.id.textView_5_4);
        
    }
    void inicializa_teclado(){
        teclado[0]=this.findViewById(R.id.btn_A);
        teclado[1]=this.findViewById(R.id.btn_B);
        teclado[2]=this.findViewById(R.id.btn_C);
        teclado[3]=this.findViewById(R.id.btn_D);
        teclado[4]=this.findViewById(R.id.btn_E);
        teclado[5]=this.findViewById(R.id.btn_F);
        teclado[6]=this.findViewById(R.id.btn_G);
        teclado[7]=this.findViewById(R.id.btn_H);
        teclado[8]=this.findViewById(R.id.btn_I);
        teclado[9]=this.findViewById(R.id.btn_J);
        teclado[10]=this.findViewById(R.id.btn_K);
        teclado[11]=this.findViewById(R.id.btn_L);
        teclado[12]=this.findViewById(R.id.btn_M);
        teclado[13]=this.findViewById(R.id.btn_N);
        teclado[14]=this.findViewById(R.id.btn_Ñ);
        teclado[15]=this.findViewById(R.id.btn_O);
        teclado[16]=this.findViewById(R.id.btn_P);
        teclado[17]=this.findViewById(R.id.btn_Q);
        teclado[18]=this.findViewById(R.id.btn_R);
        teclado[19]=this.findViewById(R.id.btn_S);
        teclado[20]=this.findViewById(R.id.btn_T);
        teclado[21]=this.findViewById(R.id.btn_U);
        teclado[22]=this.findViewById(R.id.btn_V);
        teclado[23]=this.findViewById(R.id.btn_W);
        teclado[24]=this.findViewById(R.id.btn_X);
        teclado[25]=this.findViewById(R.id.btn_Y);
        teclado[26]=this.findViewById(R.id.btn_Z);

    }


    public void click_letra(View view) {

        if (indiceletraActual<=NUM_LETRAS_EN_PALABRA-1 && palabras[intento][indiceletraActual].getText().length()==0 ) {
            Button b = (Button) view;
            char letra = b.getText().charAt(0);
            palabras[intento][indiceletraActual].setText(letra+"");
            palabras[intento][indiceletraActual].setBackgroundResource(R.drawable.cuadrado_resaltado);
            if (indiceletraActual < NUM_LETRAS_EN_PALABRA-1) indiceletraActual++;
        }

    }

    public void click_enviar(View view) {
        if (indiceletraActual == NUM_LETRAS_EN_PALABRA-1 && palabras[intento][indiceletraActual].getText().length()>0) {
            String palabraUsuario = "";
            for (int i = 0; i < NUM_LETRAS_EN_PALABRA; i++) {
                palabraUsuario = palabraUsuario + palabras[intento][i].getText();
            }

            if (palabraUsuario.equals(palabraAAdivinar)) {
                colorearPalabraIntento(palabraUsuario);
                tv_info.setTextSize(25);
                tv_info.setTextColor(getResources().getColor(R.color.green));
                tv_info.setText("¡ENHORABUENA!\n ¡HAS GANADO!");
                tableLayout_teclado.setVisibility(View.GONE);
                reinicio.setVisibility(View.VISIBLE);
            }
            else {
				boolean existeEnDiccionario= db.palabraEnDiccionario(palabraUsuario);
				// Completar
                if (!existeEnDiccionario) {
                    Toast.makeText(getApplicationContext(),"La palabra no está en el diccionario", Toast.LENGTH_SHORT).show();
                } else {
                    colorearPalabraIntento(palabraUsuario);
                    coloreaTeclado();
                    if (intento<NUM_PALABRAS-1) {
                        intento++;
                        indiceletraActual = 0;
                    }
                    else {
                        tableLayout_teclado.setVisibility(View.GONE);
                        tv_info.setTextSize(20);
                        tv_info.setText("PALABRA: " + palabraAAdivinar);
                        tv_resultado.setTextColor(getResources().getColor(R.color.red));
                        tv_resultado.setText("HA PERDIDO \n ¡VUELVA A INTENTARLO!");
                        reinicio.setVisibility(View.VISIBLE);
                    }
                }
            }
        }
        else {
            Toast.makeText(getApplicationContext(),"Debe introducir todas las letras",Toast.LENGTH_SHORT).show();
        }

    }

    public void click_borrar(View view) {
        if (palabras[intento][indiceletraActual].getText().length()==0) {
            if (indiceletraActual>0) indiceletraActual--;
        }
        palabras[intento][indiceletraActual].setText("");
        palabras[intento][indiceletraActual].setBackgroundResource(R.drawable.cuadrado);

    }

    @SuppressLint("ResourceAsColor")
    void colorearPalabraIntento(String palabraUsuario) {
        for(int i=0; i<palabraAAdivinar.length(); i++ ) {

            int cont1=0;
            int cont2=0;
            int cont3=0;

            for(int j=0; j<palabraAAdivinar.length(); j++ ) {

                if (palabraAAdivinar.charAt(j) == palabraUsuario.charAt(i)) {
                   cont1++;

                }
            }
            for(int j=0;j<5;j++){
                if(palabraUsuario.charAt(j)==palabraUsuario.charAt(i) && palabraUsuario.charAt(j)==palabraAAdivinar.charAt(j)){
                    cont2++;
                }
            }
            for(int j=0;j<i;j++){
                if(palabraUsuario.charAt(i)==palabraUsuario.charAt(j)){
                    cont3++;
                }
            }

            if (palabraAAdivinar.charAt(i) == palabraUsuario.charAt(i)) {
                palabras[intento][i].setBackgroundResource(R.drawable.cuadrado_verde);
                if (palabraAAdivinar.charAt(i) == palabraUsuario.charAt(i)&& cont2==cont1) {
                    tMarcadas.add(String.valueOf(palabraUsuario.charAt(i)));
                }else{
                    tContains.add(String.valueOf(palabraUsuario.charAt(i)));
                }

            }
            else if (palabraAAdivinar.contains(palabraUsuario.charAt(i)+"") && cont1>cont2 && cont1>cont3){
                palabras[intento][i].setBackgroundResource(R.drawable.cuadrado_amarillo);
                tContains.add(String.valueOf(palabraUsuario.charAt(i)));

            }else if(!palabraAAdivinar.contains(palabraUsuario.charAt(i)+"")){
                tError.add(String.valueOf(palabraUsuario.charAt(i)));
                palabras[intento][i].setBackgroundResource(R.drawable.cuadrado_gris);
            }else{
                palabras[intento][i].setBackgroundResource(R.drawable.cuadrado_gris);
            }
        }
    }
    public void coloreaTeclado(){

        for(int i=0;i<tContains.size();i++){
            switch (tContains.get(i)){
                case "A":
                    boton=this.findViewById(R.id.btn_A);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    break;
                case "B":

                    boton=this.findViewById(R.id.btn_B);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    break;


                case "C":

                    boton=this.findViewById(R.id.btn_C);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    break;

                case "D":

                    boton=this.findViewById(R.id.btn_D);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    break;

                case "E":

                    boton=this.findViewById(R.id.btn_E);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    break;

                case "F":

                    boton=this.findViewById(R.id.btn_F);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "G":

                    boton = this.findViewById(R.id.btn_G);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "H":

                    boton = this.findViewById(R.id.btn_H);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "I":

                    boton = this.findViewById(R.id.btn_I);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "J":

                    boton = this.findViewById(R.id.btn_J);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "K":

                    boton = this.findViewById(R.id.btn_K);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "L":

                    boton = this.findViewById(R.id.btn_L);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "M":

                    boton = this.findViewById(R.id.btn_M);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "N":

                    boton = this.findViewById(R.id.btn_N);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "Ñ":

                    boton = this.findViewById(R.id.btn_Ñ);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "O":

                    boton = this.findViewById(R.id.btn_O);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "P":

                    boton = this.findViewById(R.id.btn_P);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "Q":

                    boton = this.findViewById(R.id.btn_Q);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "R":

                    boton = this.findViewById(R.id.btn_R);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "S":

                    boton = this.findViewById(R.id.btn_S);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "T":

                    boton = this.findViewById(R.id.btn_T);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "U":

                    boton = this.findViewById(R.id.btn_U);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "V":

                    boton = this.findViewById(R.id.btn_V);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "W":

                    boton = this.findViewById(R.id.btn_W);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "X":

                    boton = this.findViewById(R.id.btn_X);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "Y":

                    boton = this.findViewById(R.id.btn_Y);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;
                case "Z":

                    boton = this.findViewById(R.id.btn_Z);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));

                    break;

            }
        }

        if(toggle.isChecked()) {
            for (int i = 0; i < teclado.length; i++) {
                if (tError.contains(teclado[i].getText().toString().toUpperCase())) {
                    teclado[i].setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.red)));
                }
            }
        }else{
            for (int i = 0; i < teclado.length; i++) {
                if (tError.contains(teclado[i].getText().toString().toUpperCase())) {
                    teclado[i].setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.grey)));
                }
            }
        }

        for(int i=0;i<tMarcadas.size();i++){
            switch (tMarcadas.get(i)){
                case "A":
                    boton=this.findViewById(R.id.btn_A);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    break;
                case "B":

                    boton=this.findViewById(R.id.btn_B);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    break;


                case "C":

                    boton=this.findViewById(R.id.btn_C);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    break;

                case "D":

                    boton=this.findViewById(R.id.btn_D);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    break;

                case "E":

                    boton=this.findViewById(R.id.btn_E);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));
                    break;

                case "F":

                    boton=this.findViewById(R.id.btn_F);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "G":

                    boton = this.findViewById(R.id.btn_G);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "H":

                    boton = this.findViewById(R.id.btn_H);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "I":

                    boton = this.findViewById(R.id.btn_I);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "J":

                    boton = this.findViewById(R.id.btn_J);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "K":

                    boton = this.findViewById(R.id.btn_K);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "L":

                    boton = this.findViewById(R.id.btn_L);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "M":

                    boton = this.findViewById(R.id.btn_M);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "N":

                    boton = this.findViewById(R.id.btn_N);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "Ñ":

                    boton = this.findViewById(R.id.btn_Ñ);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "O":

                    boton = this.findViewById(R.id.btn_O);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "P":

                    boton = this.findViewById(R.id.btn_P);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "Q":

                    boton = this.findViewById(R.id.btn_Q);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "R":

                    boton = this.findViewById(R.id.btn_R);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "S":

                    boton = this.findViewById(R.id.btn_S);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "T":

                    boton = this.findViewById(R.id.btn_T);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "U":

                    boton = this.findViewById(R.id.btn_U);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "V":

                    boton = this.findViewById(R.id.btn_V);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "W":

                    boton = this.findViewById(R.id.btn_W);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "X":

                    boton = this.findViewById(R.id.btn_X);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "Y":

                    boton = this.findViewById(R.id.btn_Y);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;
                case "Z":

                    boton = this.findViewById(R.id.btn_Z);
                    boton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.green)));

                    break;

            }
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        // Completar
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        // Completar
    }
}
