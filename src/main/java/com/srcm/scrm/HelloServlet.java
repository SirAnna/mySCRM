package com.srcm.scrm;

import java.io.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import com.srcm.scrm.CrossHost;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CrossHost.set(response);
        response.setContentType("text/html");
        // Hello
        PrintWriter out = response.getWriter();
        out.write("biss");
    }
    class usr{
        public String data=null;
        public int name=0;
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CrossHost.set(response);
        String data=request.getParameter("data");
        String name=request.getParameter("name");
        usr u=new usr();
        u.data=data;
        u.name=Integer.parseInt(name);
        ObjectMapper objectMapper=new ObjectMapper();
        String res= objectMapper.writeValueAsString(u);
        response.getWriter().write(res);
    }

    public void destroy() {
    }
}