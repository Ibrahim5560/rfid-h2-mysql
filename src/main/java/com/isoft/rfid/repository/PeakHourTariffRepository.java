package com.isoft.rfid.repository;

import com.isoft.rfid.domain.PeakHourTariff;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PeakHourTariff entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PeakHourTariffRepository extends JpaRepository<PeakHourTariff, Long>, JpaSpecificationExecutor<PeakHourTariff> {}
