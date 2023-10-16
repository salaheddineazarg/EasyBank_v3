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

import java.io.IOException;


@WebServlet("/delete")
public class DeleteServlet extends HttpServlet {


    public ClientService service ;

    @Override
    public void init() throws ServletException {
        this.service = new ClientService(new ClientD());
    }



    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

      req.getParameter("code");

        try {
            int DeleteClient = service.DeleteClient(req.getParameter("code"));
            if (DeleteClient > 0) {
                System.out.println("true");
            } else {
                System.out.println("false");
            }
            String redirectURL = req.getContextPath() + "/dashboard";
            resp.sendRedirect(redirectURL);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}