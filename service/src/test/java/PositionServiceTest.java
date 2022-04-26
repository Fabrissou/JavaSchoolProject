import liquibase.pro.packaged.P;
import org.javaschool.data.model.employee.Position;
import org.javaschool.data.repository.PositionsRepository;
import org.javaschool.service.service.ConfigService;
import org.javaschool.service.service.PositionService;
import org.javaschool.service.service.dto.PositionDto;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class PositionServiceTest {
    private final PositionService positionService;
    private final PositionsRepository positionsRepository;


    public PositionServiceTest() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ConfigService.class);
        this.positionService = context.getBean(PositionService.class);
        this.positionsRepository = context.getBean(PositionsRepository.class);
    }


    @Test
    public void get() {
        Position position = new Position();
        position.setEmployeePosition("developer");
        positionsRepository.save(position);

        Assert.assertEquals(position.getEmployeePosition(), positionService.get(1L).getEmployeePosition());
    }

    @Test
    public void getAll() {
        Position position = new Position();
        position.setEmployeePosition("manager");
        positionsRepository.save(position);

        PositionDto positionDto1 = new PositionDto();
        PositionDto positionDto2 = new PositionDto();
        positionDto1.setId(1L);
        positionDto2.setId(2L);
        positionDto1.setEmployeePosition("developer");
        positionDto2.setEmployeePosition("manager");

        List<PositionDto> list = new ArrayList<>();
        list.add(positionDto1);
        list.add(positionDto2);

        Assert.assertEquals(list, positionService.getAll());
    }

    @Test
    public void save() {
        Assert.assertEquals(2, positionsRepository.count());

        PositionDto positionDto = new PositionDto();
        positionDto.setEmployeePosition("designer");
        positionDto.setId(3L);
        positionService.save(positionDto);

        Assert.assertEquals(3, positionsRepository.count());
    }

    @Test
    public void update() {
        Assert.assertEquals("developer", positionsRepository.findById(1L).get().getEmployeePosition());

        PositionDto positionDto = new PositionDto();
        positionDto.setEmployeePosition("java developer");
        positionService.update(positionDto, 1L);

        Assert.assertEquals("java developer", positionsRepository.findById(1L).get().getEmployeePosition());
    }

    @Test
    public void delete() {
        Assert.assertEquals(3, positionsRepository.count());
        positionService.delete(1L);
        Assert.assertEquals(2, positionsRepository.count());
        positionService.delete(2L);
        Assert.assertEquals(1, positionsRepository.count());
        positionService.delete(3L);
        Assert.assertEquals(0, positionsRepository.count());
    }
}
