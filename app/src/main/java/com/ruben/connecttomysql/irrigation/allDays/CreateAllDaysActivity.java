package com.ruben.connecttomysql.irrigation.allDays;

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
import com.ruben.connecttomysql.irrigation.ListIrrigationActivity;
import com.ruben.connecttomysql.model.Plot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CreateAllDaysActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText nombreEt;
    private EditText duracionEt;
    private EditText inicioEt;
    private EditText finEt;
    private EditText horaEt;
    private EditText minutoEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_all_days);

        //Instanciamos los elementos
        nombreEt = (EditText) findViewById(R.id.editText1);
        duracionEt = (EditText) findViewById(R.id.editText2);
        inicioEt = (EditText) findViewById(R.id.editText6);
        finEt = (EditText) findViewById(R.id.editText7);
        horaEt = (EditText) findViewById(R.id.editText8);
        minutoEt = (EditText) findViewById(R.id.editText9);

        Button buttonAceptar = (Button) findViewById(R.id.button3);
        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateAllDaysActivity.CreateSeveralTimesSchedule().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class CreateSeveralTimesSchedule extends AsyncTask<Void,Void,Void> {
        private String nombre="";
        private Integer duracion=0;
        private Date fechaInicio = new Date(System.currentTimeMillis());
        private Date fechaFin = new Date(System.currentTimeMillis()+1000000);
        private Integer hora=0;
        private Integer minuto=0;
        private String tipoRiego = "ALLDAYS";


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
                        duracion = Integer.parseInt(duracionEt.getText().toString());

                        //FORZAR A QUE SEAN LAS 00:00
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

                        //FORZAR A QUE SEAN LAS 00:00

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

                errores = SeveralTimesScheduleUtils.compruebaErrores(CreateAllDaysActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud);
                    String sql = "insert into IRRIGATION (name,cancelMoment,startDate, endDate,tipo_riego,id_plot) VALUES ('"+nombre+"',"+null+",'"+fechaInicio+"','"+fechaFin+"','"+tipoRiego+"',"+plot.getId()+")";
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
                    Log.d("Debug", "Antes de la consulta el usuario: " + lastIdInt);

                    //Parseamos la fecha para poder introducirlo en la BD


                    while(fechaInicio.getTime() <= fechaFin.getTime()){
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(fechaInicio);

                        //Le cambiamos la hora y minutos
                        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR)+ hora);
                        cal.set(Calendar.MINUTE, cal.get(Calendar.MINUTE)+ minuto);
                        Timestamp fecha = new Timestamp(cal.getTime().getTime());

                        //Le cambiamos el mes
                        cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH)+1);
                        fechaInicio = cal.getTime();

                        String sql2 = "insert into IRRIGATIONMOMENTDAY (irrigationMoment,duration,id_irrigation) VALUES ('"+fecha+"',"+duracion+","+lastIdInt+")";

                        st.executeUpdate(sql2);

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

            // Comprobamos y visualizamos los errores en caso de que fuera necesario
            errores = SeveralTimesScheduleUtils.compruebaErrores(CreateAllDaysActivity.this);
            SeveralTimesScheduleUtils.visualizaErrores(CreateAllDaysActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (CreateAllDaysActivity.this, ListIrrigationActivity.class);

                intent.putExtra("plot", plot);
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }

}

