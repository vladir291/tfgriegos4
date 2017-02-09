package com.ruben.connecttomysql.farm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ruben.connecttomysql.model.Farm;
import com.ruben.connecttomysql.plot.ListPlotsActivity;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.WebViewClimaActivity;

public class DisplayFarmActivity extends AppCompatActivity {
    // Declaramos los elementos
    private TextView nombreTv;
    private TextView localizacionTv;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_farm);
        //Instanciamos los elementos
        nombreTv = (TextView) findViewById(R.id.textView8);
        localizacionTv = (TextView) findViewById(R.id.textView9);

        //Obtenemos la farm pasada por parametro
        Farm farm =(Farm) getIntent().getSerializableExtra("farm");

        //Cargamos los valores a mostrar

        nombreTv.setText(farm.getName());
        localizacionTv.setText(farm.getLocation().toString());

        Button buttonEditarFarm = (Button) findViewById(R.id.button);


        buttonEditarFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayFarmActivity.this, EditFarmActivity.class);
                intent.putExtra("farm", farm);
                startActivity(intent);
            }
        });



        Button buttonListarPlots= (Button) findViewById(R.id.button5);


        buttonListarPlots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DisplayFarmActivity.this, ListPlotsActivity.class);
                intent.putExtra("idFarm", farm.getId());
                startActivity(intent);
            }
        });

        Button buttonVerClima= (Button) findViewById(R.id.verClimaBt);
        buttonVerClima.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(DisplayFarmActivity.this, WebViewClimaActivity.class);
                intent.putExtra("busqueda", farm.getLocation());
                startActivity(intent);
            }
        });


    }



}
