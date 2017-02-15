package com.ruben.connecttomysql.irrigation.severalTimes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.irrigation.severalTimes.momentDay.ListMomentDayActivity;
import com.ruben.connecttomysql.model.Irrigation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DisplaySeveralTimesActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView nombreTv;
    private TextView cancelMomentTv;
    private TextView startDateTv;
    private TextView endDateTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_several_times);
        //Instanciamos los elementos
        nombreTv = (TextView) findViewById(R.id.textView29);
        cancelMomentTv = (TextView) findViewById(R.id.textView30);
        startDateTv = (TextView) findViewById(R.id.textView31);
        endDateTv = (TextView) findViewById(R.id.textView32);

        //Obtenemos la farm pasada por parametro
        Irrigation riego =(Irrigation) getIntent().getSerializableExtra("riego");

        //Cargamos los valores a mostrar

        nombreTv.setText(riego.getName());

        if(riego.getCancelMoment()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getCancelMoment());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String cancelMomentStr = sdf.format(calendar.getTime());

            cancelMomentTv.setText(cancelMomentStr);
        }


        if(riego.getStartDate()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getStartDate());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String cancelStartMomentStr = sdf.format(calendar.getTime());


            startDateTv.setText(cancelStartMomentStr);
        }

        if(riego.getEndDate()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getEndDate());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String cancelEndDateStr = sdf.format(calendar.getTime());


            endDateTv.setText(cancelEndDateStr);
        }


        Button buttonCancelar = (Button) findViewById(R.id.button11);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp now = new Timestamp(System.currentTimeMillis()-100);
                riego.setCancelMoment(now);

                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(),ConnectionUtils.getUser(),ConnectionUtils.getPass());
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    java.sql.Timestamp fechaCancelDB = new java.sql.Timestamp(now.getTime());

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud+",farmId:"+farmId);
                    String sql = "update IRRIGATION set cancelMoment ='"+fechaCancelDB +"' where id="+riego.getId();
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                }catch(Exception e){
                    e.printStackTrace();
                }
                Intent intent = new Intent(DisplaySeveralTimesActivity.this, DisplaySeveralTimesActivity.class);
                intent.putExtra("riego", riego);
                startActivity(intent);
            }
        });

        Button buttonEditar = (Button) findViewById(R.id.button14);


        buttonEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplaySeveralTimesActivity.this, EditSeveralTimesActivity.class);
                intent.putExtra("riego", riego);
                startActivity(intent);
            }
        });

        Button buttonMomentDay= (Button) findViewById(R.id.button12);

        buttonMomentDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplaySeveralTimesActivity.this, ListMomentDayActivity.class);
                intent.putExtra("idIrrigation", riego.getId());
                startActivity(intent);
            }
        });

        // Si el riego ya estaba cancelado ocultamos el boton
        if(riego.getCancelMoment()==null) {
            buttonCancelar.setVisibility(View.VISIBLE);
        }else{
            buttonCancelar.setVisibility(View.INVISIBLE);
        }
    }





}
