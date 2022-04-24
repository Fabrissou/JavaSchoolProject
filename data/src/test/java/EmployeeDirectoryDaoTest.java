import org.junit.Test;

import java.sql.*;

public class EmployeeDirectoryDaoTest {
//    EmployeeDirectoryDao directoryDao = new EmployeeDirectoryDao();
//    Connection connection = PoolConnectionBuilder.getConnection();

    @Test
    public void get() throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from employees where id = 1");
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//
//        Assert.assertEquals(resultSet.getString(2), directoryDao.get(1).getPersonalData());
//        Assert.assertEquals(resultSet.getString(3), directoryDao.get(1).getPosition());
//        Assert.assertEquals(resultSet.getString(4), directoryDao.get(1).getDepartment());
//
//        preparedStatement = connection.prepareStatement("select * from employees where id = 2");
//
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//
//        Assert.assertEquals(resultSet.getString(2), directoryDao.get(2).getPersonalData());
//        Assert.assertEquals(resultSet.getString(3), directoryDao.get(2).getPosition());
//        Assert.assertEquals(resultSet.getString(4), directoryDao.get(2).getDepartment());
//
//        preparedStatement = connection.prepareStatement("select * from employees where id = 3");
//
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//
//        Assert.assertEquals(resultSet.getString(2), directoryDao.get(3).getPersonalData());
//        Assert.assertEquals(resultSet.getString(3), directoryDao.get(3).getPosition());
//        Assert.assertEquals(resultSet.getString(4), directoryDao.get(3).getDepartment());
    }

    @Test
    public void getAll() throws SQLException {
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from employees");
//        ResultSet resultSet = preparedStatement.executeQuery();
//        List<EmployeeCard> list = directoryDao.getAll();
//
//        int i = 0;
//        while (resultSet.next()) {
//            Assert.assertEquals(list.get(i).getId(), resultSet.getLong(1));
//            Assert.assertEquals(list.get(i).getPersonalData(), resultSet.getString(2));
//            Assert.assertEquals(list.get(i).getPosition(), resultSet.getString(3));
//            Assert.assertEquals(list.get(i).getDepartment(), resultSet.getString(4));
//            i++;
//        }
    }

    @Test
    public void save() throws SQLException {
//
//        directoryDao.save(card);
//        directoryDao.save(card2);
//        directoryDao.save(card3);
//
//        PreparedStatement preparedStatement = connection.prepareStatement("select * from employees where personal_info = 'New employee'");
//
//        ResultSet resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//
//        Assert.assertEquals(resultSet.getString(2), card.getPersonalData());
//        Assert.assertEquals(resultSet.getString(3), card.getPosition());
//        Assert.assertEquals(resultSet.getString(4), card.getDepartment());
//
//        preparedStatement = connection.prepareStatement("select * from employees where personal_info = 'New employee2'");
//
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//
//        Assert.assertEquals(resultSet.getString(2), card2.getPersonalData());
//        Assert.assertEquals(resultSet.getString(3), card2.getPosition());
//        Assert.assertEquals(resultSet.getString(4), card2.getDepartment());
//
//        preparedStatement = connection.prepareStatement("select * from employees where personal_info = 'New employee3'");
//
//        resultSet = preparedStatement.executeQuery();
//        resultSet.next();
//
//        Assert.assertEquals(resultSet.getString(2), card3.getPersonalData());
//        Assert.assertEquals(resultSet.getString(3), card3.getPosition());
//        Assert.assertEquals(resultSet.getString(4), card3.getDepartment());
    }

    @Test
    public void update() {

    }

    @Test
    public void delete() throws SQLException {
//        EmployeeCard employeeCard = new EmployeeCard(1000, "robert", "developer", "three");
//        PreparedStatement preparedStatement = connection.prepareStatement("insert into employees (id, personal_info, position, department) values (1000, 'robert', 'developer', 'three')");
//        preparedStatement.execute();
//        Assert.assertEquals(directoryDao.get(1000), employeeCard);
//        directoryDao.delete(employeeCard);
//        Assert.assertEquals(directoryDao.get(1000), null);

    }



    public EmployeeDirectoryDaoTest() throws SQLException {
    }
}
