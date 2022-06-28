package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import java.io.IOException;
import java.sql.ResultSet;

@WebServlet(name = "getRules", value = "/getRules")
public class getRules extends HttpServlet {
    static class myRules{
        public myRule[] rules;
        public myRules(ResultSet rs){
            try{
                rs.last();
                rules=new myRule[rs.getRow()];
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                    rules[i++]=new myRule(rs);
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
        try{
            ResultSet rs=sqlConnector.sql.stat.executeQuery(
              "select *from points_rules_log ORDER BY rule_id DESC"
            );
            myRules rules=new myRules(rs);
            ObjectMapper objectMapper=new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(rules));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
