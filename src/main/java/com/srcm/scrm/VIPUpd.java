package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.CrossHost;
import com.srcm.scrm.sqlConnector;
import com.srcm.scrm.myUser;

import java.io.IOException;

@WebServlet(name = "VIPUpd", value = "/VIPUpd")
public class VIPUpd extends HttpServlet {
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
        myUser user=new myUser(request);
        Res r=new Res();
        try {
            sqlConnector.sql.stat.executeUpdate(
                    "update vips set "+user.updValue()+" where vip_id LIKE "+user.vip_id
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
