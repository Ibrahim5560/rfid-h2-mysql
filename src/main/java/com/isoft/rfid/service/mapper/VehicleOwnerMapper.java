package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.VehicleOwner;
import com.isoft.rfid.service.dto.VehicleOwnerDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleOwner} and its DTO {@link VehicleOwnerDTO}.
 */
@Mapper(componentModel = "spring")
public interface VehicleOwnerMapper extends EntityMapper<VehicleOwnerDTO, VehicleOwner> {}
