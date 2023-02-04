package com.isoft.rfid.service;

import com.isoft.rfid.service.dto.TollSubscriptionDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.rfid.domain.TollSubscription}.
 */
public interface TollSubscriptionService {
    /**
     * Save a tollSubscription.
     *
     * @param tollSubscriptionDTO the entity to save.
     * @return the persisted entity.
     */
    TollSubscriptionDTO save(TollSubscriptionDTO tollSubscriptionDTO);

    /**
     * Updates a tollSubscription.
     *
     * @param tollSubscriptionDTO the entity to update.
     * @return the persisted entity.
     */
    TollSubscriptionDTO update(TollSubscriptionDTO tollSubscriptionDTO);

    /**
     * Partially updates a tollSubscription.
     *
     * @param tollSubscriptionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TollSubscriptionDTO> partialUpdate(TollSubscriptionDTO tollSubscriptionDTO);

    /**
     * Get all the tollSubscriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TollSubscriptionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tollSubscription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TollSubscriptionDTO> findOne(Long id);

    /**
     * Delete the "id" tollSubscription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
