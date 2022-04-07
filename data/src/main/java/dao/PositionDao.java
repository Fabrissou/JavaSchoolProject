package dao;

import liquibase.pro.packaged.P;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PositionDao implements BaseDao<String> {

    @Override
    public String get(long id) {
        String position = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from positions where id = ?")) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                position = resultSet.getString(2);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return position;
    }

    @Override
    public List<String> getAll() {
        List<String> positionsList = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from positions")) {
            positionsList = new ArrayList<>();

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                positionsList.add(resultSet.getString(2));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return positionsList;
    }

    @Override
    public void save(String s) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("insert into positions (position) values (?)")) {

            preparedStatement.setString(1, s);
            preparedStatement.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(String s) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("delete from positions where position = ?");) {

            preparedStatement.setString(1, s);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean doesPositionExist(long position_id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from positions where id = ?")) {
            preparedStatement.setLong(1, position_id);
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
             PreparedStatement preparedStatement = connection.prepareStatement("update positions set position = ? where position = ?");) {

            preparedStatement.setString(1, newString);
            preparedStatement.setString(2, oldString);
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Long getPositionId(String s) {
        Long posId = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from positions where position = ?")) {
            preparedStatement.setString(1, s);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                posId = resultSet.getLong(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return posId;
    }
}
