package com.srcm.scrm;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.sqlConnector;
import com.srcm.scrm.myBill;
import com.srcm.scrm.CrossHost;

import java.io.IOException;
import java.sql.ResultSet;

@WebServlet(name = "bSearch", value = "/bSearch")
public class bSearch extends HttpServlet {
    static class BillsGroup{
        public myBill[] Bills=null;
        public BillsGroup(ResultSet rs){
            try{
                rs.last();
                Bills=new myBill[rs.getRow()];
                rs.beforeFirst();
                int i=0;
                while(rs.next())
                    Bills[i++]=new myBill(rs);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
    String g(String s){
        return "'%"+s+"%'";
    }
    static String c(String s){
        return "'"+s+"',";
    }
    static String d(String s){return "'"+s+"'";}
    static String c(int s){
        return "'"+s+"',";
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        String spp=request.getParameter("sPara");
        String[] sPara=spp.split(" ");
        String spLeft="concat(bill_id,vip_id,transaction,pay,commodity) LIKE ";
        StringBuilder inWhere=new StringBuilder();
        for(String s:sPara)
            inWhere.append(spLeft).append(g(s)).append(" and ");
        String pay_type=request.getParameter("pay_type");
        if(!pay_type.equals("ig"))
            inWhere.append("pay_type = ").append(pay_type).append(" and ");
        String acc=request.getParameter("acc");
        if(!acc.equals("ig"))
            inWhere.append("is_accounted = ").append(acc).append(" and ");
        String time=request.getParameter("time");
        if(!time.equals("ig")){
            String[] times=time.split(">>");
            inWhere.append("trans_date between ").append(d(times[0])).append(" and ").append(d(times[1])).append(" and ");
        }
        inWhere.append("true");
        String InWhere=inWhere.toString();
        try{
            ResultSet rs=sqlConnector.sql.stat.executeQuery(
                    "select *from vip_bills where "+InWhere
            );
            BillsGroup bg=new BillsGroup(rs);
            ObjectMapper objectMapper=new ObjectMapper();
            response.getWriter().write(objectMapper.writeValueAsString(bg));
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
