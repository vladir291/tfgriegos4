package com.ruben.connecttomysql.climatologicalProbe;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.farm.ListFarmsActivity;
import com.ruben.connecttomysql.model.ClimatologicalProbe;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class EditClimatologicalProbeActivity extends AppCompatActivity {
    // Declaramos los elementos
    private EditText editTextSoilLower;
    private EditText editTextSoilUpper;
    private Switch switchActiveClimatologicalProbeSw;
    private Integer id;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_climatological_probe);




        //Instanciamos los elementos
        editTextSoilLower = (EditText) findViewById(R.id.editTextSoilLower);
        editTextSoilUpper = (EditText) findViewById(R.id.editTextSoilUpper);
        switchActiveClimatologicalProbeSw = (Switch) findViewById(R.id.switchActiveClimatologicalProbe2);

        //Obtenemos el climatologicalProbe pasada por parametro
        ClimatologicalProbe climatologicalProbe =(ClimatologicalProbe) getIntent().getSerializableExtra("climatologicalProbe");

        id= climatologicalProbe.getId();

        //Cargamos los valores a mostrar

        editTextSoilLower.setText(climatologicalProbe.getSoilLowerLimit().toString());
        editTextSoilUpper.setText(climatologicalProbe.getSoilUpperLimit().toString());




        if(climatologicalProbe.getActive()==true){
            switchActiveClimatologicalProbeSw.setChecked(true);
        }else{
            switchActiveClimatologicalProbeSw.setChecked(false);
        }



        Button buttonAceptar = (Button) findViewById(R.id.buttonAceptarClimatologicalProbe);
        //Definimos el comportamiento del boton aceptar
        buttonAceptar.setOnClickListener(new View.OnClickListener() {



            @Override
            public void onClick(View view) {

                new EditClimatologicalProbeActivity.EditarClimatologicalProbe().execute();
            }
        });

    }


    // Ejecutamos de forma asincrona, las acciones del boton aceptar
    private class EditarClimatologicalProbe extends AsyncTask<Void,Void,Void> {
        private Double soilMoistureLower;
        private Double soilMoistureUpper;
        private Boolean active;


        @Override
        protected Void doInBackground(Void... voids) {
            List<String> errores;



            errores = new ArrayList<String>();
            try{
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection(ConnectionUtils.getUrl(),ConnectionUtils.getUser(),ConnectionUtils.getPass());

                runOnUiThread(new Runnable() {
                    public void run() {
                        // some code #3 (Write your code here to run in UI thread)

                        //Obtenemos el texto de los campos que ha introducido el usuario

                        if(!editTextSoilLower.getText().toString().isEmpty()) {
                            soilMoistureLower = Double.parseDouble(editTextSoilLower.getText().toString());
                        }else{
                            soilMoistureLower=0.0;
                        }
                        if(!editTextSoilUpper.getText().toString().isEmpty()) {
                            soilMoistureUpper = Double.parseDouble(editTextSoilUpper.getText().toString());
                        }else{
                            soilMoistureUpper=0.0;
                        }



                        if(switchActiveClimatologicalProbeSw.isChecked()==true){
                            active=true;
                        }else{
                            active=false;
                        }


                        //Log.d("Debug", "soil run: " + soilMoistureLower);
                        //Log.d("Debug", "El valor de la contraseña es: " + contraEditText);
                    }
                });

                errores = ClimatologicalProbeUtils.compruebaErrores(EditClimatologicalProbeActivity.this);
                if(errores.size()==0){
                    Statement st = con.createStatement();

                    //Guardamos la conexion que usaremos en la aplicacion
                    ConnectionUtils.setStatement(st);

                String sql = "update CLIMATOLOGICALPROBE set soilMoistureIrrigationLowerLimit="+soilMoistureLower +",soilMoistureIrrigationUpperLimit="+soilMoistureUpper+",active="+active+" where id="+id;



                Log.d("Debug", "Cadena consulta: "+sql);
                //Realizamos la consulta contra la base de datos
                    st.executeUpdate(sql);





               }

            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
        @Override
        protected void onPostExecute(Void result){
            List<String> errores;

            // Comprobamos y visualizamos los errores en caso de que fuera necesario

            errores = ClimatologicalProbeUtils.compruebaErrores(EditClimatologicalProbeActivity.this);
            ClimatologicalProbeUtils.visualizaErrores(EditClimatologicalProbeActivity.this,errores);


            if(errores.size()>0){
                return;
            }else{
                // Si ha ido correctamente lo llevamos a la nueva ventana
                Intent intent = new Intent (EditClimatologicalProbeActivity.this, ListFarmsActivity.class);

                startActivity(intent);
            }




            super.onPostExecute(result);
        }


    }
    }

