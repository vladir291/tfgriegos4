package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.farm.ListFarmsActivity;
import com.ruben.connecttomysql.model.SeveralTimesDaySchedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EditSeveralTimesDayScheduleActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText nombreEt;
    private EditText inicioEt;
    private EditText finEt;
    private Integer irrigationId;
    private SeveralTimesDaySchedule riego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_several_times_day_schedule);

        //Instanciamos los elementos
        nombreEt = (EditText) findViewById(R.id.editText1);
        inicioEt = (EditText) findViewById(R.id.editText10);
        finEt = (EditText) findViewById(R.id.editText11);

        //Obtenemos la farm pasada por parametro
        riego =(SeveralTimesDaySchedule) getIntent().getSerializableExtra("riego");

        //Cargamos los valores a mostrar

        nombreEt.setText(riego.getName());

        if(riego.getStartDate()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getStartDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String cancelStartMomentStr = sdf.format(calendar.getTime());


            inicioEt.setText(cancelStartMomentStr);
        }

        if(riego.getEndDate()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getEndDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

            String cancelEndDateStr = sdf.format(calendar.getTime());


            finEt.setText(cancelEndDateStr);
        }

        irrigationId= riego.getId();
        Log.d("Debug", "Irrigation id: " + irrigationId);
        Button buttonAceptar = (Button) findViewById(R.id.button3);


        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditarSeveralTimesDaySchedule().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class EditarSeveralTimesDaySchedule extends AsyncTask<Void,Void,Void> {
        private String nombre="";
        private Date fechaInicio = new Date(System.currentTimeMillis());
        private Date fechaFin = new Date(System.currentTimeMillis()+1000000);

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
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

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

                errores = SeveralTimesDayScheduleUtils.compruebaErrores(EditSeveralTimesDayScheduleActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud+",farmId:"+farmId);
                    String sql = "update IRRIGATION set name='"+nombre + "' where id="+irrigationId;
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                    String sql2 = "update SEVERALTIMESDAYSCHEDULE set startDate='"+fechaInicio + "', endDate='"+fechaFin+"' where id_irrigation="+irrigationId;

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
            errores = SeveralTimesDayScheduleUtils.compruebaErrores(EditSeveralTimesDayScheduleActivity.this);
            SeveralTimesDayScheduleUtils.visualizaErrores(EditSeveralTimesDayScheduleActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (EditSeveralTimesDayScheduleActivity.this, ListFarmsActivity.class);

                //intent.putExtra("farm", farm);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }


    }

