package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.CrossHost;
import com.srcm.scrm.sqlConnector;

import java.io.IOException;
import java.sql.ResultSet;

@WebServlet(name = "vExist", value = "/vExist")
public class vExist extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    static class res{
        public short flag=0;//0=>exist,1=>none,2=>deleted,3=>other err
        public String err=null;
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        String v_id=request.getParameter("vip_id");
        res r=new res();
        try{
            ResultSet rs=sqlConnector.sql.stat.executeQuery(
                    "select vip_deleted from vips where vip_id LIKE "+v_id
            );
            if(rs.next())
                r.flag =(short)(rs.getString(1).equals("0")?0:2);
            else r.flag=1;
        }catch(Exception e){
            e.printStackTrace();
            r.flag=3;
            r.err=e.toString();
        }
        ObjectMapper objectMapper=new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(r));
    }
}
