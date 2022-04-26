package org.javaschool.service.service;

import org.javaschool.data.model.employee.Position;
import org.javaschool.service.service.dto.PositionDto;

import java.util.List;

public interface PositionService {

    PositionDto get(Long id);

    public List<PositionDto> getAll();

    boolean save(PositionDto positionDto);

    boolean delete(Long id);

    boolean update(PositionDto positionDto, Long id);

    Position mapperPosition(PositionDto positionDto);

    PositionDto mapperPositionDto(Position position);
}
