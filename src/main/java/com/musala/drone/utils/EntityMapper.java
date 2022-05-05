package com.musala.drone.utils;

import com.musala.drone.db.entity.DroneEntity;
import com.musala.drone.models.Drone;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper( EntityMapper.class );
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateDroneFromDto(Drone drone, @MappingTarget DroneEntity droneEntity);
}


