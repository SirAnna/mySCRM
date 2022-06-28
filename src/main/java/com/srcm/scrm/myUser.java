package com.srcm.scrm;

import jakarta.servlet.http.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class myUser {
    public String vip_id;
    public String vip_name;
    public short vip_sex;
    public String vip_birth;
    public String vip_phone_number;
    public String vip_email;
    public String vip_address;
    public String vip_car_license;
    public int vip_points;
    public short vip_deleted;
    public myUser(HttpServletRequest request){
        vip_id=request.getParameter("vip_id");
        if(vip_id.equals("ignore"))
            vip_id=null;
        vip_name=request.getParameter("vip_name");
        vip_birth=request.getParameter("vip_birth");
        vip_phone_number=request.getParameter("vip_phone_number");
        vip_email=request.getParameter("vip_email");
        vip_address=request.getParameter("vip_address");
        vip_car_license=request.getParameter("vip_car_license");

        String r=null;
        r=request.getParameter("vip_sex");
        if(r!=null)
            vip_sex =Short.parseShort(r);
        else vip_sex=0;
        r=request.getParameter("vip_points");
        if(r!=null)
            vip_points =Integer.parseInt(r);
        else vip_points=0;
        r=request.getParameter("vip_deleted");
        if(r!=null)
            vip_deleted =Short.parseShort(r);
        else vip_deleted=0;
    }
    public myUser(ResultSet rs){
        try {
            vip_id = rs.getString(1);
            vip_name=rs.getString(2);
            vip_sex=Short.parseShort(rs.getString(3));
            vip_birth=rs.getString(4);
            vip_phone_number=rs.getString(5);
            vip_email=rs.getString(6);
            vip_address=rs.getString(7);
            vip_car_license=rs.getString(8);
            vip_points=Integer.parseInt(rs.getString(9));
            vip_deleted=Short.parseShort(rs.getString(10));
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    static String c(String s){
        return "'"+s+"',";
    }
    static String c(int s){
        return "'"+s+"',";
    }
    public String Value(){
        return (vip_id==null?"DEFAULT,":c(vip_id))
                +c(vip_name)+c(vip_sex)+c(vip_birth)+c(vip_phone_number)
                +c(vip_email)+c(vip_address)+c(vip_car_license)+c(vip_points)
                +"'"+vip_deleted+"'";
    }
    public String updValue(){
        return "vip_name="+c(vip_name)
                +"vip_phone_number="+c(vip_phone_number)
                +"vip_email="+c(vip_email)
                +"vip_address="+c(vip_address)
                +"vip_car_license="+c(vip_car_license)
                +"vip_sex="+c(vip_sex)
                +"vip_birth="+"'"+vip_birth+"'";
    }
}
