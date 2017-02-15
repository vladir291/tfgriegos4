package com.ruben.connecttomysql.irrigation.manual;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.model.Irrigation;
import com.ruben.connecttomysql.model.IrrigationMomentDay;
import com.ruben.connecttomysql.model.ManualForm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DisplayManualActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView nombreTv;
    private TextView cancelMomentTv;
    private TextView startDateTv;
    private TextView duracionTv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_manual);
        //Instanciamos los elementos
        nombreTv = (TextView) findViewById(R.id.textView29);
        cancelMomentTv = (TextView) findViewById(R.id.textView30);
        startDateTv = (TextView) findViewById(R.id.textView31);
        duracionTv = (TextView) findViewById(R.id.textView32);

        //Obtenemos la farm pasada por parametro
        Irrigation riego =(Irrigation) getIntent().getSerializableExtra("manual");

        //Cargamos los valores a mostrar

        nombreTv.setText(riego.getName());
        if(riego.getCancelMoment()!= null) {
            Calendar calendar;
            calendar= Calendar.getInstance();;

            calendar.setTime(riego.getCancelMoment());
            SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

            String cancelMomentStr = sdf.format(calendar.getTime());



            cancelMomentTv.setText(cancelMomentStr);
        }

        Calendar calendar2;
        calendar2= Calendar.getInstance();;

        calendar2.setTime(riego.getStartDate());
        SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String startDateStr = sdf2.format(calendar2.getTime());

        startDateTv.setText(startDateStr);

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
                duracionTv.setText(rs.getInt(4));
            }
        }catch(Exception e){
            e.printStackTrace();
        }




        Button buttonCancelar = (Button) findViewById(R.id.button9);

        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Timestamp now = new Timestamp(System.currentTimeMillis() - 100);
                riego.setCancelMoment(now);

                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(), ConnectionUtils.getUser(), ConnectionUtils.getPass());
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud+",farmId:"+farmId);

                    java.sql.Timestamp fechaCancelDB = new java.sql.Timestamp(now.getTime());

                    String sql = "update IRRIGATION set cancelMoment ='" + fechaCancelDB + "' where id=" + riego.getId();
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(DisplayManualActivity.this, DisplayManualActivity.class);
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
