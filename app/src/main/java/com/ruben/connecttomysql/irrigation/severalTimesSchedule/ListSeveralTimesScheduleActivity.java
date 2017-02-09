package com.ruben.connecttomysql.irrigation.severalTimesSchedule;

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
import com.ruben.connecttomysql.model.Plot;
import com.ruben.connecttomysql.model.SeveralTimesSchedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class ListSeveralTimesScheduleActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private String name;
    private Timestamp cancelMoment;
    private Timestamp startDate;
    private Timestamp endDate;
    private Integer hours;
    private Integer minutes;
    private Integer duration;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_several_times_schedule);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<SeveralTimesSchedule> riegos;
        Statement st,st2;


        riegos = new ArrayList<SeveralTimesSchedule>();

        Plot plot =(Plot) getIntent().getSerializableExtra("plot");


        listView =  (ListView) findViewById(R.id.listView);

        st = ConnectionUtils.getStatement();

        try{
            int userId = ConnectionUtils.getUserId();
            final String url= "jdbc:mysql://138.68.102.13:3306/TFGRIEGOS";
            final String user = "prueba";
            final String pass = "irrigadino";
            Connection con = DriverManager.getConnection(url,user,pass);
            st2 = con.createStatement();
            String sql = "select * from IRRIGATION where id_plot="+plot.getId();
            //Realizamos la consulta contra la base de datos
            final ResultSet rs = st.executeQuery(sql);

            //rs.next();

            while(rs.next()) {
                SeveralTimesSchedule riego;
                id = rs.getInt(1);
                name = rs.getString(5);
                cancelMoment = rs.getTimestamp(3);
                String sql2 = "select * from SEVERALTIMESSCHEDULE where id_irrigation="+id;
                final ResultSet rs2 = st2.executeQuery(sql2);

                while(rs2.next()) {
                    startDate = rs2.getTimestamp(3);
                    endDate = rs2.getTimestamp(4);
                    hours = rs2.getInt(5);
                    minutes = rs2.getInt(6);
                    duration = rs2.getInt(7);

                    riego = new SeveralTimesSchedule(id, name, cancelMoment, startDate, endDate, hours, minutes, duration);



                    riegos.add(riego);
                }
            }



        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<SeveralTimesSchedule>(listView.getContext(),android.R.layout.simple_list_item_1,riegos);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                SeveralTimesSchedule riego =(SeveralTimesSchedule) adapter.getItem(position);

                Intent intent = new Intent(ListSeveralTimesScheduleActivity.this, DisplaySeveralTimesScheduleActivity.class);
                intent.putExtra("riego", riego);
                startActivity(intent);
            }
        });



        Button buttonCreateManual = (Button) findViewById(R.id.button4);

        buttonCreateManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListSeveralTimesScheduleActivity.this, CreateSeveralTimesScheduleActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

    }




}
