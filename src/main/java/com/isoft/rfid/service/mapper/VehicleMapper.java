package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.Vehicle;
import com.isoft.rfid.service.dto.VehicleDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Vehicle} and its DTO {@link VehicleDTO}.
 */
@Mapper(componentModel = "spring")
public interface VehicleMapper extends EntityMapper<VehicleDTO, Vehicle> {}
