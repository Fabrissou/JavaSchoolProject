package org.javaschool.service.service;

import org.javaschool.data.model.department.Type;
import org.javaschool.service.service.dto.TypeDto;

public interface TypesService {

    TypeDto get(Long id);

    void save(TypeDto typeDto);

    void delete(Long id);

    void update(TypeDto typeDto, Long id);

    Type mapperType(TypeDto typeDto);

    TypeDto mapperTypeDto(Type type);
}
