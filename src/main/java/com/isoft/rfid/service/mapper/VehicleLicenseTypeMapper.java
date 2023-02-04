package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.VehicleLicenseType;
import com.isoft.rfid.service.dto.VehicleLicenseTypeDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link VehicleLicenseType} and its DTO {@link VehicleLicenseTypeDTO}.
 */
@Mapper(componentModel = "spring")
public interface VehicleLicenseTypeMapper extends EntityMapper<VehicleLicenseTypeDTO, VehicleLicenseType> {}
