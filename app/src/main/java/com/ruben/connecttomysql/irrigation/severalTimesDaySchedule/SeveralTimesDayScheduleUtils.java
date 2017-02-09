package com.ruben.connecttomysql.irrigation.severalTimesDaySchedule;

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

public class SeveralTimesDayScheduleUtils {

    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText nombre, inicio, fin;
        String nombreStr;
        Timestamp startDateDt;
        Timestamp endDateDt;

        errores = new ArrayList<String>();
        nombre = (EditText) activity.findViewById(R.id.editText1);
        inicio = (EditText) activity.findViewById(R.id.editText10);
        fin = (EditText) activity.findViewById(R.id.editText11);


        nombreStr = nombre.getText().toString();


        //Comprobamos las restricciones del dominio
        if (nombreStr.matches("")) {
            errores.add("Debes introducir un nombre");
        }
        if(!inicio.getText().toString().isEmpty()){
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy");

            Date parsedTimeStamp = null;
            try {
                parsedTimeStamp = dateFormat.parse(inicio.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startDateDt = new Timestamp(parsedTimeStamp.getTime());

            Timestamp now = new Timestamp(System.currentTimeMillis());
            if(startDateDt.before(now)){
                errores.add("La fecha de inicio no puede ser anterior a la actual");
            }

        }
        if(!fin.getText().toString().isEmpty()){

            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "dd-MM-yyyy");

            Date parsedTimeStamp = null;
            try {
                parsedTimeStamp = dateFormat.parse(fin.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            endDateDt = new Timestamp(parsedTimeStamp.getTime());



            parsedTimeStamp = null;
            try {
                parsedTimeStamp = dateFormat.parse(inicio.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            startDateDt = new Timestamp(parsedTimeStamp.getTime());

            if(endDateDt.before(startDateDt)){
                errores.add("La fecha de fin no puede ser anterior a la de inicio");
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
