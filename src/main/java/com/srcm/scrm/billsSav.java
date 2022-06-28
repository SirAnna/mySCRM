package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.CrossHost;
import com.srcm.scrm.sqlConnector;

import java.io.IOException;
import java.sql.ResultSet;

@WebServlet(name = "billsSav", value = "/billsSav")
public class billsSav extends HttpServlet {
    static class res{
        public int flag;
        public String err=null;
        public String bill_id=null;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        CrossHost.set(response);
        myBill bill=new myBill(request);
        res r=new res();
        try{
            sqlConnector.sql.stat.executeUpdate(
                    "insert into vip_bills values ("+bill.insValue()+")"
            );
            ResultSet rs=sqlConnector.sql.stat.executeQuery(
                    "select max(bill_id) from vip_bills"
            );
            rs.next();
            r.flag=0;
            r.bill_id=rs.getString(1);
            if(bill.is_accounted==1)
                Calculator.Calc(
                        bill.vip_id,
                        Double.parseDouble(bill.transaction),
                        bill.trans_date
                );
        }catch (Exception e){
            e.printStackTrace();
            r.flag=1;
            r.err=e.toString();
        }
        ObjectMapper objectMapper=new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(r));
    }
}
