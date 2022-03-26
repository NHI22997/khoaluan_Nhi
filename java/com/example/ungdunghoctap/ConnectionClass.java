package com.example.ungdunghoctap;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClass {
    String ip = "172.16.0.37";
    String classs = "net.sourceforge.jtds.jdbc.Driver";
    String db = "edusoft";
    String un = "kl";
    String password = "kl123";

    @SuppressLint("NewApi")
    public Connection CONN() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection conn = null;
        String ConnURL;
        try {
            Class.forName(classs);
            ConnURL = "jdbc:jtds:sqlserver://" + ip + ";" + "databaseName=" + db + ";user=" + un + ";password=" + password + ";";
            conn = DriverManager.getConnection(ConnURL);
        }
        catch (SQLException se)
        {
            Log.e("SQL Error: ", se.getMessage());
        }
        catch (ClassNotFoundException e) {
        }
        catch (Exception e) {
            Log.e("Class Error:", e.getMessage());
        }
        return conn;
    }
}
