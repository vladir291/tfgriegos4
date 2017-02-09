package com.ruben.connecttomysql;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.ruben.connecttomysql.farm.ListFarmsActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    private static final String url= "jdbc:mysql://138.68.102.13:3306/TFGRIEGOS";
    private static final String user = "prueba";
    private static final String pass = "irrigadino";

    // Declaramos los elementos de la interfaz
    private TextView nombre,contrasenya;
    private EditText nombreLogin,contrasenyaLogin;

    boolean usuarioValido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializamos los elementos de la interfaz
        nombre = (TextView) findViewById(R.id.textView);
        contrasenya = (TextView) findViewById(R.id.textView6);

        nombreLogin = (EditText) findViewById(R.id.editText);
        contrasenyaLogin =  (EditText) findViewById(R.id.editText2);


        Button buttonLogin = (Button) findViewById(R.id.button2);


        //Definimos el comportamiento del boton login
        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Loguearse().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton login
    private class Loguearse extends AsyncTask<Void,Void,Void>{
        private String nom="",nomEditText="",contra="",contraEditText="";

        @Override
        protected Void doInBackground(Void... voids) {
            List<String> errores;


            errores = new ArrayList<String>();
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(url,user,pass);

                runOnUiThread(new Runnable() {
                    public void run() {
                        // some code #3 (Write your code here to run in UI thread)

                        //Obtenemos el texto de los campos usuario y contraseña que ha introducido el usuario
                         nomEditText = nombreLogin.getText().toString();
                         contraEditText= contrasenyaLogin.getText().toString();



                        //Log.d("Debug", "El valor del usuario es: " + nomEditText);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = LoginUtils.compruebaErroresLogin(LoginActivity.this);
                //Log.d("Debug", "Tamaño de la lista de errores: " + errores.size());
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Antes de la consulta la contraseña: " + contraEditText);

                    // Pasamos la contraseña a MD5
                    contraEditText = MD5.crypt(contraEditText);

                    String sql = "select * from PROPIETARY where name ='"+nomEditText+"' and password='"+contraEditText+"'";
                    //Realizamos la consulta contra la base de datos
                    final ResultSet rs = st.executeQuery(sql);

                    //rs.next();

                    while(rs.next()) {
                        //Guardamos la id del usuario logueado para asi no tener que pasarla por parametros entre actividades
                        int userId;

                        userId = rs.getInt(1);
                        ConnectionUtils.setUserId(userId);

                        nom=rs.getString(3);
                        contra=rs.getString(4);
                        // Comprobamos si las credenciales que ha introducido son validas

                        //Log.d("Debug", "despues de la consulta el usuario: " + nomEditText);
                        //Log.d("Debug", "despues de la consulta la contraseña: " + contraEditText);
                        //Log.d("Debug", "despues de la consulta el usuario bd: " + nom);
                        //Log.d("Debug", "despues de la consulta la contraseña bd: " + contra);

                        usuarioValido = LoginUtils.compruebaUsuarioValido(nomEditText,contraEditText,nom,contra);

                        //Log.d("Debug", "He entrado");
                    }

                }

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            List<String> errores;
            //nombre.setText(nom);
            //contrasenya.setText(contra);

            // Comprobamos y visualizamos los errores en caso de que fuera necesario
            errores = LoginUtils.compruebaErroresLogin(LoginActivity.this);
            LoginUtils.visualizaErroresLogin(LoginActivity.this,errores);
            LoginUtils.visualizaErroresUsuarioValido(LoginActivity.this,usuarioValido);


            if(errores.size()>0 || usuarioValido==false){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (LoginActivity.this, ListFarmsActivity.class);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }





}
