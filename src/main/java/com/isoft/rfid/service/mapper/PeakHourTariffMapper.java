package com.isoft.rfid.service.mapper;

import com.isoft.rfid.domain.PeakHourTariff;
import com.isoft.rfid.service.dto.PeakHourTariffDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link PeakHourTariff} and its DTO {@link PeakHourTariffDTO}.
 */
@Mapper(componentModel = "spring")
public interface PeakHourTariffMapper extends EntityMapper<PeakHourTariffDTO, PeakHourTariff> {}
