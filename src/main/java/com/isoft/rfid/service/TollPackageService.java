package com.isoft.rfid.service;

import com.isoft.rfid.service.dto.TollPackageDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.rfid.domain.TollPackage}.
 */
public interface TollPackageService {
    /**
     * Save a tollPackage.
     *
     * @param tollPackageDTO the entity to save.
     * @return the persisted entity.
     */
    TollPackageDTO save(TollPackageDTO tollPackageDTO);

    /**
     * Updates a tollPackage.
     *
     * @param tollPackageDTO the entity to update.
     * @return the persisted entity.
     */
    TollPackageDTO update(TollPackageDTO tollPackageDTO);

    /**
     * Partially updates a tollPackage.
     *
     * @param tollPackageDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TollPackageDTO> partialUpdate(TollPackageDTO tollPackageDTO);

    /**
     * Get all the tollPackages.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TollPackageDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tollPackage.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TollPackageDTO> findOne(Long id);

    /**
     * Delete the "id" tollPackage.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
