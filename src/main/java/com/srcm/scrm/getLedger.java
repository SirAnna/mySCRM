package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.FileDescriptor;
import java.io.IOException;
import java.sql.ResultSet;

@WebServlet(name = "getLedger", value = "/getLedger")
public class getLedger extends HttpServlet {
    static class myLedger{
        public String led_id;
        public int deta_points;
        public String date_get;
        public myLedger(ResultSet rs){
            try {
                led_id=rs.getString(1);
                deta_points=Integer.parseInt(rs.getString(2));
                date_get=rs.getString(3);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    static class Ledgers{
        public myLedger[] ledgers;
        public Ledgers(ResultSet rs){
            try{
                rs.last();
                ledgers=new myLedger[rs.getRow()];
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                    ledgers[i++]=new myLedger(rs);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        String vip_id=request.getParameter("vip_id");
        try{
            ResultSet rs =sqlConnector.sql.stat.executeQuery(
              "select led_id,deta_points,date_get from vip_ledger where vip_id="+vip_id
            );
            Ledgers led=new Ledgers(rs);
            ObjectMapper objectMapper=new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(led));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
