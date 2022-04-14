package org.javaschool.data.old.dao;

import org.javaschool.data.old.model.DepartmentCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDao implements BaseDao<DepartmentCard> {

    @Override
    public DepartmentCard get(long id) {
        DepartmentCard departmentCard = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where id = ?");) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                departmentCard = new DepartmentCard();

                departmentCard.setId(resultSet.getLong(1));
                departmentCard.setName(resultSet.getString(2));
                departmentCard.setParentId(resultSet.getLong(3));
                departmentCard.setType(resultSet.getLong(4));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentCard;
    }

    @Override
    public List<DepartmentCard> getAll() {
        List<DepartmentCard> departmentCardList = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments");) {

            ResultSet resultSet = preparedStatement.executeQuery();
            departmentCardList = new ArrayList<>();

            while (resultSet.next()) {
                DepartmentCard departmentCard = new DepartmentCard();

                departmentCard.setId(resultSet.getLong(1));
                departmentCard.setName(resultSet.getString(2));
                departmentCard.setParentId(resultSet.getLong(3));
                departmentCard.setType(resultSet.getLong(4));

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
                     "insert into departments (name, parent, type) values (?, ?, ?)")) {

            preparedStatement.setString(1, departmentCard.getName());
            preparedStatement.setLong(2, departmentCard.getParentId());
            preparedStatement.setLong(3, departmentCard.getType());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from departments where id = ?")) {
            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(DepartmentCard departmentCard, long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update departments set name = ?, parent = ?, type = ? where id = ?")) {

            preparedStatement.setString(1, departmentCard.getName());
            preparedStatement.setLong(2, departmentCard.getParentId());
            preparedStatement.setLong(3, departmentCard.getType());
            preparedStatement.setLong(4, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getDepartmentName(long id) {
        String name = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                name = resultSet.getString(2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return name;
    }

    public List<Long> getChildrenId(long id) {
        List<Long> departmentChildrenList = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where parent = ?");) {
            preparedStatement.setLong(1, id);
            departmentChildrenList = new ArrayList<>();
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                departmentChildrenList.add(resultSet.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return departmentChildrenList;
    }

    public boolean doesDepartmentExist(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Long getDepartmentId(String s) {
        Long depId = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where name = ?")) {
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                depId = resultSet.getLong(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return depId;
    }
}
