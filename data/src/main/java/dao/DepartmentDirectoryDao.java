package dao;

import conection.PoolConnectionBuilder;
import model.DepartmentCard;
import model.EmployeeCard;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDirectoryDao implements BaseDao<DepartmentCard> {

    @Override
    public DepartmentCard get(long id) {
        DepartmentCard departmentCard = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where department_id = ?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                EmployeeDirectoryDao employeeDirectoryDao = new EmployeeDirectoryDao();

                String departmentName = resultSet.getString(2);
                EmployeeCard director = employeeDirectoryDao.get(resultSet.getLong(3));
                List<EmployeeCard> employeeCardList = employeeDirectoryDao.getEmployeeList(departmentName);

                departmentCard = new DepartmentCard();
                departmentCard.setDepartment_id(resultSet.getLong(1));
                departmentCard.setName(departmentName);
                departmentCard.setDirector(director);
                departmentCard.setHierarchy(resultSet.getString(4));
                departmentCard.setDepartmentEmployees(employeeCardList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentCard;
    }

    @Override
    public List<DepartmentCard> getAll() {
        List<DepartmentCard> departmentCardList = new ArrayList<>();

        try (Connection connection = PoolConnectionBuilder.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EmployeeDirectoryDao employeeDirectoryDao = new EmployeeDirectoryDao();

                DepartmentCard departmentCard = new DepartmentCard();
                String departmentName = resultSet.getString(2);
                EmployeeCard director = employeeDirectoryDao.get(resultSet.getLong(3));
                List<EmployeeCard> employeeCardList = employeeDirectoryDao.getEmployeeList(departmentName);

                departmentCard = new DepartmentCard();
                departmentCard.setDepartment_id(resultSet.getLong(1));
                departmentCard.setName(departmentName);
                departmentCard.setDirector(director);
                departmentCard.setHierarchy(resultSet.getString(4));
                departmentCard.setDepartmentEmployees(employeeCardList);

                departmentCardList.add(departmentCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentCardList;
    }

    @Override
    public void save(DepartmentCard departmentCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into departments (name, director, hierarchy) values (?, ?, ?);")) {
            preparedStatement.setString(1, departmentCard.getName());
            preparedStatement.setLong(2, departmentCard.getDirector().getId());
            preparedStatement.setString(3, departmentCard.getHierarchy());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(DepartmentCard departmentCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update departments set name = ?, director = ?, hierarchy = ? where department_id = ?")) {

            preparedStatement.setString(1, departmentCard.getName());
            preparedStatement.setLong(2, departmentCard.getDirector().getId());
            preparedStatement.setString(3, departmentCard.getHierarchy());
            preparedStatement.setLong(4, departmentCard.getDepartment_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DepartmentCard departmentCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from departments where department_id = ? and name = ? and director = ? and hierarchy = ?")) {
            preparedStatement.setLong(1, departmentCard.getDepartment_id());
            preparedStatement.setString(2, departmentCard.getName());
            preparedStatement.setLong(3, departmentCard.getDirector().getId());
            preparedStatement.setString(4, departmentCard.getHierarchy());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
