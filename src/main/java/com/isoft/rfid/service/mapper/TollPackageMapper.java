package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.TollPackage;
import com.isoft.rfid.domain.TollSubscription;
import com.isoft.rfid.service.dto.TollPackageDTO;
import com.isoft.rfid.service.dto.TollSubscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TollPackage} and its DTO {@link TollPackageDTO}.
 */
@Mapper(componentModel = "spring")
public interface TollPackageMapper extends EntityMapper<TollPackageDTO, TollPackage> {
    @Mapping(target = "tollSubscription", source = "tollSubscription", qualifiedByName = "tollSubscriptionId")
    TollPackageDTO toDto(TollPackage s);

    @Named("tollSubscriptionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    TollSubscriptionDTO toDtoTollSubscriptionId(TollSubscription tollSubscription);
}
