package com.ruben.connecttomysql.irrigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.irrigation.allDays.DisplayAllDaysActivity;
import com.ruben.connecttomysql.irrigation.manual.DisplayManualActivity;
import com.ruben.connecttomysql.irrigation.severalTimes.DisplaySeveralTimesActivity;
import com.ruben.connecttomysql.model.Irrigation;
import com.ruben.connecttomysql.model.Plot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ListIrrigationActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private String name;
    private Timestamp cancelMoment;
    private Timestamp startDate;
    private Timestamp endDate;
    private String tipoRiego;

    //TODO FALTA CREAR EL layout y revisar los metodos de moment day
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_irrigation);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<Irrigation> riegos;
        Statement st,st2;


        riegos = new ArrayList<Irrigation>();

        Plot plot =(Plot) getIntent().getSerializableExtra("plot");


        listView =  (ListView) findViewById(R.id.listView);

        st = ConnectionUtils.getStatement();

        try{
            int userId = ConnectionUtils.getUserId();
            final String url= "jdbc:mysql://138.68.102.13:3306/TFGRIEGOS";
            final String user = "prueba";
            final String pass = "irrigadino";
            Connection con = DriverManager.getConnection(url,user,pass);
            String sql = "select * from IRRIGATION where id_plot="+plot.getId();
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            //rs.next();

            while(rs.next()) {
                Irrigation riego;
                id = rs.getInt(1);
                name = rs.getString(3);
                cancelMoment = rs.getTimestamp(4);
                startDate = rs.getTimestamp(5);
                endDate = rs.getTimestamp(6);
                tipoRiego = rs.getString(7);

                riego = new Irrigation(id,name,cancelMoment,startDate,endDate,tipoRiego);

                riegos.add(riego);
            }

        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<Irrigation>(listView.getContext(),android.R.layout.simple_list_item_1,riegos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Irrigation riego =(Irrigation) adapter.getItem(position);
                Intent intent;
                switch(riego.getTipoRiego()){
                    case "MANUAL":
                        intent = new Intent(ListIrrigationActivity.this, DisplayManualActivity.class);
                        intent.putExtra("riego", riego);
                        startActivity(intent);

                    case "ALLDAYS":
                        intent = new Intent(ListIrrigationActivity.this, DisplayAllDaysActivity.class);
                        intent.putExtra("riego", riego);
                        startActivity(intent);

                    case "SEVERALTIMES":
                        intent = new Intent(ListIrrigationActivity.this, DisplaySeveralTimesActivity.class);
                        intent.putExtra("riego", riego);
                        startActivity(intent);
                }


            }
        });



        Button buttonCreateIrrigation = (Button) findViewById(R.id.button4);

        buttonCreateIrrigation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListIrrigationActivity.this, IrrigationMenu.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

    }




}
