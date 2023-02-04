package com.isoft.rfid.repository;

import com.isoft.rfid.domain.TollWhiteList;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TollWhiteList entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TollWhiteListRepository extends JpaRepository<TollWhiteList, Long>, JpaSpecificationExecutor<TollWhiteList> {}
