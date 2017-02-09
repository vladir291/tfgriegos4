package com.ruben.connecttomysql.irrigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ruben.connecttomysql.irrigation.manual.ListManualActivity;
import com.ruben.connecttomysql.irrigation.severalTimesDaySchedule.ListSeveralTimesDayScheduleActivity;
import com.ruben.connecttomysql.irrigation.severalTimesSchedule.ListSeveralTimesScheduleActivity;
import com.ruben.connecttomysql.model.Plot;
import com.ruben.connecttomysql.R;

public class IrrigationMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_irrigation_menu);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }



        Button buttonManual = (Button) findViewById(R.id.button5);
        Button buttonSeveral1 = (Button) findViewById(R.id.button6);
        Button buttonSeveral2 = (Button) findViewById(R.id.button8);

        Plot plot =(Plot) getIntent().getSerializableExtra("plot");

        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (IrrigationMenu.this, ListManualActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

        buttonSeveral1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (IrrigationMenu.this, ListSeveralTimesScheduleActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

        buttonSeveral2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (IrrigationMenu.this, ListSeveralTimesDayScheduleActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

    }




}
