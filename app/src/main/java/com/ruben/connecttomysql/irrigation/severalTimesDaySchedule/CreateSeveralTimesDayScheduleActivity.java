package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.model.Plot;
import com.ruben.connecttomysql.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateSeveralTimesDayScheduleActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText nombreEt;
    private EditText inicioEt;
    private EditText finEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_several_times_day_schedule);

        //Instanciamos los elementos
        nombreEt = (EditText) findViewById(R.id.editText1);
        inicioEt = (EditText) findViewById(R.id.editText10);
        finEt = (EditText) findViewById(R.id.editText11);

        Button buttonAceptar = (Button) findViewById(R.id.button3);
        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateSeveralTimesDayScheduleActivity.CreateSeveralTimesDaySchedule().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class CreateSeveralTimesDaySchedule extends AsyncTask<Void,Void,Void> {
        private String nombre="";
        private Date fechaInicio = new Date(System.currentTimeMillis());
        private Date fechaFin = new Date(System.currentTimeMillis()+1000000);


        Plot plot =(Plot) getIntent().getSerializableExtra("plot");

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

                        if(!inicioEt.getText().toString().isEmpty()){
                            SimpleDateFormat dateFormat = new SimpleDateFormat(
                                    "dd-MM-yyyy");

                            Date parsedTimeStamp = null;
                            try {
                                parsedTimeStamp = dateFormat.parse(inicioEt.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            fechaInicio = new Timestamp(parsedTimeStamp.getTime());


                        }

                        if(!finEt.getText().toString().isEmpty()){
                            SimpleDateFormat dateFormat = new SimpleDateFormat(
                                    "dd-MM-yyyy");

                            Date parsedTimeStamp = null;
                            try {
                                parsedTimeStamp = dateFormat.parse(finEt.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            fechaFin = new Timestamp(parsedTimeStamp.getTime());


                        }







                        //Log.d("Debug", "El valor del usuario es: " + nomEditText);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = SeveralTimesDayScheduleUtils.compruebaErrores(CreateSeveralTimesDayScheduleActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud);
                    String sql = "insert into IRRIGATION (cancelMoment,id_plot,name) VALUES ("+null+","+plot.getId()+",'"+nombre+"')";
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                    ResultSet rs = st.executeQuery("select last_insert_id() as last_id from IRRIGATION");

                    Integer lastIdInt;

                    if (rs.next()) {
                        String lastid = rs.getString("last_id");
                        lastIdInt= Integer.parseInt(lastid);
                    }else{
                        lastIdInt=0;
                    }

                    String sql2 = "insert into SEVERALTIMESDAYSCHEDULE (startDate,endDate,id_irrigation) VALUES ('"+fechaInicio+"','"+fechaFin+"',"+lastIdInt+")";

                    st.executeUpdate(sql2);



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
            errores = SeveralTimesDayScheduleUtils.compruebaErrores(CreateSeveralTimesDayScheduleActivity.this);
            SeveralTimesDayScheduleUtils.visualizaErrores(CreateSeveralTimesDayScheduleActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (CreateSeveralTimesDayScheduleActivity.this, ListSeveralTimesDayScheduleActivity.class);

                intent.putExtra("plot", plot);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }

}

