package com.ruben.connecttomysql;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.ruben.connecttomysql.plot.DisplayPlotActivity;
import com.ruben.connecttomysql.model.Plot;
import com.ruben.connecttomysql.plot.PlotData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class HistoricoActivity extends AppCompatActivity {
    Integer plotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);


        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<PlotData> plotDatas;
        Statement st;


        plotDatas = new ArrayList<PlotData>();

        //Obtenemos el plot pasado por parametro
        Plot plot =(Plot) getIntent().getSerializableExtra("plot");
        plotId= plot.getId();

        st = ConnectionUtils.getStatement();

        try{
            int userId = ConnectionUtils.getUserId();
            String sql = "select * from PLOTDATA where id_plot="+plotId;
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            //rs.next();

            while(rs.next()) {
                Integer id = rs.getInt(1);
                Timestamp measuringMoment = rs.getTimestamp(3);
                Double soilMoisture = rs.getDouble(4);
                Double humidity = rs.getDouble(5);
                Double temperature = rs.getDouble(6);
                plotId = rs.getInt(7);

                PlotData plotData = new PlotData(id,measuringMoment,soilMoisture,humidity,temperature,plotId);
                //Log.d("Debug", "Temp: "+plotData.getMeasuringMoment());


                plotDatas.add(plotData);

            }



        }catch(Exception e){
            e.printStackTrace();
        }




        LineChart chart = (LineChart) findViewById(R.id.chart);



        ArrayList<Entry> entries1 = new ArrayList<>();


        ArrayList<String> labels1 = new ArrayList<String>();
        //Nos recorremos los plotData que hemos obtenido en la consulta

        Integer contador=0;
        for(PlotData pd: plotDatas){
            entries1.add(new Entry(pd.getSoilMoisture().floatValue(), contador));
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(pd.getMeasuringMoment());   // assigns calendar to given date

            Integer hora = calendar.get(Calendar.HOUR_OF_DAY); // gets hour in 24h format
            Integer minuto = calendar.get(Calendar.MINUTE);

            labels1.add(pd.getMeasuringMoment().toString());
            contador=contador+1;
        }

        if(labels1.size()!=0 && entries1.size()!=0){
        LineDataSet dataset = new LineDataSet(entries1,"Temperatura");
        LineData data = new LineData(labels1, dataset);
        chart.setData(data);
        chart.setDescription("Tabla de temperaturas");
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);
        chart.animateY(2000);
        }
        Button buttonBorrarHistorico= (Button) findViewById(R.id.borrarHistoricoBt);
        buttonBorrarHistorico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    Class.forName("com.mysql.jdbc.Driver");
                    Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(),ConnectionUtils.getUser(),ConnectionUtils.getPass());

                    runOnUiThread(new Runnable() {
                        public void run() {
                            // some code #3 (Write your code here to run in UI thread)

                            //Obtenemos el texto de los campos que ha introducido el usuario

                        }
                    });

                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                    String sql = "DELETE FROM PLOTDATA WHERE id_plot="+plotId;

                    //Realizamos la consulta contra la base de datos
                    //Borramos
                    st.executeUpdate(sql);


                    Intent intent = new Intent (HistoricoActivity.this, DisplayPlotActivity.class);
                    intent.putExtra("plot", plot);
                    startActivity(intent);




                }catch(Exception e){
                    e.printStackTrace();
                }


            }
        });

    }








}
