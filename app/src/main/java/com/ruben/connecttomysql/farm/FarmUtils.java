package com.ruben.connecttomysql.farm;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import com.ruben.connecttomysql.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 20/12/2016.
 */

public class FarmUtils {

    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText nombre,localizacion;
        String nombreStr,localizacionStr;

        errores = new ArrayList<String>();
        nombre = (EditText) activity.findViewById(R.id.editText3);
        localizacion = (EditText) activity.findViewById(R.id.editText4);

        nombreStr = nombre.getText().toString();
        localizacionStr = localizacion.getText().toString();

        //Comprobamos las restricciones del dominio
        if (nombreStr.matches("")) {
            errores.add("Debes introducir un nombre");
        }
        if (localizacionStr.matches("")) {
            errores.add("Debes introducir una localización");
        }


        return errores;
    }

    public static void visualizaErrores(Activity activity, List<String> errores) {


        for(String error: errores){
            Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }


    }
}
