package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.CrossHost;
import com.srcm.scrm.sqlConnector;
import com.srcm.scrm.myBill;

import java.io.IOException;

@WebServlet(name = "billsUpd", value = "/billsUpd")
public class billsUpd extends HttpServlet {
    static class res{
        public int flag;
        public String err=null;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        myBill bill=new myBill(request);
        res r=new res();
        String updVal=bill.updValue();
        try{
            sqlConnector.sql.stat.executeUpdate(
              "update vip_bills set "+updVal
                      +" where bill_id LIKE "+bill.bill_id
            );
            r.flag=0;
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
