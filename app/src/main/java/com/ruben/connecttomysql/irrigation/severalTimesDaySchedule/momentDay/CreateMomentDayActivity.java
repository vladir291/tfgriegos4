package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule.momentDay;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CreateMomentDayActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText momentEt;
    private EditText durationEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_moment_day);

        //Instanciamos los elementos
        momentEt = (EditText) findViewById(R.id.editText1);
        durationEt = (EditText) findViewById(R.id.editText10);

        Button buttonAceptar = (Button) findViewById(R.id.button3);
        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new CreateMomentDayActivity.CreateMomentDaySchedule().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class CreateMomentDaySchedule extends AsyncTask<Void,Void,Void> {
        private Integer duracion = 0;
        private Timestamp fecha;


        Integer idSeveralTimesDaysSchedule =(Integer) getIntent().getSerializableExtra("idSeveralTimesDaysSchedule");
        Integer idIrrigation =(Integer) getIntent().getSerializableExtra("idIrrigation");

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

                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "dd-MM-yyyy hh:mm:ss");

                        Date parsedTimeStamp = null;
                        try {
                            parsedTimeStamp = dateFormat.parse(momentEt.getText().toString());
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        fecha = new Timestamp(parsedTimeStamp.getTime());


                        duracion = Integer.parseInt(durationEt.getText().toString());





                        //Log.d("Debug", "El valor del usuario es: " + nomEditText);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = MomentDayUtils.compruebaErrores(CreateMomentDayActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    String sql = "insert into IRRIGATIONMOMENTDAY (irrigationMoment,duration,id_severalTimesDaySchedule ) VALUES ('"+fecha+"',"+duracion+",'"+idSeveralTimesDaysSchedule+"')";
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
            errores = MomentDayUtils.compruebaErrores(CreateMomentDayActivity.this);
            MomentDayUtils.visualizaErrores(CreateMomentDayActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (CreateMomentDayActivity.this, ListMomentDayActivity.class);

                intent.putExtra("idIrrigation",idIrrigation );
                startActivity(intent);
            }




            super.onPostExecute(result);
        }
    }

}

