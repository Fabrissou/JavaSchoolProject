package org.javaschool.data.old.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypesDao implements BaseDao<String> {

    @Override
    public String get(long id) {
        String type = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from types where id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                type = resultSet.getString(2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return type;
    }

    @Override
    public List<String> getAll() {
        List<String> typesList = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from types")) {
            typesList = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                typesList.add(resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return typesList;
    }

    @Override
    public void save(String s) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into types (type) values (?)")) {

            preparedStatement.setString(1, s);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from types where id = ?");) {

            preparedStatement.setLong(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesTypeExist(long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from types where id = ?")) {
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

    public void update(String oldString, String newString) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update types set type = ? where type = ?");) {

            preparedStatement.setString(1, newString);
            preparedStatement.setString(2, oldString);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long getTypeId(String s) {
        Long id = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from types where type = ?")) {
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return id;
    }
}
