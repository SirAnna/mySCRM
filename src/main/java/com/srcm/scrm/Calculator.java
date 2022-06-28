package com.srcm.scrm;

import java.sql.ResultSet;

public class Calculator {
    public static void Calc(String vip_id,double trans,String trans_date){
        try{
            ResultSet rs=sqlConnector.sql.stat.executeQuery(
                    "select vip_birth from vips where vip_id= "+vip_id
            );
            rs.next();
            String[] transDates=trans_date.split("-");
            String[] vip_birth=rs.getString(1).split("-");
            boolean is_birth=(transDates[1].equals(vip_birth[1])&&transDates[2].equals(vip_birth[2]));
            rs=sqlConnector.sql.stat.executeQuery(
                    "select m,n,rule_id from points_rules_log where rule_id =(" +
                            "    select max(rule_id) from  points_rules_log" +
                            "    )"
            );
            rs.next();
            int p=Integer.parseInt(is_birth?rs.getString(2):rs.getString(1));
            int dPoints= (int)Math.round(trans*p);
            sqlConnector.sql.stat.executeUpdate(
              "insert into vip_ledger values(DEFAULT,"+vip_id+","+dPoints+",CURDATE())"
            );
            sqlConnector.sql.stat.executeUpdate(
                    "update vips set vip_points=vip_points+"+dPoints+" where vip_id = "+vip_id
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
