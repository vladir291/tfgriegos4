package com.ruben.connecttomysql.farm;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ruben.connecttomysql.ConnectionUtils;
import com.ruben.connecttomysql.R;
import com.ruben.connecttomysql.model.Farm;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ListFarmsActivity extends AppCompatActivity {
    private ListView listView;
    private Integer id;
    private String name;
    private String localizacion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_farms);

        // Permitimos que se puedan realizar peticiones en la ui de la activity
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<Farm> farms;
        Statement st;


        farms = new ArrayList<Farm>();


        listView =  (ListView) findViewById(R.id.listView);

        st = ConnectionUtils.getStatement();

        try{
                int userId = ConnectionUtils.getUserId();
                String sql = "select * from FARM where id_propietary="+userId;
                //Realizamos la consulta contra la base de datos
                final ResultSet rs = st.executeQuery(sql);

                //rs.next();

                while(rs.next()) {
                    Farm farm;
                    id = rs.getInt(1);
                    name = rs.getString(3);
                    localizacion = rs.getString(4);

                    farm = new Farm(id,name,localizacion);
                    //Log.d("Debug", "Nombre: " + name +" longitud: "+longitude.toString()+" latitude: "+latitude.toString());


                    farms.add(farm);

                }



        }catch(Exception e){
            e.printStackTrace();
        }



        ArrayAdapter adapter= new ArrayAdapter<Farm>(listView.getContext(),android.R.layout.simple_list_item_1,farms);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {


                Farm farm =(Farm) adapter.getItem(position);

                Intent intent = new Intent(ListFarmsActivity.this, DisplayFarmActivity.class);
                intent.putExtra("farm", farm);
                startActivity(intent);
            }
        });


/*
        Button buttonCreateFarm = (Button) findViewById(R.id.button4);

        buttonCreateFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent (ListFarmsActivity.this, CreateFarmActivity.class);
                startActivity(intent);

            }
        });
*/


        /*
        // Realizamos el evento para borrar un elemento al realizar una pulsacion larga
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            public boolean onItemLongClick(AdapterView<?> arg0, View arg1,int arg2, long arg3)
            {
                ListView list1 = listView;
                final String selectedValue = list1.getItemAtPosition(arg2).toString();
                Farm farm =(Farm) list1.getItemAtPosition(arg2);
                Integer farmId= farm.getId();
                AlertDialog.Builder alertDialog = new  AlertDialog.Builder(ListFarmsActivity.this);
                alertDialog.setTitle("Borrar");
                alertDialog.setMessage(selectedValue);
                alertDialog.setNegativeButton("Borrar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
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

                                String sql = "DELETE FROM FARM WHERE id="+farmId;

                                //Realizamos la consulta contra la base de datos
                                //Borramos
                                st.executeUpdate(sql);


                            Intent intent = new Intent (ListFarmsActivity.this, ListFarmsActivity.class);
                            startActivity(intent);




                        }catch(Exception e){
                            e.printStackTrace();
                        }


                    } });
                alertDialog.setPositiveButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // alertDialog.dismiss();
                    } });

                alertDialog.show();
                return true;
            }
        });

*/
    }




}
