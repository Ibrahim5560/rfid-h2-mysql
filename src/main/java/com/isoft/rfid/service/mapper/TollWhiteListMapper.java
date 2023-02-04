package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.TollWhiteList;
import com.isoft.rfid.service.dto.TollWhiteListDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TollWhiteList} and its DTO {@link TollWhiteListDTO}.
 */
@Mapper(componentModel = "spring")
public interface TollWhiteListMapper extends EntityMapper<TollWhiteListDTO, TollWhiteList> {}
