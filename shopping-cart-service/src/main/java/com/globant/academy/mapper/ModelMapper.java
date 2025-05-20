package com.globant.academy.mapper;

import java.util.ArrayList;
import java.util.List;

public interface ModelMapper<E,D> {
    D toDto(E Entity);

    E toEntity(D Dto);

    default List<D> toDTOList(List<E> entityList){
        if (entityList == null) {
            return new ArrayList<>();
        }
        return entityList.stream().map(this::toDto).toList();
    }

    default List<E> toEntityList(List<D> DTOs){
        if (DTOs == null) {
            return new ArrayList<>();
        }
        return DTOs.stream().map(this::toEntity).toList();
    }
}
