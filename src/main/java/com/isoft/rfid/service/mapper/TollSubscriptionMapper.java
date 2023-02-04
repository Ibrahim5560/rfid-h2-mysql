package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.TollPackage;
import com.isoft.rfid.domain.TollSubscription;
import com.isoft.rfid.service.dto.TollPackageDTO;
import com.isoft.rfid.service.dto.TollSubscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TollSubscription} and its DTO {@link TollSubscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TollSubscriptionMapper extends EntityMapper<TollSubscriptionDTO, TollSubscription> {
    @Mapping(target = "tollPackage", source = "tollPackage", qualifiedByName = "tollPackageId")
    TollSubscriptionDTO toDto(TollSubscription s);

    @Named("tollPackageId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TollPackageDTO toDtoTollPackageId(TollPackage tollPackage);
}
