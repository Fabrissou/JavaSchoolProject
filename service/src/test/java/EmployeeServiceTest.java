import liquibase.pro.packaged.E;
import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.department.Type;
import org.javaschool.data.model.employee.Employee;
import org.javaschool.data.model.employee.Position;
import org.javaschool.data.repository.DepartmentsRepository;
import org.javaschool.data.repository.EmployeesRepository;
import org.javaschool.data.repository.PositionsRepository;
import org.javaschool.data.repository.TypesRepository;
import org.javaschool.service.service.ConfigService;
import org.javaschool.service.service.EmployeesService;
import org.javaschool.service.service.dto.EmployeeDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class EmployeeServiceTest {
    private final EmployeesService employeesService;
    private final EmployeesRepository employeesRepository;
    private final DepartmentsRepository departmentsRepository;
    private final TypesRepository typesRepository;
    private final PositionsRepository positionsRepository;

    Department department;
    Position position;
    Type type;


    public EmployeeServiceTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigService.class);
        this.employeesService = context.getBean(EmployeesService.class);
        this.employeesRepository = context.getBean(EmployeesRepository.class);
        this.departmentsRepository = context.getBean(DepartmentsRepository.class);
        this.typesRepository = context.getBean(TypesRepository.class);
        this.positionsRepository = context.getBean(PositionsRepository.class);
        this.type = new Type();
        type.setDepartmentType("type1");
        type.setId(1L);
        this.department = new Department();
        department.setDepartmentName("department1");
        department.setDepartmentType(type);
        department.setId(1L);
        this.position = new Position();
        position.setEmployeePosition("position1");
        position.setId(1L);

    }


    @Test
    public void get() {
        typesRepository.save(type);
        positionsRepository.save(position);
        departmentsRepository.save(department);

        Employee employee = new Employee();

        employee.setEmployeeInfo("employee1");
        employee.setPositionId(position);
        employee.setDepartmentId(department);

        employeesRepository.save(employee);

        Assert.assertEquals(employee.getEmployeeInfo(), employeesService.get(1L).getEmployeeInfo());
        Assert.assertEquals(employee.getDepartmentId().getId(), employeesService.get(1L).getDepartmentId());
        Assert.assertEquals(employee.getPositionId().getId(), employeesService.get(1L).getPositionId());
    }

    @Test
    public void save() {
        Assert.assertEquals(1, typesRepository.count());

        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(2L);
        employeeDto.setEmployeeInfo("employee2");
        employeeDto.setPositionId(1L);
        employeeDto.setDepartmentId(1L);
        employeesService.save(employeeDto);

        Assert.assertEquals(2, employeesRepository.count());
    }

    @Test
    public void update() {
        Assert.assertEquals("employee1", employeesRepository.findById(1L).get().getEmployeeInfo());


        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setId(1L);
        employeeDto.setEmployeeInfo("emp");
        employeeDto.setPositionId(1L);
        employeeDto.setDepartmentId(1L);
        employeesService.update(employeeDto, 1L);


        Assert.assertEquals("emp", employeesRepository.findById(1L).get().getEmployeeInfo());
    }

    @Test
    public void delete() {
        Assert.assertEquals(2, employeesRepository.count());
        employeesService.adminDelete(1L);
        Assert.assertEquals(1, employeesRepository.count());
        employeesService.adminDelete(2L);
        Assert.assertEquals(0, employeesRepository.count());
    }
}
