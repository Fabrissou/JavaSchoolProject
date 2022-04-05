package com.javaschool.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javaschool.dto.CreateEmployeeRequest;
import com.javaschool.service.EmployeeService;
import dao.DepartmentDirectoryDao;
import dao.EmployeeDao;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

@WebServlet(name = "EmployeeServlet", urlPatterns = "/employee")
public class EmployeeServlet extends HttpServlet {

    private EmployeeService employeeService;
    private ObjectMapper objectMapper;

    @Override
    public void init() throws ServletException {
        super.init();
        employeeService = new EmployeeService();
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        if (id != null) {
            CreateEmployeeRequest employeeRequest = employeeService.get(Long.parseLong(id));
            try (PrintWriter writer = resp.getWriter()) {
                resp.setContentType("application/json");
                writer.write(objectMapper.writeValueAsString(id));
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        System.out.println(body);
//        objectMapper.readValue(body, CreateEmployeeRequest.class);
        CreateEmployeeRequest employeeRequest = new CreateEmployeeRequest();
        employeeRequest.setPersonalData("sdvsv");
        employeeRequest.setDepartment_id(2L);
        employeeRequest.setPosition_id(2L);
        employeeService.save(employeeRequest);
    }
}
