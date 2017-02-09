package com.ruben.connecttomysql;
import java.sql.Statement;
/**
 * Created by ruben on 19/12/2016.
 */

public class ConnectionUtils {

    private static final String url= "jdbc:mysql://138.68.102.13:3306/TFGRIEGOS";
    private static final String user = "prueba";
    private static final String pass = "irrigadino";
    private static Statement statement;
    private static int userId;



    public static String getUrl() {
        return url;
    }

    public static String getUser() {
        return user;
    }

    public static String getPass() {
        return pass;
    }

    public static Statement getStatement() {
        return statement;
    }

    public static void setStatement(Statement statement) {

        ConnectionUtils.statement = statement;
    }

    public static int getUserId() {
        return userId;
    }

    public static void setUserId(int userId) {
        ConnectionUtils.userId = userId;
    }


}
