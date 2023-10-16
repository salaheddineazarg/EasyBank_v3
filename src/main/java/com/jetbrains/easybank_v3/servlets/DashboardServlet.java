package com.jetbrains.easybank_v3.servlets;

import java.io.*;
import java.security.Key;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.jetbrains.easybank_v3.dao.ClientD;
import com.jetbrains.easybank_v3.dto.Client;
import com.jetbrains.easybank_v3.services.ClientService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import javax.swing.text.html.Option;


@WebServlet(name = "dashboardServlet",urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {
    public ClientService service ;
    @Override
    public void init(){
        this.service = new ClientService(new ClientD());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

                req.setAttribute("clients", service.GetClients());

             req.getRequestDispatcher("dashboard.jsp").forward(req,resp);

    }


}
