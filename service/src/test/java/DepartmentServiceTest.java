import org.javaschool.data.model.department.Department;
import org.javaschool.data.model.department.Type;
import org.javaschool.data.model.employee.Position;
import org.javaschool.data.repository.DepartmentsRepository;
import org.javaschool.data.repository.EmployeesRepository;
import org.javaschool.data.repository.PositionsRepository;
import org.javaschool.data.repository.TypesRepository;
import org.javaschool.service.service.ConfigService;
import org.javaschool.service.service.EmployeesService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class DepartmentServiceTest {
    private final DepartmentsRepository departmentsRepository;
    private final TypesRepository typesRepository;

    Type type;
    Department department;

    public DepartmentServiceTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigService.class);
        this.departmentsRepository = context.getBean(DepartmentsRepository.class);
        this.typesRepository = context.getBean(TypesRepository.class);
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

    }
}
