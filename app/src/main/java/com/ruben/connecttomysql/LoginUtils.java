package com.ruben.connecttomysql;

import android.app.Activity;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruben on 15/12/2016.
 */

public  class LoginUtils {

    public static List<String> compruebaErroresLogin(Activity activity) {
        List<String> errores;
        EditText nombre, contrasenya;
        String nombreStr, contrasenyaStr;

        errores = new ArrayList<String>();
        nombre = (EditText) activity.findViewById(R.id.editText);
        contrasenya = (EditText) activity.findViewById(R.id.editText2);

        nombreStr = nombre.getText().toString();
        contrasenyaStr = contrasenya.getText().toString();

        if (nombreStr.matches("")) {
            errores.add("Debes introducir un nombre");
        }
        if (contrasenyaStr.matches("")) {
            errores.add("Debes introducir una contraseña");
        }
        return errores;
    }

    public static void visualizaErroresLogin(Activity activity, List<String> errores) {


        for(String error: errores){
            Toast.makeText(activity.getApplicationContext(), error, Toast.LENGTH_SHORT).show();
        }


    }

    public static boolean compruebaUsuarioValido(String nombreEditText, String contrasenyaEditText, String nombreConsulta, String contrasenyaConsulta) {
        boolean result;

        // Pasamos a MD5 la contraseña que ha introducido el usuario
        //contrasenyaEditText = MD5.crypt(contrasenyaEditText);
        //Log.d("Debug", "Contraseña md5: "+contrasenyaEditText);
        //Log.d("Debug", "Contraseña bd: "+contrasenyaConsulta);


        if (nombreEditText.equals(nombreConsulta) && contrasenyaEditText.equals(contrasenyaConsulta)) {
            result = true;
        } else {
            result = false;
        }


        return result;

    }

    public static void visualizaErroresUsuarioValido(Activity activity,boolean bool) {

        if (bool==false) {
            Toast.makeText(activity.getApplicationContext(), "Las credenciales no son válidas", Toast.LENGTH_SHORT).show();
        }

    }



}
