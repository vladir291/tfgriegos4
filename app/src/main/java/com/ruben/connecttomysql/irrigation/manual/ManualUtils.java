package com.ruben.connecttomysql.irrigation.manual;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import com.ruben.connecttomysql.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 20/12/2016.
 */

public class ManualUtils {

    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText nombre,duracion;
        String nombreStr;
        Integer duracionInt;

        errores = new ArrayList<String>();
        nombre = (EditText) activity.findViewById(R.id.editText1);
        duracion = (EditText) activity.findViewById(R.id.editText2);

        nombreStr = nombre.getText().toString();


        //Comprobamos las restricciones del dominio
        if (nombreStr.matches("")) {
            errores.add("Debes introducir un nombre");
        }
        if(!duracion.getText().toString().isEmpty()){
            duracionInt = Integer.parseInt(duracion.getText().toString());
            if(duracionInt <0 || duracionInt >10 ){
                errores.add("La duracion debe estar entre 0 y 10 minutos");
            }

        }

        return errores;
    }

    public static void visualizaErrores(Activity activity, List<String> errores) {


        for(String error: errores){
            Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }


    }
}
