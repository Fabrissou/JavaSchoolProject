import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.department.Type;
import org.javaschool.data.model.employee.Position;
import org.javaschool.data.repository.DepartmentsRepository;
import org.javaschool.data.repository.EmployeesRepository;
import org.javaschool.data.repository.PositionsRepository;
import org.javaschool.data.repository.TypesRepository;
import org.javaschool.service.service.ConfigService;
import org.javaschool.service.service.DepartmentsService;
import org.javaschool.service.service.EmployeesService;
import org.javaschool.service.service.dto.DepartmentDto;
import org.javaschool.service.service.dto.PositionDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class DepartmentServiceTest {
    private final DepartmentsRepository departmentsRepository;
    private final DepartmentsService departmentsService;
    private final TypesRepository typesRepository;

    Type type;
    Department department;

    public DepartmentServiceTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigService.class);
        this.departmentsRepository = context.getBean(DepartmentsRepository.class);
        this.typesRepository = context.getBean(TypesRepository.class);
        this.departmentsService = context.getBean(DepartmentsService.class);
        this.type = new Type();
        type.setDepartmentType("type1");
        type.setId(1L);
        this.department = new Department();
        department.setDepartmentName("organisation");
        department.setDepartmentType(type);
        department.setId(1L);
    }


    @Test
    public void get() {
        typesRepository.save(type);
        departmentsRepository.save(department);
        Assert.assertEquals(department.getDepartmentName(), departmentsService.get(1L).getDepartmentName());
    }

    @Test
    public void getAll() {
        Department department1 = new Department();
        department1.setDepartmentType(type);
        department1.setDepartmentName("department2");
        department1.setParentDepartment(department);
        departmentsRepository.save(department1);

        Assert.assertEquals(2L, departmentsService.getAll().size());
    }

    @Test
    public void save() {
        Assert.assertEquals(2, departmentsRepository.count());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(3L);
        departmentDto.setDepartmentTypeId(1L);
        departmentDto.setDepartmentName("department3");
        departmentDto.setParentDepartmentId(2L);
        departmentsService.save(departmentDto);

        Assert.assertEquals(3, departmentsRepository.count());
    }

    @Test
    public void update() {
        Assert.assertEquals("organisation", departmentsRepository.findById(1L).get().getDepartmentName());

        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.setId(3L);
        departmentDto.setDepartmentTypeId(1L);
        departmentDto.setDepartmentName("aaaaa");
        departmentDto.setParentDepartmentId(0L);
        departmentsService.update(departmentDto, 1L);

        Assert.assertEquals("aaaaa", departmentsRepository.findById(1L).get().getDepartmentName());
    }

    @Test
    public void delete() {
        Assert.assertEquals(3, departmentsRepository.count());
        departmentsService.delete(1L);
        Assert.assertEquals(0, departmentsRepository.count());
    }
}
