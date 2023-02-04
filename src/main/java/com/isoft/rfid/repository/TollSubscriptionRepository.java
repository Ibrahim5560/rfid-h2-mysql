package com.isoft.rfid.repository;

import com.isoft.rfid.domain.TollSubscription;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the TollSubscription entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TollSubscriptionRepository extends JpaRepository<TollSubscription, Long>, JpaSpecificationExecutor<TollSubscription> {}
