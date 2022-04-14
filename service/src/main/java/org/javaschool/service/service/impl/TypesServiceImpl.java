package org.javaschool.service.service.impl;

import liquibase.pro.packaged.T;
import org.javaschool.data.model.department.Type;
import org.javaschool.service.service.TypesService;
import org.javaschool.data.repository.TypesRepository;
import org.javaschool.service.service.dto.TypeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class TypesServiceImpl implements TypesService {

    @Autowired
    private TypesRepository typesRepository;

    @Transactional
    @Override
    public TypeDto get(Long id) {
        Type type = null;
        Optional<Type> optionalType = typesRepository.findById(id);

        if (optionalType.isPresent()) {
            type = optionalType.get();
        }

        return mapperTypeDto(type);
    }

    @Transactional
    @Override
    public void save(TypeDto typeDto) {
        if (!typesRepository.existsById(typeDto.getId())) {
            typesRepository.save(mapperType(typeDto));
        }
    }

    @Transactional
    @Override
    public void delete(Long id) {
        if (typesRepository.existsById(id)) {
            typesRepository.deleteById(id);
        }
    }

    @Transactional
    @Override
    public void update(TypeDto typeDto, Long id) {
        if (typesRepository.existsById(id)) {
            typeDto.setId(id);
            typesRepository.save(mapperType(typeDto));
        }
    }



    @Transactional
    @Override
    public Type mapperType(TypeDto typeDto) {
        Type type = null;

        if (typeDto != null) {
            type = new Type();
            type.setId(typeDto.getId());
            type.setDepartmentType(typeDto.getDepartmentType());
        }

        return type;
    }

    @Transactional
    @Override
    public TypeDto mapperTypeDto(Type type) {
        TypeDto typeDto = null;

        if (type != null) {
            typeDto = new TypeDto();
            typeDto.setId(type.getId());
            typeDto.setDepartmentType(type.getDepartmentType());
        }

        return typeDto;
    }
}
