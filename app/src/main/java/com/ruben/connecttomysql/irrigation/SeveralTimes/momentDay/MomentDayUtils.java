package com.ruben.connecttomysql.irrigation.severalTimes.momentDay;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import com.ruben.connecttomysql.R;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ruben on 20/12/2016.
 */

public class MomentDayUtils {

    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText fecha, duracion;
        Integer duracionInt;

        errores = new ArrayList<String>();
        fecha = (EditText) activity.findViewById(R.id.editText1);
        duracion = (EditText) activity.findViewById(R.id.editText10);


        //Comprobamos las restricciones del dominio

        if(!fecha.getText().toString().isEmpty()){

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy hh:mm:ss");

            Date parsedTimeStamp = null;
            try {
                parsedTimeStamp = dateFormat.parse(fecha.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Timestamp momentDt = new Timestamp(parsedTimeStamp.getTime());

            Timestamp now = new Timestamp(System.currentTimeMillis());
            if(momentDt.before(now)){
                errores.add("La fecha no puede ser anterior a la actual");
            }

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
