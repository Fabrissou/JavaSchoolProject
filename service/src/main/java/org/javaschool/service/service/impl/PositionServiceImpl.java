package org.javaschool.service.service.impl;

import org.javaschool.data.model.employee.Position;
import org.javaschool.service.service.PositionService;
import org.javaschool.data.repository.PositionsRepository;
import org.javaschool.service.service.dto.PositionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class PositionServiceImpl implements PositionService {

    @Autowired
    private PositionsRepository positionsRepository;

    @Transactional
    @Override
    public PositionDto get(Long id) {
        Position position = null;
        Optional<Position> optionalPosition = positionsRepository.findById(id);

        if (optionalPosition.isPresent()) {
            position = optionalPosition.get();
        }

        return mapperPositionDto(position);
    }

    @Transactional
    @Override
    public boolean save(PositionDto positionDto) {
        if (!positionsRepository.existsById(positionDto.getId())) {
            positionsRepository.save(mapperPosition(positionDto));
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean delete(Long id) {
        if (positionsRepository.existsById(id)) {
            positionsRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public boolean update(PositionDto positionDto, Long id) {
        if (positionsRepository.existsById(id)) {
            positionDto.setId(id);
            positionsRepository.save(mapperPosition(positionDto));
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    @Override
    public Position mapperPosition(PositionDto positionDto) {
        Position position = null;

        if (positionDto != null) {
            position = new Position();
            position.setId(positionDto.getId());
            position.setEmployeePosition(positionDto.getEmployeePosition());
        }

        return position;
    }

    @Transactional
    @Override
    public PositionDto mapperPositionDto(Position position) {
        PositionDto positionDto = null;

        if (position != null) {
            positionDto = new PositionDto();
            positionDto.setId(position.getId());
            positionDto.setEmployeePosition(position.getEmployeePosition());
        }

        return positionDto;
    }
}
