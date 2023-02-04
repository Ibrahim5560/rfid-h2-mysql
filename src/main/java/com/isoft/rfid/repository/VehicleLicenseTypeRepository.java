package com.isoft.rfid.repository;

import com.isoft.rfid.domain.VehicleLicenseType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VehicleLicenseType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleLicenseTypeRepository
    extends JpaRepository<VehicleLicenseType, Long>, JpaSpecificationExecutor<VehicleLicenseType> {}
