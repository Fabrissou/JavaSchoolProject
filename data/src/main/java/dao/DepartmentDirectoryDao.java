package dao;

import conection.PoolConnectionBuilder;
import liquibase.pro.packaged.E;
import liquibase.pro.packaged.S;
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
             PreparedStatement preparedStatement = connection.prepareStatement("select * from departments where id = ?");) {
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                EmployeeDao employeeDirectoryDao = new EmployeeDao();
                departmentCard = new DepartmentCard();

                departmentCard.setName(resultSet.getString(2));
                departmentCard.setPath(getPath(id));
                departmentCard.setParent_id(resultSet.getLong(3));
                departmentCard.setDepartmentEmployees(employeeDirectoryDao.getEmployeeList(id));
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
            EmployeeDao employeeDirectoryDao = new EmployeeDao();
            departmentCardList = new ArrayList<>();

            while (resultSet.next()) {
                DepartmentCard departmentCard = new DepartmentCard();

                departmentCard.setName(resultSet.getString(2));
                departmentCard.setPath(getPath(resultSet.getLong(1)));
                departmentCard.setDepartmentEmployees(employeeDirectoryDao.getEmployeeList(resultSet.getLong(1)));

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

    public void update(DepartmentCard departmentCard) {
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(
//                     "update departments set name = ?, director = ?, hierarchy = ? where department_id = ?")) {
//
//            preparedStatement.setString(1, departmentCard.getName());
//            preparedStatement.setLong(2, departmentCard.getDirector().getId());
//            preparedStatement.setString(3, departmentCard.getHierarchy());
//            preparedStatement.setLong(4, departmentCard.getDepartment_id());
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void delete(DepartmentCard departmentCard) {
//        try (Connection connection = getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(
//                     "delete from departments where department_id = ? and name = ? and director = ? and hierarchy = ?")) {
//            preparedStatement.setLong(1, departmentCard.getDepartment_id());
//            preparedStatement.setString(2, departmentCard.getName());
//            preparedStatement.setLong(3, departmentCard.getDirector().getId());
//            preparedStatement.setString(4, departmentCard.getHierarchy());
//            preparedStatement.execute();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
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

//    public ResultSet getHierarchy() throws SQLException {
//        ResultSet resultSet = null;
//
//        try {
//            Connection connection = getConnection();
//            PreparedStatement preparedStatement = connection.prepareStatement("with recursive department_tree\n" +
//                    "                   (id, name, path, level, parent_id)\n" +
//                    "                   as (\n" +
//                    "        select id, name, '', 0, parent_id\n" +
//                    "        from departments\n" +
//                    "        where parent_id is NULL\n" +
//                    "        union all\n" +
//                    "        select\n" +
//                    "            mn.id,\n" +
//                    "            mn.name,\n" +
//                    "            mt.path || '/' || mn.name,\n" +
//                    "            mt.level + 1,\n" +
//                    "            mt.id\n" +
//                    "        from departments mn, department_tree mt\n" +
//                    "        where mn.parent_id = mt.id\n" +
//                    "    )\n" +
//                    "select * from department_tree where level > 0 order by level, parent_id");
//
//            resultSet = preparedStatement.executeQuery();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return resultSet;
//    }

}
