import org.javaschool.data.model.department.Type;
import org.javaschool.data.repository.TypesRepository;
import org.javaschool.service.service.ConfigService;
import org.javaschool.service.service.TypesService;
import org.javaschool.service.service.dto.TypeDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class TypesServiceTest {
    private final TypesService typesService;
    private final TypesRepository typesRepository;


    public TypesServiceTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigService.class);
        this.typesService = context.getBean(TypesService.class);
        this.typesRepository = context.getBean(TypesRepository.class);
    }


    @Test
    public void get() {
        Type type = new Type();
        type.setDepartmentType("organisation");
        typesRepository.save(type);

        Assert.assertEquals(type.getDepartmentType(), typesService.get(1L).getDepartmentType());
    }

    @Test
    public void getAll() {
        Type type = new Type();
        type.setDepartmentType("department");
        typesRepository.save(type);

        TypeDto typeDto1 = new TypeDto();
        TypeDto typeDto2 = new TypeDto();
        typeDto1.setId(1L);
        typeDto2.setId(2L);
        typeDto1.setDepartmentType("organisation");
        typeDto2.setDepartmentType("department");

        List<TypeDto> list = new ArrayList<>();
        list.add(typeDto1);
        list.add(typeDto2);

        Assert.assertEquals(list, typesService.getAll());
    }

    @Test
    public void save() {
        Assert.assertEquals(2, typesRepository.count());

        TypeDto typeDto = new TypeDto();
        typeDto.setDepartmentType("dddd");
        typeDto.setId(3L);
        typesService.save(typeDto);

        Assert.assertEquals(3, typesRepository.count());
    }

    @Test
    public void update() {
        Assert.assertEquals("organisation", typesRepository.findById(1L).get().getDepartmentType());

        TypeDto typeDto = new TypeDto();
        typeDto.setDepartmentType("o");
        typesService.update(typeDto, 1L);

        Assert.assertEquals("o", typesRepository.findById(1L).get().getDepartmentType());
    }

    @Test
    public void delete() {
        Assert.assertEquals(3, typesRepository.count());
        typesService.delete(1L);
        Assert.assertEquals(2, typesRepository.count());
        typesService.delete(2L);
        Assert.assertEquals(1, typesRepository.count());
        typesService.delete(3L);
        Assert.assertEquals(0, typesRepository.count());
    }
}
