package com.srcm.scrm;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.ResultSet;

public class myRule {
    public String rule_id;
    public int m;
    public int n;
    public String changer;
    public String change_date;

    public static String g(String s){
        return "'"+s+"',";
    }
    public myRule(HttpServletRequest request){
        rule_id="DEFAULT";
        m=Integer.parseInt(request.getParameter("m"));
        n=Integer.parseInt(request.getParameter("n"));
        changer=request.getParameter("changer");
        change_date="CURDATE()";
    }
    public myRule(ResultSet rs){
        try {
            rule_id = rs.getString(1);
            m=Integer.parseInt(rs.getString(2));
            n=Integer.parseInt(rs.getString(3));
            changer=rs.getString(4);
            change_date=rs.getString(5);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String Value(){
        return rule_id+","+m+","+n+","+g(changer)+change_date;
    }
}
