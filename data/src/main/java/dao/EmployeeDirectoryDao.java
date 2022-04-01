package dao;

import conection.PoolConnectionBuilder;
import model.EmployeeCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDirectoryDao implements BaseDao<EmployeeCard> {

    Connection getConnection() throws SQLException {
        return PoolConnectionBuilder.getConnection();
    }

    @Override
    public EmployeeCard get(long id) {
        EmployeeCard employee = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from employees where id = ?")) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employee = new EmployeeCard(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employee;
    }

    @Override
    public List<EmployeeCard> getAll() {
        List<EmployeeCard> employeeCardList = new ArrayList<>();

        try (Connection connection = PoolConnectionBuilder.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from employees")) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                EmployeeCard employeeCard = new EmployeeCard(
                        resultSet.getLong(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4));

                employeeCardList.add(employeeCard);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return employeeCardList;
    }

    @Override
    public void save(EmployeeCard employeeCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "insert into employees (personal_info, position, department) values (?, ?, ?);")) {
                preparedStatement.setString(1, employeeCard.getPersonalData());
                preparedStatement.setString(2, employeeCard.getPosition());
                preparedStatement.setString(3, employeeCard.getDepartment());
                preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(EmployeeCard employeeCard) {

    }

    @Override
    public void delete(EmployeeCard employeeCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from employees where id = ? and personal_info = ? and position = ? and department = ?")) {
            preparedStatement.setLong(1, employeeCard.getId());
            preparedStatement.setString(2, employeeCard.getPersonalData());
            preparedStatement.setString(3, employeeCard.getPosition());
            preparedStatement.setString(4, employeeCard.getDepartment());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
