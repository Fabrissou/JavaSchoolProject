package org.javaschool.service.service;

import org.javaschool.data.model.employee.Position;
import org.javaschool.service.service.dto.PositionDto;

public interface PositionService {

    PositionDto get(Long id);

    void save(PositionDto positionDto);

    void delete(Long id);

    void update(PositionDto positionDto, Long id);

    Position mapperPosition(PositionDto positionDto);

    PositionDto mapperPositionDto(Position position);
}
