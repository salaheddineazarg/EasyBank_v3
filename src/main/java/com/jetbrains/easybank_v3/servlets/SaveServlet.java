package com.jetbrains.easybank_v3.servlets;

import com.jetbrains.easybank_v3.dao.ClientD;
import com.jetbrains.easybank_v3.dto.Client;
import com.jetbrains.easybank_v3.services.ClientService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;


@WebServlet(urlPatterns = { "/save"})
public class SaveServlet extends HttpServlet {
    private  Client client = new Client();
    public ClientService service ;

    @Override
    public void init() throws ServletException {
        this.service = new ClientService(new ClientD());
    }




    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try{
            client.setCode(req.getParameter("code"));
            client.setFirstName(req.getParameter("firstName"));
            client.setLastName(req.getParameter("lastName"));
            client.setNbPhone(req.getParameter("phone"));
            client.setDateBirth(LocalDate.parse(req.getParameter("date")));
            client.setAdresse(req.getParameter("adresse"));
            boolean clientResulet = service.CreateClient(client);
           if (clientResulet){
               String redirectURL = req.getContextPath() + "/dashboard";
               resp.sendRedirect(redirectURL);
           }

        }catch (Exception e){
            e.printStackTrace();

        }

    }


}
