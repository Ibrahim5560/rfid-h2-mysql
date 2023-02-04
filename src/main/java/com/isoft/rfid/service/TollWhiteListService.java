package com.isoft.rfid.service;

import com.isoft.rfid.service.dto.TollWhiteListDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.isoft.rfid.domain.TollWhiteList}.
 */
public interface TollWhiteListService {
    /**
     * Save a tollWhiteList.
     *
     * @param tollWhiteListDTO the entity to save.
     * @return the persisted entity.
     */
    TollWhiteListDTO save(TollWhiteListDTO tollWhiteListDTO);

    /**
     * Updates a tollWhiteList.
     *
     * @param tollWhiteListDTO the entity to update.
     * @return the persisted entity.
     */
    TollWhiteListDTO update(TollWhiteListDTO tollWhiteListDTO);

    /**
     * Partially updates a tollWhiteList.
     *
     * @param tollWhiteListDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<TollWhiteListDTO> partialUpdate(TollWhiteListDTO tollWhiteListDTO);

    /**
     * Get all the tollWhiteLists.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TollWhiteListDTO> findAll(Pageable pageable);

    /**
     * Get the "id" tollWhiteList.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TollWhiteListDTO> findOne(Long id);

    /**
     * Delete the "id" tollWhiteList.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
