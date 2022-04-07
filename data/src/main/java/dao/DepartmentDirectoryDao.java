package dao;

import liquibase.pro.packaged.R;
import model.DepartmentCard;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDirectoryDao implements BaseDao<DepartmentCard> {

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
                departmentCard.setPath(getPath(id));
                departmentCard.setParent_id(resultSet.getLong(3));
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
                departmentCard.setPath(getPath(resultSet.getLong(1)));
                departmentCard.setParent_id(resultSet.getLong(3));


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
                     "insert into departments (name, parent_id) values (?, ?)")) {

            preparedStatement.setString(1, departmentCard.getName());
            preparedStatement.setLong(2, departmentCard.getParent_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(DepartmentCard departmentCard) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "delete from departments where id = ?")) {
            preparedStatement.setLong(1, departmentCard.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(DepartmentCard departmentCard, long id) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("update departments set name = ?, parent_id = ? where id = ?")) {

            preparedStatement.setString(1, departmentCard.getName());
            preparedStatement.setLong(2, departmentCard.getParent_id());
            preparedStatement.setLong(3, id);
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

    public void changeDepartmentName(DepartmentCard departmentCard, String newName) {
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(
                     "update departments set name = ? where (name = ? and parent_id = ?)")) {

            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, departmentCard.getName());
            preparedStatement.setLong(3, departmentCard.getParent_id());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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

    public String getPath(long id) {
        String path = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("with recursive department_tree\n" +
                     "                   (id, name, path, level, parent_id)\n" +
                     "                   as (\n" +
                     "        select id, name, '', 0, parent_id\n" +
                     "        from departments\n" +
                     "        where parent_id is NULL\n" +
                     "        union all\n" +
                     "        select\n" +
                     "            mn.id,\n" +
                     "            mn.name,\n" +
                     "            mt.path || '/' || mn.name,\n" +
                     "            mt.level + 1,\n" +
                     "            mt.id\n" +
                     "        from departments mn, department_tree mt\n" +
                     "        where mn.parent_id = mt.id\n" +
                     "    )\n" +
                     "select * from department_tree where id = ? order by level, parent_id")) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                path = resultSet.getString(3);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return path;
    }

    public List<Long> getChildrenId(long id) {
        List<Long> departmentChildrenList = null;

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where parent_id = ?");) {
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
}
