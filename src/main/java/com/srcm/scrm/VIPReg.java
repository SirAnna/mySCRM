package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.myUser;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "VIPReg", value = "/VIPReg")
public class VIPReg extends HttpServlet {
    static class Res{
        public short flag=0;
        public String err=null;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        myUser u=new myUser(request);
        Res r= new Res();
        try {
            String val=u.Value();
            sqlConnector.sql.stat.executeUpdate(
                    "insert into vips values ("+val+")"
            );
        }catch (SQLException e){
            e.printStackTrace();
            r.flag=1;
            r.err=e.toString();
        }
        ObjectMapper objectMapper=new ObjectMapper();
        response.getWriter().write( objectMapper.writeValueAsString(r));
    }
}
