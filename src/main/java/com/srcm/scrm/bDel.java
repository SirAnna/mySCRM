package com.srcm.scrm;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.CrossHost;
import com.srcm.scrm.sqlConnector;

import java.io.IOException;

@WebServlet(name = "bDel", value = "/bDel")
public class bDel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        String bill_id=request.getParameter("bill_id");
        try{
            sqlConnector.sql.stat.executeUpdate(
                    "delete from vip_bills where bill_id = "+bill_id
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
