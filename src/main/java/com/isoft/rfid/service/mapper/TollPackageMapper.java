package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.TollPackage;
import com.isoft.rfid.service.dto.TollPackageDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TollPackage} and its DTO {@link TollPackageDTO}.
 */
@Mapper(componentModel = "spring")
public interface TollPackageMapper extends EntityMapper<TollPackageDTO, TollPackage> {}
