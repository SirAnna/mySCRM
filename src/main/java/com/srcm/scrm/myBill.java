package com.srcm.scrm;

import jakarta.servlet.http.HttpServletRequest;

import java.sql.ResultSet;

public class myBill {
    public String bill_id;
    public String vip_id;
    public String transaction;
    public String pay;
    public String commodity;
    public String trans_date;
    public short pay_type;
    public short is_accounted;
    static String c(String s){
        return "'"+s+"',";
    }
    static String c(int s){
        return "'"+s+"',";
    }
    public myBill(HttpServletRequest request){
        bill_id=request.getParameter("bill_id");
        vip_id=request.getParameter("vip_id");
        transaction=request.getParameter("transaction");
        pay=request.getParameter("pay");
        commodity=request.getParameter("commodity");
        trans_date=request.getParameter("trans_date");
        pay_type=Short.parseShort(request.getParameter("pay_type"));
        is_accounted=Short.parseShort(request.getParameter("is_accounted"));
    }
    public myBill(ResultSet rs){
        try {
            bill_id = rs.getString(1);
            vip_id = rs.getString(2);
            transaction = rs.getString(3);
            pay = rs.getString(4);
            commodity = rs.getString(5);
            trans_date = rs.getString(6);
            pay_type = Short.parseShort(rs.getString(7));
            is_accounted = Short.parseShort(rs.getString(8));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public String insValue(){
        return "DEFAULT,"+c(vip_id)+c(transaction)+c(pay)
                +c(commodity)+c(trans_date)+c(pay_type)+"'"+is_accounted+"'";
    }
    public String updValue(){
        return "vip_id="+c(vip_id)+"transaction="+c(transaction)
                +"pay="+c(pay)+"commodity="+c(commodity)
                +"trans_date="+c(trans_date)+"pay_type="+c(pay_type)
                +"is_accounted='"+is_accounted+"'";
    }
}
