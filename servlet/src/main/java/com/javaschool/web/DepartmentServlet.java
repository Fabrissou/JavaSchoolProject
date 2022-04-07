package com.javaschool.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.dto.CreateDepartmentRequest;
import com.javaschool.dto.CreateEmployeeRequest;
import com.javaschool.service.DepartmentService;
import com.javaschool.service.EmployeeService;
import exceptions.NoSuchDepartmentException;
import exceptions.NoSuchPositionException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "DepartmentServlet", urlPatterns = "/department")
public class DepartmentServlet extends HttpServlet {

    private DepartmentService departmentService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        departmentService = new DepartmentService();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null) {
            CreateDepartmentRequest departmentRequest = departmentService.get(Long.parseLong(id));
            try (PrintWriter writer = resp.getWriter()) {
                resp.setContentType("application/json");
                if (departmentRequest == null) {
                    writer.write("There is no such department");
                } else {
                    writer.write(objectMapper.writeValueAsString(departmentRequest));
                }
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        CreateDepartmentRequest departmentRequest = objectMapper.readValue(body, CreateDepartmentRequest.class);

        PrintWriter writer = resp.getWriter();

        try {
            resp.setContentType("application/json");
            departmentService.save(departmentRequest);
            writer.write("Success!");
        } catch (NoSuchDepartmentException e) {
            writer.write("There is no such department");
        } catch (NoSuchPositionException e) {
            writer.write("There is no such position");
        }

        writer.close();
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        CreateDepartmentRequest departmentRequest = objectMapper.readValue(body, CreateDepartmentRequest.class);

        PrintWriter writer = resp.getWriter();

        if (id != null) {
            try {
                resp.setContentType("application/json");
                System.out.println(departmentRequest);
                departmentService.update(departmentRequest, Long.parseLong(id));
                writer.write("Success!");
            } catch (NoSuchDepartmentException e) {
                writer.write("There is no such department");
            }
        } else {
            writer.write("There is no such department");
        }

        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        CreateDepartmentRequest departmentRequest = objectMapper.readValue(body, CreateDepartmentRequest.class);

        PrintWriter writer = resp.getWriter();

        try {
            resp.setContentType("application/json");
            departmentService.delete(departmentRequest);
            writer.write("Success!");
        } catch (NoSuchDepartmentException e) {
            writer.write("There is no such department");
        }

        writer.close();
    }
}
