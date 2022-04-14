package org.javaschool.service.old.service;

import org.javaschool.service.old.dto.CreateEmployeeRequest;
import org.javaschool.data.old.dao.DepartmentDao;
import org.javaschool.data.old.dao.EmployeeDao;
import org.javaschool.data.old.dao.PositionDao;
import org.javaschool.service.old.exceptions.NoSuchDepartmentException;
import org.javaschool.service.old.exceptions.NoSuchEmployeeException;
import org.javaschool.service.old.exceptions.NoSuchPositionException;
import org.javaschool.data.old.model.EmployeeCard;

import java.util.ArrayList;
import java.util.List;

public class EmployeeService {
    private DepartmentDao departmentDao = new DepartmentDao();
    private PositionDao positionDao = new PositionDao();
    private EmployeeDao employeeDao = new EmployeeDao();

    public CreateEmployeeRequest get(long id) {
        CreateEmployeeRequest employeeRequest = null;
        EmployeeCard employeeCard = employeeDao.get(id);

        if (employeeCard != null) {
            employeeRequest = employeeToRequest(employeeCard);
        }

        return employeeRequest;
    }

    public void save(CreateEmployeeRequest employeeRequest) throws NoSuchDepartmentException, NoSuchPositionException {
        boolean departmentExist = departmentDao.doesDepartmentExist(employeeRequest.getDepartmentId());
        boolean positionExist = positionDao.doesPositionExist(employeeRequest.getPositionId());
        Long departmentId = employeeRequest.getDepartmentId();

        if (departmentId == null) { throw new NoSuchDepartmentException(); }
        if (departmentExist && positionExist) {
            employeeDao.save(requestToEmployee(employeeRequest));
        } else if (!departmentExist){
            throw new NoSuchDepartmentException();
        } else {
            throw new NoSuchPositionException();
        }
    }

    public void delete(Long id) throws NoSuchEmployeeException {
        if (employeeDao.doesEmployeeExist(id)) {
            employeeDao.delete(id);
        } else {
            throw new NoSuchEmployeeException();
        }
    }

    public void update(CreateEmployeeRequest employeeRequest, long id) throws NoSuchDepartmentException, NoSuchPositionException {
        CreateEmployeeRequest oldEmployee = get(id);
        if (employeeRequest.getPositionId() == null) { employeeRequest.setPositionId(oldEmployee.getPositionId());}
        if (employeeRequest.getDepartmentId() == null) { employeeRequest.setDepartmentId(oldEmployee.getDepartmentId());}
        if (employeeRequest.getPersonalData() == null) { employeeRequest.setPersonalData(oldEmployee.getPersonalData());}
        boolean departmentExist = departmentDao.doesDepartmentExist(employeeRequest.getDepartmentId());
        boolean positionExist = positionDao.doesPositionExist(employeeRequest.getPositionId());

        if (departmentExist && positionExist) {
            employeeDao.update(requestToEmployee(employeeRequest), id);
        } else if (!departmentExist) {
            throw new NoSuchDepartmentException();
        } else {
            throw new NoSuchPositionException();
        }
    }

    public List<CreateEmployeeRequest> getCreateEmployeeRequestList(long departmentId) {
        List<CreateEmployeeRequest> createEmployeeRequestList = null;
        List<EmployeeCard> employeeCardList = employeeDao.getEmployeeList(departmentId);

        if (employeeCardList != null) {
            createEmployeeRequestList = new ArrayList<>();

            for (EmployeeCard employeeCard: employeeCardList) {
                createEmployeeRequestList.add(employeeToRequest(employeeCard));
            }
        }

        return createEmployeeRequestList;
    }

    private CreateEmployeeRequest employeeToRequest(EmployeeCard employeeCard) {
        CreateEmployeeRequest employeeRequest = new CreateEmployeeRequest();

        employeeRequest.setPersonalData(employeeCard.getPersonalData());
        employeeRequest.setPosition(positionDao.get(employeeCard.getPositionId()));
        employeeRequest.setPositionId(employeeCard.getPositionId());
        employeeRequest.setDepartment(departmentDao.getDepartmentName(employeeCard.getDepartmentId()));
        employeeRequest.setDepartmentId(employeeCard.getDepartmentId());

        return employeeRequest;
    }

    private EmployeeCard requestToEmployee(CreateEmployeeRequest employeeRequest) {
        EmployeeCard employeeCard = new EmployeeCard();

        employeeCard.setPersonalData(employeeRequest.getPersonalData());
        employeeCard.setPositionId(employeeRequest.getPositionId());
        employeeCard.setDepartmentId(employeeRequest.getDepartmentId());

        return employeeCard;
    }
}
