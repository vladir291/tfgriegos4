package com.ruben.connecttomysql.irrigation.allDays;

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

public class SeveralTimesScheduleUtils {

    public static List<String> compruebaErrores(Activity activity) {
        List<String> errores;
        EditText nombre,duracion, inicio, fin, horas, minutos;
        String nombreStr;
        Timestamp startDateDt;
        Timestamp endDateDt;
        Integer hoursInt;
        Integer minutesInt;
        Integer duracionInt;

        errores = new ArrayList<String>();
        nombre = (EditText) activity.findViewById(R.id.editText1);
        duracion = (EditText) activity.findViewById(R.id.editText2);
        inicio = (EditText) activity.findViewById(R.id.editText6);
        fin = (EditText) activity.findViewById(R.id.editText7);
        horas = (EditText) activity.findViewById(R.id.editText8);
        minutos = (EditText) activity.findViewById(R.id.editText9);


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
        if(!horas.getText().toString().isEmpty()){
            hoursInt = Integer.parseInt(horas.getText().toString());
            if(hoursInt <0 || hoursInt >23 ){
                errores.add("No es una hora válida (0-23)");
            }

        }
        if(!minutos.getText().toString().isEmpty()){
            minutesInt = Integer.parseInt(minutos.getText().toString());
            if(minutesInt <0 || minutesInt >59 ){
                errores.add("No son minutos validos (0-59)");
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
