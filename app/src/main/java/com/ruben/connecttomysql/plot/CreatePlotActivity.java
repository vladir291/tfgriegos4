package com.ruben.connecttomysql.plot;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.farm.ListFarmsActivity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CreatePlotActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText nombreEt;
    private Switch bombaAguaSw;
    private Boolean bombaAgua;
    private Integer idFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_plot);

        //Instanciamos los elementos
        nombreEt = (EditText) findViewById(R.id.editTextNombrePlot);
        //bombaAguaSw = (Switch) findViewById(R.id.switchBombaAgua2);


        //Obtenemos el plot pasado por parametro
        idFarm =(Integer) getIntent().getSerializableExtra("idFarm");

        Log.d("Debug", "ID farm: " +idFarm);

        Button buttonAceptar = (Button) findViewById(R.id.buttonAceptarPlot);
        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreatePlotActivity.CreatePlot().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class CreatePlot extends AsyncTask<Void,Void,Void> {
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

                        if(bombaAguaSw.isChecked()==true){
                            bombaAgua=true;
                        }else{
                            bombaAgua=false;
                        }






                        //Log.d("Debug", "El valor del usuario es: " + nomEditText);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = PlotUtils.compruebaErrores(CreatePlotActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();
                    Double soilMoistureIrrigationLowerLimit=0.0;
                    Double humidityIrrigationLowerLimit=0.0;
                    Double temperatureIrrigationLowerLimit=0.0;
                    Double soilMoistureIrrigationUpperLimit=0.0;
                    Double humidityIrrigationUpperLimit=0.0;
                    Double temperatureIrrigationUpperLimit=0.0;



                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud);

                    String sql = "insert into PLOT (name,waterPump,id_farm) VALUES ('"+nombre+"',"+bombaAgua+","+idFarm+ ")";

                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                    // Una vez creamos el plot debemos asignarle un ClimatologicalProbe

                    ResultSet rs = st.executeQuery("select last_insert_id() as last_id from PLOT");

                    Integer lastIdInt;

                    if (rs.next()) {
                    String lastid = rs.getString("last_id");
                        lastIdInt= Integer.parseInt(lastid);
                    }else{
                        lastIdInt=0;
                    }



                    Log.d("Debug", "Id de la ultima insercion: " + lastIdInt);
                    sql = "insert into CLIMATOLOGICALPROBE (soilMoistureIrrigationLowerLimit,humidityIrrigationLowerLimit,temperatureIrrigationLowerLimit,soilMoistureIrrigationUpperLimit,humidityIrrigationUpperLimit,temperatureIrrigationUpperLimit,active,id_plot) VALUES ('"+soilMoistureIrrigationLowerLimit+"',"+humidityIrrigationLowerLimit+","+temperatureIrrigationLowerLimit+","+soilMoistureIrrigationUpperLimit+","+humidityIrrigationUpperLimit+","+temperatureIrrigationUpperLimit+","+false+","+lastIdInt+ ")";


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
            errores = PlotUtils.compruebaErrores(CreatePlotActivity.this);
            PlotUtils.visualizaErrores(CreatePlotActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (CreatePlotActivity.this, ListFarmsActivity.class);

                //intent.putExtra("farm", farm);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }
}
