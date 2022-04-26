package org.javaschool.service.service;

import org.javaschool.data.model.department.Type;
import org.javaschool.service.service.dto.TypeDto;

import java.util.List;

public interface TypesService {

    TypeDto get(Long id);

    List<TypeDto> getAll();

    boolean save(TypeDto typeDto);

    boolean delete(Long id);

    boolean update(TypeDto typeDto, Long id);

    Type mapperType(TypeDto typeDto);

    TypeDto mapperTypeDto(Type type);
}
