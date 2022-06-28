package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "setRules", value = "/setRules")
public class setRlues extends HttpServlet {
    public class Res {
        public short flag;
        public String err=null;
        Res(){
            flag=0;
        }
        Res(Exception e){
            flag=1;
            err=e.toString();
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        myRule rule=new myRule(request);
        Res r;
        try{
            sqlConnector.sql.stat.executeUpdate(
              "insert into points_rules_log values("+rule.Value()+")"
            );
            r=new Res();
        }catch (Exception e){
            e.printStackTrace();
            r=new Res(e);
        }
        ObjectMapper objectMapper=new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(r));
    }
}
