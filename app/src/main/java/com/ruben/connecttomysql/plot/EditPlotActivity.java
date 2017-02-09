package com.ruben.connecttomysql.plot;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.farm.ListFarmsActivity;
import com.ruben.connecttomysql.model.Plot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditPlotActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText nombreEt;
    private Integer farmId;
    private Plot plot;
    private Integer plotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plot);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }


        //Instanciamos los elementos
        nombreEt = (EditText) findViewById(R.id.editTextNombrePlot);

        //Obtenemos la farm pasada por parametro
        plot =(Plot) getIntent().getSerializableExtra("plot");

        //Cargamos los valores a mostrar

        nombreEt.setText(plot.getName());



        plotId= plot.getId();
        //Log.d("Debug", "Farm id: " + farmId);



        Button buttonAceptar = (Button) findViewById(R.id.buttonAceptarPlot);
        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                new EditPlotActivity.EditarPlot().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class EditarPlot extends AsyncTask<Void,Void,Void> {
        private String nombre="";

        @Override
        protected Void doInBackground(Void... voids) {
            List<String> errores;



            errores = new ArrayList<String>();
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(),ConnectionUtils.getUser(),ConnectionUtils.getPass());

                runOnUiThread(new Runnable() {
                    public void run() {
                        // some code #3 (Write your code here to run in UI thread)

                        //Obtenemos el texto de los campos que ha introducido el usuario
                        nombre = nombreEt.getText().toString();



                        //Log.d("Debug", "El valor del usuario es: " + nomEditText);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = PlotUtils.compruebaErrores(EditPlotActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre + " plotid: "+plotId);
                    String sql = "update PLOT set name='"+nombre +"' where id="+plotId;
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);





                }

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            List<String> errores;

            // Comprobamos y visualizamos los errores en caso de que fuera necesario
            errores = PlotUtils.compruebaErrores(EditPlotActivity.this);
            PlotUtils.visualizaErrores(EditPlotActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (EditPlotActivity.this, ListFarmsActivity.class);

                //intent.putExtra("idFarm", farmId);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }


    }


    }

