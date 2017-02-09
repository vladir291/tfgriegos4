package com.ruben.connecttomysql.irrigation.severalTimesSchedule;

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
import com.ruben.connecttomysql.model.SeveralTimesSchedule;

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

public class EditSeveralTimesScheduleActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText nombreEt;
    private EditText duracionEt;
    private EditText inicioEt;
    private EditText finEt;
    private EditText horaEt;
    private EditText minutoEt;
    private Integer irrigationId;
    private SeveralTimesSchedule riego;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_several_times_schedule);

        //Instanciamos los elementos
        nombreEt = (EditText) findViewById(R.id.editText1);
        duracionEt = (EditText) findViewById(R.id.editText2);
        inicioEt = (EditText) findViewById(R.id.editText6);
        finEt = (EditText) findViewById(R.id.editText7);
        horaEt = (EditText) findViewById(R.id.editText8);
        minutoEt = (EditText) findViewById(R.id.editText9);

        //Obtenemos la farm pasada por parametro
        riego =(SeveralTimesSchedule) getIntent().getSerializableExtra("riego");

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






        horaEt.setText(riego.getHours().toString());
        minutoEt.setText(riego.getMinutes().toString());
        duracionEt.setText(riego.getDuration().toString());

        irrigationId= riego.getId();
        Log.d("Debug", "Irrigation id: " + irrigationId);
        Button buttonAceptar = (Button) findViewById(R.id.button3);


        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new EditarSeveralTimesSchedule().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class EditarSeveralTimesSchedule extends AsyncTask<Void,Void,Void> {
        private String nombre="";
        private Integer duracion=0;
        private Date fechaInicio = new Date(System.currentTimeMillis());
        private Date fechaFin = new Date(System.currentTimeMillis()+1000000);
        private Integer hora=0;
        private Integer minuto=0;

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

                        duracion = Integer.parseInt(duracionEt.getText().toString());

                        if(!inicioEt.getText().toString().isEmpty()){
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                            Date parsedTimeStamp = null;
                            try {
                                parsedTimeStamp = dateFormat.parse(inicioEt.getText().toString());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Log.d("Debug", "he entrado: "+fechaInicio.toString());
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

                        hora = Integer.parseInt(horaEt.getText().toString());

                        minuto = Integer.parseInt(minutoEt.getText().toString());






                        //Log.d("Debug", "El valor del usuario es: " + nomEditText);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = SeveralTimesScheduleUtils.compruebaErrores(EditSeveralTimesScheduleActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud+",farmId:"+farmId);
                    String sql = "update IRRIGATION set name='"+nombre + "' where id="+irrigationId;
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                    String sql2 = "update SEVERALTIMESSCHEDULE set startDate='"+fechaInicio + "', endDate='"+fechaFin+"', hours="+hora+", minutes="+minuto+", duration="+duracion+" where id_irrigation="+irrigationId;

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
            errores = SeveralTimesScheduleUtils.compruebaErrores(EditSeveralTimesScheduleActivity.this);
            SeveralTimesScheduleUtils.visualizaErrores(EditSeveralTimesScheduleActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (EditSeveralTimesScheduleActivity.this, ListFarmsActivity.class);

                //intent.putExtra("plot",plot);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }


    }

