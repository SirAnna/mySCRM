package com.srcm.scrm;

import java.sql.*;

public class sqlConnector {
    final static String port="49153";
    public Connection con=null;
    public Statement stat=null;
    public sqlConnector(){
        String JDBC_DRIVER="com.mysql.cj.jdbc.Driver";
        String USER="root";
        String PASS="18978909834";
        String url="jdbc:mysql://localhost:"+port+
                "/scrmBase?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        try{
            Class.forName(JDBC_DRIVER);
            con =DriverManager.getConnection(url,USER,PASS);
            stat=con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
            System.out.println("sql connects successfully");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static sqlConnector sql=new sqlConnector();
}
