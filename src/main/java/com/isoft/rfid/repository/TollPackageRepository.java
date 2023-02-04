package com.isoft.rfid.repository;

import com.isoft.rfid.domain.TollPackage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TollPackage entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TollPackageRepository extends JpaRepository<TollPackage, Long>, JpaSpecificationExecutor<TollPackage> {}
