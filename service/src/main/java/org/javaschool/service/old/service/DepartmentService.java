package org.javaschool.service.old.service;

import org.javaschool.service.old.dto.CreateDepartmentRequest;
import org.javaschool.service.old.dto.CreateEmployeeRequest;
import org.javaschool.data.old.dao.DepartmentDao;
import org.javaschool.data.old.dao.EmployeeDao;
import org.javaschool.service.old.exceptions.NoSuchDepartmentException;
import org.javaschool.service.old.exceptions.NoSuchPositionException;
import org.javaschool.data.old.model.DepartmentCard;

import java.util.List;

public class DepartmentService {

    private DepartmentDao departmentDao = new DepartmentDao();
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
        if (departmentRequest.getDepartmentEmployees() != null) {
            for (CreateEmployeeRequest employeeRequest: departmentRequest.getDepartmentEmployees()) {
                employeeRequest.setDepartmentId(departmentDao.getDepartmentId(departmentRequest.getName()));
                employeeService.save(employeeRequest);

            }
        }
    }

    public void delete(Long id) throws NoSuchDepartmentException {
        if (!(departmentDao.doesDepartmentExist(id))) {
            throw new NoSuchDepartmentException();
        }
        List<Long> childrenIdList = departmentDao.getChildrenId(id);

        if (childrenIdList.isEmpty()) {
            employeeDao.changeDepartmentEmployees(id, get(id).getParentId());
            departmentDao.delete(id);
        } else {
            for (Long i: childrenIdList) {
                delete(i);
            }
            employeeDao.changeDepartmentEmployees(id, get(id).getParentId());
            departmentDao.delete(id);
        }
    }

    public void update(CreateDepartmentRequest departmentRequest, long id) throws NoSuchDepartmentException {
        CreateDepartmentRequest oldDepartment = get(id);
        if (departmentRequest.getName() == null) { departmentRequest.setName(oldDepartment.getName());}
        if (departmentRequest.getParentId() == 0) { departmentRequest.setParentId(oldDepartment.getParentId());}
        if (departmentRequest.getType() == 0) { departmentRequest.setType(oldDepartment.getType());}

        if (departmentDao.doesDepartmentExist(id) && departmentDao.doesDepartmentExist(departmentRequest.getParentId())) {
            departmentDao.update(requestToDepartment(departmentRequest), id);
        } else {
            throw new NoSuchDepartmentException();
        }

    }

    private CreateDepartmentRequest departmentToRequest(DepartmentCard departmentCard) {
        CreateDepartmentRequest departmentRequest = new CreateDepartmentRequest();

        departmentRequest.setName(departmentCard.getName());
        departmentRequest.setId(departmentCard.getId());
        departmentRequest.setParentId(departmentCard.getParentId());
        departmentRequest.setType(departmentCard.getType());
        departmentRequest.setDepartmentEmployees(employeeService.getCreateEmployeeRequestList(departmentCard.getId()));

        return departmentRequest;
    }
    private DepartmentCard requestToDepartment(CreateDepartmentRequest departmentRequest) {
        DepartmentCard departmentCard = new DepartmentCard();

        departmentCard.setName(departmentRequest.getName());
        departmentCard.setId(departmentRequest.getId());
        departmentCard.setParentId(departmentRequest.getParentId());
        departmentCard.setType(departmentRequest.getType());

        return departmentCard;
    }
}
