package com.ruben.connecttomysql.plot;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import com.ruben.connecttomysql.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 20/12/2016.
 */

public class PlotUtils {


    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText nombre;
        String nombreStr;

        errores = new ArrayList<String>();
        nombre = (EditText) activity.findViewById(R.id.editTextNombrePlot);

        nombreStr = nombre.getText().toString();

        if (nombreStr.matches("")) {
            errores.add("Debes introducir un nombre");
        }
        return errores;
    }

    public static void visualizaErrores(Activity activity, List<String> errores) {


        for(String error: errores){
            Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }


    }
}
