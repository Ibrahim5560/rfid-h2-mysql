package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.TollSubscription;
import com.isoft.rfid.service.dto.TollSubscriptionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link TollSubscription} and its DTO {@link TollSubscriptionDTO}.
 */
@Mapper(componentModel = "spring")
public interface TollSubscriptionMapper extends EntityMapper<TollSubscriptionDTO, TollSubscription> {}
