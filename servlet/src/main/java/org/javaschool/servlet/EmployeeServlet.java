package org.javaschool.servlet;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.javaschool.service.old.dto.CreateEmployeeRequest;
import org.javaschool.service.old.service.EmployeeService;
import org.javaschool.service.old.exceptions.NoSuchDepartmentException;
import org.javaschool.service.old.exceptions.NoSuchEmployeeException;
import org.javaschool.service.old.exceptions.NoSuchPositionException;

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
                if (employeeRequest == null) {
                    writer.write("There is no such employee");
                } else {
                    writer.write(objectMapper.writeValueAsString(employeeRequest));
                }
            }
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        CreateEmployeeRequest employeeRequest = objectMapper.readValue(body, CreateEmployeeRequest.class);

        PrintWriter writer = resp.getWriter();

        try {
            resp.setContentType("application/json");
            employeeService.save(employeeRequest);
            writer.write("Success!");
        } catch (NoSuchDepartmentException e) {
            writer.write("There is no such department");
        } catch (NoSuchPositionException e) {
            writer.write("There is no such position");
        } finally {
            writer.close();
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");
        String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        CreateEmployeeRequest employeeRequest = objectMapper.readValue(body, CreateEmployeeRequest.class);
        PrintWriter writer = resp.getWriter();

        if (id != null) {
            try {
                resp.setContentType("application/json");
                employeeService.update(employeeRequest, Long.parseLong(id));
                writer.write("Success!");
            } catch (NoSuchDepartmentException e) {
                writer.write("There is no such department");
            } catch (NoSuchPositionException e) {
                writer.write("There is no such position");
            }
        } else {
            writer.write("There is no such employee");
        }

        writer.close();
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String id = req.getParameter("id");

        if (id != null) {
            PrintWriter writer = resp.getWriter();

            try {
                resp.setContentType("application/json");
                employeeService.delete(Long.parseLong(id));
                writer.write("Success!");
            } catch (NoSuchEmployeeException e) {
                writer.write("There is no such employee");
            }

            writer.close();
        } else {
            resp.sendRedirect(req.getContextPath() + "/");
        }
    }
}
