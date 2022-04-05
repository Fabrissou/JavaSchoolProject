package com.javaschool.service;

import com.javaschool.dto.CreateDepartmentRequest;
import com.javaschool.dto.CreateEmployeeRequest;
import dao.DepartmentDirectoryDao;
import dao.EmployeeDao;
import model.DepartmentCard;
import model.EmployeeCard;

import java.util.ArrayList;
import java.util.List;

public class DepartmentService {

    private DepartmentDirectoryDao departmentDao = new DepartmentDirectoryDao();
    private EmployeeDao employeeDao = new EmployeeDao();
    private EmployeeService employeeService = new EmployeeService();

    public CreateDepartmentRequest get(long id) {
        DepartmentCard departmentCard = departmentDao.get(id);
        CreateDepartmentRequest departmentRequest = null;

        if (departmentCard != null) {
            departmentRequest = departmentToRequest(departmentCard);
        }

        return departmentRequest;
    }

    public void save(CreateDepartmentRequest departmentRequest) {
        departmentDao.save(requestToDepartment(departmentRequest));
    }

    public void delete(CreateDepartmentRequest departmentRequest) {
        List<Long> childrenIdList = departmentDao.getChildrenId(departmentRequest.getId());

        if (childrenIdList.isEmpty()) {
            employeeDao.clearDepartmentEmployees(departmentRequest.getId());
            departmentDao.delete(requestToDepartment(departmentRequest));
        } else {
            for (Long i: childrenIdList) {
                CreateDepartmentRequest department = new CreateDepartmentRequest();
                department.setId(i);
                delete(department);
            }
            employeeDao.clearDepartmentEmployees(departmentRequest.getId());
            departmentDao.delete(requestToDepartment(departmentRequest));
        }
    }



    private CreateDepartmentRequest departmentToRequest(DepartmentCard departmentCard) {
        CreateDepartmentRequest departmentRequest = new CreateDepartmentRequest();

        departmentRequest.setName(departmentCard.getName());
        departmentRequest.setId(departmentCard.getId());
        departmentRequest.setParent_id(departmentCard.getParent_id());
        departmentRequest.setPath(departmentCard.getPath());
        departmentRequest.setDepartmentEmployees(employeeService.getCreateEmployeeRequestList(departmentCard.getId()));

        return departmentRequest;
    }

    private DepartmentCard requestToDepartment(CreateDepartmentRequest departmentRequest) {
        DepartmentCard departmentCard = new DepartmentCard();

        departmentCard.setName(departmentRequest.getName());
        departmentCard.setId(departmentRequest.getId());
        departmentCard.setParent_id(departmentRequest.getParent_id());

        return departmentCard;
    }
}
