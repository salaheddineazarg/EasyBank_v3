package com.jetbrains.easybank_v3.servlets;

import java.io.*;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

import com.jetbrains.easybank_v3.dao.ClientD;
import com.jetbrains.easybank_v3.dto.Client;
import com.jetbrains.easybank_v3.services.ClientService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.swing.text.html.Option;


@WebServlet(name = "dashboardServlet",urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    private static Client client;
    private  static ClientD clientD;
    private  static ClientService clientService;

    @Override
    public void init(){

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        //req.setAttribute("clientMap");
            RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
            dispatcher.forward(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        client.setFirstName(req.getParameter("firstNAme"));
       client.setLastName(req.getParameter("lastName"));
       client.setNbPhone(req.getParameter("phone"));
       client.setDateBirth(LocalDate.parse(req.getParameter("date")));
       client.setAdresse(req.getParameter("adresse"));
       clientService.CreationClient(client);

        RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
        dispatcher.forward(req,resp);
    }
}
