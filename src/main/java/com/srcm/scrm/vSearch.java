package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.myUser;
import com.srcm.scrm.sqlConnector;
import com.srcm.scrm.CrossHost;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(name = "vSearch", value = "/vSearch")
public class vSearch extends HttpServlet {
    static class uGroup{
        public myUser[] Users;
        public uGroup(ResultSet rs){
            try {
                rs.last();
                Users = new myUser[rs.getRow()];
                int i=0;
                rs.beforeFirst();
                while(rs.next())
                    Users[i++]=new myUser(rs);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
    String g(String s){
        return "'%"+s+"%'";
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        String spp=request.getParameter("sPara");
        String sqlWhere=null;
        if(spp==null||spp.length()==0) {
            sqlWhere=" vip_deleted = 0 ";
        }
        else {
            String[] sPara = spp.split(" ");
            String left = "concat(" +
                    "vip_id," +
                    "vip_name," +
                    "vip_birth," +
                    "vip_phone_number," +
                    "vip_email," +
                    "vip_address," +
                    "vip_car_license" +
                    ") like ";
            StringBuilder inWhere = new StringBuilder();
            for (String sp : sPara)
                inWhere.append(left).append(g(sp)).append(" and ");
            inWhere.append(" vip_deleted = 0 ");
            sqlWhere = inWhere.toString();
        }
        try{
            ResultSet rs=sqlConnector.sql.stat.executeQuery(
                    "select * from vips where "+sqlWhere
            );
            uGroup us= new uGroup(rs);
            ObjectMapper objectMapper=new ObjectMapper();
            String data=objectMapper.writeValueAsString(us);
            response.getWriter().write(data);
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
