package com.isoft.rfid.repository;

import com.isoft.rfid.domain.VehicleOwner;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the VehicleOwner entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VehicleOwnerRepository extends JpaRepository<VehicleOwner, Long>, JpaSpecificationExecutor<VehicleOwner> {}
