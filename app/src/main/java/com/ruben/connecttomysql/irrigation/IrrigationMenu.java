package com.ruben.connecttomysql.irrigation;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ruben.connecttomysql.irrigation.allDays.CreateAllDaysActivity;
import com.ruben.connecttomysql.irrigation.manual.CreateManualActivity;
import com.ruben.connecttomysql.irrigation.severalTimes.CreateSeveralTimesActivity;
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
        Button buttonAllDays = (Button) findViewById(R.id.button6);
        Button buttonSeveralTimes = (Button) findViewById(R.id.button8);

        Plot plot =(Plot) getIntent().getSerializableExtra("plot");

        buttonManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (IrrigationMenu.this, CreateManualActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

        buttonAllDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (IrrigationMenu.this, CreateAllDaysActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

        buttonSeveralTimes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (IrrigationMenu.this, CreateSeveralTimesActivity.class);
                intent.putExtra("plot", plot);
                startActivity(intent);

            }
        });

    }




}
