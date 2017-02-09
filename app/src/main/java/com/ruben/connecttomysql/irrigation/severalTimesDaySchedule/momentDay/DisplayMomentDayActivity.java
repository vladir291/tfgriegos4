package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule.momentDay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.model.IrrigationMomentDay;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DisplayMomentDayActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView momentTv;
    private TextView durationTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_moment_day);
        //Instanciamos los elementos
        momentTv = (TextView) findViewById(R.id.textView29);
        durationTv = (TextView) findViewById(R.id.textView30);

        //Obtenemos la farm pasada por parametro
        IrrigationMomentDay riego =(IrrigationMomentDay) getIntent().getSerializableExtra("riego");

        //Cargamos los valores a mostrar

        Calendar calendar;
        calendar = Calendar.getInstance();


        calendar.setTime(riego.getIrrigationMoment());
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");

        String cancelEndDateStr = sdf.format(calendar.getTime());

        momentTv.setText(cancelEndDateStr);



        durationTv.setText(riego.getDuration().toString());

       /* Button buttonCancelar = (Button) findViewById(R.id.button11);
        buttonCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Date now = new Date(System.currentTimeMillis()-100);
                riego.setCancelMoment(now);

                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(),ConnectionUtils.getUser(),ConnectionUtils.getPass());
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    //Log.d("Debug", "Antes de la consulta el usuario: " + nomEditText);
                    //Log.d("Debug", "Nombre: " +nombre +" latitud: "+latitud+ " longitud: "+longitud+",farmId:"+farmId);
                    String sql = "update IRRIGATION set cancelMoment ='"+now +"'where id="+riego.getId();
                    //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);

                }catch(Exception e){
                    e.printStackTrace();
                }
            }
        });*/

      /*  Button buttonMomentDay= (Button) findViewById(R.id.button12);

        buttonMomentDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayMomentDayActivity.this, ListMomentDayActivity.class);
                intent.putExtra("idIrrigation", riego.getId());
                startActivity(intent);
            }
        });*/
    }



}
