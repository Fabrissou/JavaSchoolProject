package com.javaschool.service;

import com.javaschool.dto.CreateDepartmentRequest;
import com.javaschool.dto.CreateEmployeeRequest;
import dao.DepartmentDirectoryDao;
import dao.EmployeeDao;
import exceptions.NoSuchDepartmentException;
import exceptions.NoSuchPositionException;
import model.DepartmentCard;

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

    public void save(CreateDepartmentRequest departmentRequest) throws NoSuchDepartmentException, NoSuchPositionException {
        departmentDao.save(requestToDepartment(departmentRequest));
        for (CreateEmployeeRequest employeeRequest: departmentRequest.getDepartmentEmployees()) {
            employeeRequest.setDepartment_id(departmentDao.getDepartmentId(departmentRequest.getName()));
            employeeService.save(employeeRequest);

        }
    }

    public void delete(CreateDepartmentRequest departmentRequest) throws NoSuchDepartmentException {
        if (!(departmentDao.doesDepartmentExist(departmentRequest.getId()))) {
            throw new NoSuchDepartmentException();
        }
        List<Long> childrenIdList = departmentDao.getChildrenId(departmentRequest.getId());

        if (childrenIdList.isEmpty()) {
            employeeDao.changeDepartmentEmployees(departmentRequest.getId(), departmentRequest.getParent_id());
            departmentDao.delete(requestToDepartment(departmentRequest));
        } else {
            for (Long i: childrenIdList) {
                CreateDepartmentRequest department = get(i);
                delete(department);
            }
            employeeDao.changeDepartmentEmployees(departmentRequest.getId(), departmentRequest.getParent_id());
            departmentDao.delete(requestToDepartment(departmentRequest));
        }
    }

    public void update(CreateDepartmentRequest departmentRequest, long id) throws NoSuchDepartmentException {
        if (departmentDao.doesDepartmentExist(id) && departmentDao.doesDepartmentExist(departmentRequest.getParent_id())) {
            departmentDao.update(requestToDepartment(departmentRequest), id);
        } else {
            throw new NoSuchDepartmentException();
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
