package com.srcm.scrm;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.CrossHost;
import com.srcm.scrm.sqlConnector;

import java.io.IOException;

@WebServlet(name = "vDel", value = "/vDel")
public class vDel extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        String id=request.getParameter("vip_id");
        try {
            sqlConnector.sql.stat.executeUpdate(
                    "update vips set vip_deleted=1 where vip_id ="+id
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
