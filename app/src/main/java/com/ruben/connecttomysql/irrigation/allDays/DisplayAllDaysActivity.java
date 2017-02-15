package com.ruben.connecttomysql.irrigation.allDays;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.model.AllDaysForm;
import com.ruben.connecttomysql.model.Irrigation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DisplayAllDaysActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView nombreTv;
    private TextView cancelMomentTv;
    private TextView startDateTv;
    private TextView endDateTv;
    private TextView horaTv;
    private TextView minutoTv;
    private TextView duracionTv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_all_days);
        //Instanciamos los elementos
        nombreTv = (TextView) findViewById(R.id.textView29);
        cancelMomentTv = (TextView) findViewById(R.id.textView30);
        startDateTv = (TextView) findViewById(R.id.textView31);
        endDateTv = (TextView) findViewById(R.id.textView32);
        horaTv = (TextView) findViewById(R.id.textView34);
        minutoTv = (TextView) findViewById(R.id.textView36);
        duracionTv = (TextView) findViewById(R.id.textView39);

        //Obtenemos la farm pasada por parametro
        Irrigation riego =(Irrigation) getIntent().getSerializableExtra("riego");

        //Cargamos los valores a mostrar

        nombreTv.setText(riego.getName());
        if(riego.getCancelMoment()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getCancelMoment());
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            String cancelMomentStr = sdf.format(calendar.getTime());

            cancelMomentTv.setText(cancelMomentStr);
        }


        if(riego.getStartDate()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getStartDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String cancelStartMomentStr = sdf.format(calendar.getTime());


            startDateTv.setText(cancelStartMomentStr);
        }

        if(riego.getEndDate()!= null) {
            Calendar calendar;
            calendar = Calendar.getInstance();


            calendar.setTime(riego.getEndDate());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            String cancelEndDateStr = sdf.format(calendar.getTime());


            endDateTv.setText(cancelEndDateStr);
        }

        try {
            Statement st = ConnectionUtils.getStatement();
            int userId = ConnectionUtils.getUserId();
            final String url = "jdbc:mysql://138.68.102.13:3306/TFGRIEGOS";
            final String user = "prueba";
            final String pass = "irrigadino";
            Connection con = DriverManager.getConnection(url, user, pass);
            String sql = "select * from IRRIGATIONMOMENTDAY where id_irrigation=" + riego.getId();
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Date moment = rs.getDate(3);

                Calendar cal = Calendar.getInstance();
                cal.setTime(moment);

                horaTv.setText(cal.get(Calendar.HOUR_OF_DAY));
                minutoTv.setText(cal.get(Calendar.MINUTE));
                duracionTv.setText(rs.getInt(4));
            }
        }catch(Exception e){
            e.printStackTrace();
        }

        Button buttonCancelar = (Button) findViewById(R.id.button10);
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
                Intent intent = new Intent(DisplayAllDaysActivity.this, DisplayAllDaysActivity.class);
                intent.putExtra("riego", riego);
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
